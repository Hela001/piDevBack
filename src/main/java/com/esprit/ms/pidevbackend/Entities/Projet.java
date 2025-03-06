package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Projet {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idProjet;

    String NomProjet;


    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Inspection> inspections;

}
