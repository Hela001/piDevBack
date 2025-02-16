package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class Materiel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idMateriel;
    @Enumerated(EnumType.STRING)
    Categorie categorie ;
    int quantite;
    String nomMateriel ;
    float prixMateriel ;
    @OneToMany(mappedBy = "materiel")
    @JsonIgnore
    List<LigneCommande> ligneCommandes ;

}
