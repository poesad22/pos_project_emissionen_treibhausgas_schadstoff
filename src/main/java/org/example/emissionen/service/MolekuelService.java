package org.example.emissionen.service;

import lombok.RequiredArgsConstructor;
import org.example.emissionen.repository.MolekuelRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MolekuelService {
    private final MolekuelRepo molekuelRepo;
}
