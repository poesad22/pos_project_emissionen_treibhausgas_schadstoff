package org.example.emissionen.service;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.NfrItem;
import org.example.emissionen.repository.NfrItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NfrService {
    private final NfrItemRepository nfrItemRepository;

    public List<NfrItem> getNfrItems() {
        return nfrItemRepository.findAll();
    }

    public List<NfrItem> getBySchadstoff(String schadstoff) {
        return nfrItemRepository.findBySchadstoff(schadstoff);
    }

    public List<NfrItem> getByRegion(String region) {
        return nfrItemRepository.findByRegion(region);
    }

    public List<NfrItem> getByJahr(int jahr) {
        return nfrItemRepository.findByJahr(jahr);
    }

    public List<NfrItem> getByJahrRange(int von, int bis) {
        return nfrItemRepository.findByJahrBetween(von, bis);
    }




}
