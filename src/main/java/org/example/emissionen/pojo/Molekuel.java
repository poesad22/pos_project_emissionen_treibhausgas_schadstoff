package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Molekuel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long molekuelId;

    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name ="molekuel_atom",
            joinColumns = @JoinColumn(name = "molekuelId"),
            inverseJoinColumns = @JoinColumn(name = "atomId")
    )
    @JsonIgnore
    private List<Atom> atome;
}
