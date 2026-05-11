package org.example.emissionen.repository;

import org.example.emissionen.pojo.NfrItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfrItemRepository extends JpaRepository<NfrItem, Long> {
}
