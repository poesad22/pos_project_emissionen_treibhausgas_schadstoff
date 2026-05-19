package org.example.emissionen.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Atom {
    @Id
    private String symbol;
    private String name;
    private Double atomicMass;
    private Double boil;
    private Double melt;
    private Double density;
    private Double electronegativityPauling;
    @ManyToMany(mappedBy = "atome")
    private List<Molekuel> molekuele;
}
