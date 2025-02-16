package com.esprit.ms.pidevbackend.Entities;

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
    Commande commande;
    boolean affecte ;
}
