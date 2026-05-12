package org.example.emissionen.database;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.NfrItem;
import org.example.emissionen.repository.NfrItemRepository;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitDatabase {
    private NfrItemRepository nfrItemRepository;
    @PostConstruct
    public void createData(){
        try {
            InputStream NfrStream = getClass().getResourceAsStream("/luftschadstoff_emissionen_nach_nfr.json");
            InputStream CrtStream = getClass().getResourceAsStream("/treibhausgas_emissionen_nach_crt.json");
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            List<NfrItem> nfrItems = objectMapper.readerForListOf(NfrItem.class).readValue(NfrStream);
            nfrItemRepository.saveAll(nfrItems);
        } catch (IOException e){
            throw new RuntimeException();
        }
    }
}
