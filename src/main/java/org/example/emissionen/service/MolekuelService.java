package org.example.emissionen.service;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.Atom;
import org.example.emissionen.pojo.Molekuel;
import org.example.emissionen.repository.AtomRepo;
import org.example.emissionen.repository.MolekuelRepo;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public double getSchmelzpunkt(Molekuel molekuel) {
        return molekuel.getAtome().stream()
                .map(Atom::getMelt)
                .filter(b -> b != null)
                .min(Double::compareTo)
                .orElse(0.0);
    }

    public double berechneMolareMasse(Molekuel molekuel) {
        double masse = 0.0;
        for (Map.Entry<String, Integer> entry : molekuel.getAnzahlDerAtome().entrySet()) {
            String symbol = entry.getKey();
            int anzahl = entry.getValue();
            Atom atom = atomRepo.findById(symbol).orElse(null);
            if (atom != null && atom.getAtomicMass() != null) {
                masse += atom.getAtomicMass() * anzahl;
            }
        }
        return Math.round(masse * 100.0) / 100.0;
    }

}
