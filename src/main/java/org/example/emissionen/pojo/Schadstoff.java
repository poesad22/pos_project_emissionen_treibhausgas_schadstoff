package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Schadstoff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name ="schadstoff_atom",
            joinColumns = @JoinColumn(name = "schadstoffId"),
            inverseJoinColumns = @JoinColumn(name = "atomId")
    )
    @JsonIgnore
    private List<Atom> atome;
}
