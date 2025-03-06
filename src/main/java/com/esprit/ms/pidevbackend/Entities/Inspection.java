package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inspection {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idINS;

    Date dateInspection;

    @Enumerated(EnumType.STRING)
    TypeInspection typeInspection;

    @Enumerated(EnumType.STRING)
    StatusInspection statusInspection;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspection_id")
    List<NonConfirmity> nonConformities;



    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rapport_id", referencedColumnName = "idR", unique = true)

    RapportQualite rapportQualite;

    @ManyToOne
    @JoinColumn(name = "inspecteur_id")

    Inspecteur inspecteur;

    @ManyToOne
    @JoinColumn(name = "projet_id")

    private Projet projet;

    public Inspection(Long idINS, Projet projet) {
        this.idINS = idINS;
        this.projet = projet;
    }

    public Long getIdProjet() {
        return projet.getIdProjet();
    }

    public Long getIdInspecteur() {

        return inspecteur.getIdInspecteur();
    }
}


