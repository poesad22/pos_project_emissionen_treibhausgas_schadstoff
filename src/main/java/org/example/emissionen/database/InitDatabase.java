package org.example.emissionen.database;

import com.fasterxml.jackson.databind.JsonNode;
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

            // --- 1. NFR Daten ---
            InputStream nfrStream = getClass().getResourceAsStream("/luftschadstoff_emissionen_nach_nfr.json");
            if (nfrStream == null) throw new IllegalStateException("Resource not found: /luftschadstoff_emissionen_nach_nfr.json");
            List<NfrItem> nfrItems = objectMapper.readerForListOf(NfrItem.class).readValue(nfrStream);
            nfrItemRepository.saveAll(nfrItems);

            // --- 2. Atom Daten aus Periodentabelle ---
            InputStream atomStream = getClass().getResourceAsStream("/periodic-table-lookup.json");
            if (atomStream == null) throw new IllegalStateException("Resource not found: /periodic-table-lookup.json");
            JsonNode root = objectMapper.readTree(atomStream);

            List<String> order = objectMapper.convertValue(
                    root.get("order"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            List<Atom> atome = new ArrayList<>();
            for (String elementName : order) {
                JsonNode elementNode = root.get(elementName);
                if (elementNode == null) continue;

                Atom atom = new Atom();
                atom.setSymbol(elementNode.path("symbol").asText());
                atom.setName(elementNode.path("name").asText());
                atom.setAtomicMass(!elementNode.path("atomic_mass").isNull()
                        ? elementNode.path("atomic_mass").asDouble() : null);
                atom.setBoil(!elementNode.path("boil").isNull()
                        ? elementNode.path("boil").asDouble() : null);
                atom.setMelt(!elementNode.path("melt").isNull()
                        ? elementNode.path("melt").asDouble() : null);
                atom.setDensity(!elementNode.path("density").isNull()
                        ? elementNode.path("density").asDouble() : null);
                atom.setElectronegativityPauling(!elementNode.path("electronegativity_pauling").isNull()
                        ? elementNode.path("electronegativity_pauling").asDouble() : null);

                atome.add(atom);
            }
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
                Map<String, Integer> anzahl = new HashMap<>();

                if (kuerzel.equals("NOX")) {
                    // NOX = Gruppe von Stickoxiden, als NO2 approximiert
                    addAtom(molekuelAtome, anzahl, "N", 1);
                    addAtom(molekuelAtome, anzahl, "O", 2);
                } else if (!SKIP_KUERZEL.contains(kuerzel)) {
                    parseMolekuelFormel(kuerzel, molekuelAtome, anzahl);
                }

                m.setAtome(molekuelAtome);
                m.setAnzahlDerAtome(anzahl);
                molekuele.add(m);
            }
            molekuelRepository.saveAll(molekuele);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void parseMolekuelFormel(String formel, Set<Atom> atome, Map<String, Integer> anzahl) {
        Matcher matcher = ELEMENT_PATTERN.matcher(formel);
        while (matcher.find()) {
            String symbol = matcher.group(1);
            int count = matcher.group(2).isEmpty() ? 1 : Integer.parseInt(matcher.group(2));
            addAtom(atome, anzahl, symbol, count);
        }
    }

    private void addAtom(Set<Atom> atome, Map<String, Integer> anzahl, String symbol, int count) {
        atomRepository.findById(symbol).ifPresent(atom -> {
            atome.add(atom);
            anzahl.put(symbol, count);
        });
    }
}