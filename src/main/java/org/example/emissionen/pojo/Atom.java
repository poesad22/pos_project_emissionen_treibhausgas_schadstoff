package org.example.emissionen.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Atom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long atomId;

    @ManyToMany(mappedBy = "atome")
    private List<Schadstoff> schadstoffe;
}
