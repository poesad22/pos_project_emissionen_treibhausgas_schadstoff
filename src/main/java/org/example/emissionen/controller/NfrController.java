package org.example.emissionen.controller;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.repository.NfrItemRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nfr")
@RequiredArgsConstructor
public class NfrController {
    private final NfrItemRepository nfrItemRepository;
}
