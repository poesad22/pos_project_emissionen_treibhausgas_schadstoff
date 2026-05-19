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

    @GetMapping("/region/{region}")
    public ResponseEntity<List<NfrItem>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(nfrService.getByRegion(region));
    }

    @GetMapping("/jahr/{jahr}")
    public ResponseEntity<List<NfrItem>> getByJahr(@PathVariable int jahr) {
        return ResponseEntity.ok(nfrService.getByJahr(jahr));
    }

    @GetMapping("/jahr/range")
    public ResponseEntity<List<NfrItem>> getByJahrRange(
            @RequestParam int von,
            @RequestParam int bis) {
        return ResponseEntity.ok(nfrService.getByJahrRange(von, bis));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<NfrItem>> getBySchadstoffAndJahr(
            @RequestParam String schadstoff,
            @RequestParam int jahr) {
        return ResponseEntity.ok(nfrService.getBySchadstoffAndJahr(schadstoff, jahr));
    }

    @GetMapping("/sektor/{sektorName}")
    public ResponseEntity<List<NfrItem>> getBySektor(@PathVariable String sektorName) {
        return ResponseEntity.ok(nfrService.getBySektor(sektorName));
    }


}
