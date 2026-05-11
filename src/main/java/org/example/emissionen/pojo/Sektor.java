package org.example.emissionen.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Sektor {
    @Id
    private Long sektorId;

    private String sektorName;

    @OneToMany(
            mappedBy ="sektor",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<NfrItem> nfrItems = new ArrayList<>();
}
