package org.example.emissionen.database;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.Atom;
import org.example.emissionen.pojo.Molekuel;
import org.example.emissionen.pojo.NfrItem;
import org.example.emissionen.repository.AtomRepo;
import org.example.emissionen.repository.MolekuelRepo;
import org.example.emissionen.repository.NfrItemRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitDatabase {
    private final NfrItemRepository nfrItemRepository;
    private final AtomRepo atomRepository;
    private final MolekuelRepo molekuelRepository;

    private static final Pattern ELEMENT_PATTERN = Pattern.compile("([A-Z][a-z]?)([0-9]*)");

    private static final Set<String> SKIP_KUERZEL = Set.of(
            "PM10", "PM2.5", "TSP", "PAH", "PCB", "NMVOC", "THG", "NOX"
    );

    @PostConstruct
    public void createData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            ObjectMapper snakeMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            // --- 1. NFR Daten ---
            InputStream nfrStream = getClass().getResourceAsStream("/luftschadstoff_emissionen_nach_nfr.json");
            if (nfrStream == null) throw new IllegalStateException("Resource not found: /luftschadstoff_emissionen_nach_nfr.json");
            List<NfrItem> nfrItems = objectMapper.readerForListOf(NfrItem.class).readValue(nfrStream);
            nfrItemRepository.saveAll(nfrItems);

            // --- 2. Atom Daten aus Periodentabelle ---
            InputStream atomStream = getClass().getResourceAsStream("/periodic-table-lookup.json");
            if (atomStream == null) throw new IllegalStateException("Resource not found: /periodic-table-lookup.json");
            JsonNode root = snakeMapper.readTree(atomStream);

            List<String> order = snakeMapper.convertValue(
                    root.get("order"),
                    snakeMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            List<Atom> atome = order.stream()
                    .map(root::get)
                    .filter(Objects::nonNull)
                    .map(el -> snakeMapper.convertValue(el, Atom.class))
                    .toList();

            atomRepository.saveAll(atome);

            // --- 3. Molekuel Daten aus NFR JSON ---
            InputStream molekuelStream = getClass().getResourceAsStream("/luftschadstoff_emissionen_nach_nfr.json");
            if (molekuelStream == null) throw new IllegalStateException("Resource not found: /luftschadstoff_emissionen_nach_nfr.json");
            List<NfrItem> nfrItemsForMolekuel = objectMapper.readerForListOf(NfrItem.class).readValue(molekuelStream);

            Set<String> schadstoffKuerzel = nfrItemsForMolekuel.stream()
                    .map(NfrItem::getSchadstoff)
                    .collect(Collectors.toSet());

            List<Molekuel> molekuele = new ArrayList<>();
            for (String kuerzel : schadstoffKuerzel) {
                Molekuel m = new Molekuel();
                m.setName(kuerzel);
                m.setFormel(kuerzel);

                Set<Atom> molekuelAtome = new HashSet<>();

                if (kuerzel.equals("NOX")) {
                    addAtom(molekuelAtome, "N");
                    addAtom(molekuelAtome, "O");
                } else if (!SKIP_KUERZEL.contains(kuerzel)) {
                    parseMolekuelFormel(kuerzel, molekuelAtome);
                }

                m.setAtome(molekuelAtome);
                molekuele.add(m);
            }
            molekuelRepository.saveAll(molekuele);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void parseMolekuelFormel(String formel, Set<Atom> atome) {
        Matcher matcher = ELEMENT_PATTERN.matcher(formel);
        while (matcher.find()) {
            addAtom(atome, matcher.group(1));
        }
    }

    private void addAtom(Set<Atom> atome, String symbol) {
        atomRepository.findById(symbol).ifPresent(atome::add);
    }
}