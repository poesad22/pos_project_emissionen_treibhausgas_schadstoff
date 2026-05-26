package org.example.emissionen.service;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.pojo.Atom;
import org.example.emissionen.pojo.Molekuel;
import org.example.emissionen.repository.AtomRepo;
import org.example.emissionen.repository.MolekuelRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MolekuelService {
    private final MolekuelRepo molekuelRepo;
    private final AtomRepo atomRepo;

    public double getSiedepunkt(Molekuel molekuel) {
        List<Atom> atome = molekuel.getAtome().stream()
                .filter(a -> a.getBoil() != null)
                .toList();

        if (atome.isEmpty()) return 0.0;

        Map<String, Long> haeufigkeit = atome.stream()
                .collect(Collectors.groupingBy(Atom::getSymbol, Collectors.counting()));

        double summe = 0.0;
        long gesamt = 0;
        for (Atom atom : atome) {
            long count = haeufigkeit.get(atom.getSymbol());
            summe += atom.getBoil() * count;
            gesamt += count;
        }
        return summe / gesamt;
    }

    public double getSchmelzpunkt(Molekuel molekuel) {
        List<Atom> atome = molekuel.getAtome().stream()
                .filter(a -> a.getMelt() != null)
                .toList();

        if (atome.isEmpty()) return 0.0;

        Map<String, Long> haeufigkeit = atome.stream()
                .collect(Collectors.groupingBy(Atom::getSymbol, Collectors.counting()));

        double summe = 0.0;
        long gesamt = 0;
        for (Atom atom : atome) {
            long count = haeufigkeit.get(atom.getSymbol());
            summe += atom.getMelt() * count;
            gesamt += count;
        }
        return summe / gesamt;
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
    public double getENDifferenz(Molekuel molekuel) {
        List<Double> enWerte = molekuel.getAtome().stream()
                .map(Atom::getElectronegativityPauling)
                .filter(b -> b != null)
                .collect(Collectors.toList());

        if (enWerte.size() >= 2) {
            double max = Collections.max(enWerte);
            double min = Collections.min(enWerte);
            return Math.round((max - min) * 100.0) / 100.0;
        }
        return 0.0; // Reine Elemente wie Pb, Cd
    }
}
