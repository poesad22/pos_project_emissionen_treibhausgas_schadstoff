package org.example.emissionen.repository;

import org.example.emissionen.pojo.Sektor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SektorRepository extends JpaRepository<Sektor, Long> {
    Sektor findBySektorName(String sektorName);
}
