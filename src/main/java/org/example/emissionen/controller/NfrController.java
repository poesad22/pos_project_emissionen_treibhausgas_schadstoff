package org.example.emissionen.controller;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.NfrItem;
import org.example.emissionen.repository.NfrItemRepository;
import org.example.emissionen.service.NfrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nfr")
@RequiredArgsConstructor
public class NfrController {
    private final NfrService nfrService;

    @GetMapping("/all")
    public ResponseEntity<List<NfrItem>> getAll(){

    }
}
