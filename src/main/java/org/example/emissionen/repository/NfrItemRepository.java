package org.example.emissionen.repository;

import org.example.emissionen.pojo.NfrItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NfrItemRepository extends JpaRepository<NfrItem, Long> {
    List<NfrItem> findBySchadstoff(String schadstoff);
    List<NfrItem> findByRegion(String region);
    List<NfrItem> findByJahr(int jahr);



}
