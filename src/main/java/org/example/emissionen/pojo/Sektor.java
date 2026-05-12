package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Sektor {
    @Id
    private Long sektorId;

    private String sektorName;

    // ↓ Add this constructor for Jackson
    @JsonCreator
    public Sektor(String sektorName) {
        this.sektorName = sektorName;
    }

    // ↓ Keep this for JPA
    public Sektor() {}

    @OneToMany(mappedBy = "sektor", cascade = CascadeType.ALL)  // ← removed @Transient
    @JsonManagedReference
    private List<NfrItem> nfrItems = new ArrayList<>();
}