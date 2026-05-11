package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class NfrItem {
    @Id
    private Long NfrId;

    private String region;

    private String schadstoff;

    private String einheit;

    private int nfr_code;

    @ManyToOne
    @JoinColumn(name = "sektorId")
    @JsonBackReference
    private Sektor sektor;

    private String quelle;

    private LocalDate datenstand;

    private int jahr;

    private float werte;
}
