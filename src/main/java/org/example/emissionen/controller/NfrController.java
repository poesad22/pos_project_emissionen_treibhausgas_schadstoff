package org.example.emissionen.controller;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.NfrItem;
import org.example.emissionen.repository.NfrItemRepository;
import org.example.emissionen.service.NfrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nfr")
@RequiredArgsConstructor
public class NfrController {
    private final NfrService nfrService;

    @GetMapping("/all")
    public ResponseEntity<List<NfrItem>> getAll() {
        return ResponseEntity.ok(nfrService.getNfrItems());
    }

    @GetMapping("/schadstoff/{schadstoff}")
    public ResponseEntity<List<NfrItem>> getBySchadstoff(@PathVariable String schadstoff) {
        return ResponseEntity.ok(nfrService.getBySchadstoff(schadstoff));
    }


}
