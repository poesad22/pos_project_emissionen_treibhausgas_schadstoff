package org.example.emissionen.repository;

import org.example.emissionen.pojo.Molekuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MolekuelRepo extends JpaRepository<Molekuel, String> {
}
