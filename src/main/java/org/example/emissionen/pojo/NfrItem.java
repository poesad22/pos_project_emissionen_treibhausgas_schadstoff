package org.example.emissionen.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class NfrItem {
    @Id
    private Long NfrId;

    private String region;

    private String schadstoff;

    private String einheit;

    private int nfr_code;

    private String nfr_sektor;

    private String quelle;

    private LocalDate datenstand;

    private int jahr;

    private float werte;
}
