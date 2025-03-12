package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class LigneCommande {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idLigneCommande;
    float prixUnitaire;
    float prixTotal ;
    int quantite;
    @ManyToOne
    Materiel materiel ;
    @ManyToOne
    @JsonBackReference
    Commande commande;
    boolean affecte ;
}
