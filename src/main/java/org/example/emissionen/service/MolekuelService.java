package org.example.emissionen.service;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.Atom;
import org.example.emissionen.pojo.Molekuel;
import org.example.emissionen.repository.AtomRepo;
import org.example.emissionen.repository.MolekuelRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MolekuelService {
    private final MolekuelRepo molekuelRepo;
    private final AtomRepo atomRepo;

    public double getSiedepunkt(Molekuel molekuel) {
        return molekuel.getAtome().stream()
                .map(Atom::getBoil)
                .filter(b -> b != null)
                .max(Double::compareTo)
                .orElse(0.0);
    }

}
