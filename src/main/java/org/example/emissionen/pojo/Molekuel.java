package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Data
public class Molekuel {
    @Id
    private String name;

    private String formel;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name ="molekuel_atom",
            joinColumns = @JoinColumn(name = "molekuel_id"),
            inverseJoinColumns = @JoinColumn(name = "atom_symbol")
    )
    private Set<Atom> atome = new HashSet<>();

    @ElementCollection
    private Map<String, Integer> anzahlDerAtome;
}
