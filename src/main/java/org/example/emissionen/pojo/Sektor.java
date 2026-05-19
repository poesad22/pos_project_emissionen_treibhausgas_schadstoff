package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Sektor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sektorId;

    private String sektorName;

    @JsonCreator
    public Sektor(String sektorName) {
        this.sektorName = sektorName;
    }
    @OneToMany(mappedBy = "sektor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NfrItem> nfrItems = new ArrayList<>();
}