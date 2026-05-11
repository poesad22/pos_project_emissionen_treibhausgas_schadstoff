package org.example.emissionen.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sektor {
    @Id
    private Long sektorId;

    private String sektorName;
}
