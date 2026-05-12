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

    public List<NfrItem> getNfrItems(){
        return nfrItemRepository.findAll();
    }
}
