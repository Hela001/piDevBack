package com.esprit.ms.pidevbackend.Entities;

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
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Commande {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idCommande;
    float prixTotal ;
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "commande")
    List<LigneCommande> ligneCommandes ;
    Long idfournisseur ;
    Date dateCreation ;
    @Enumerated(EnumType.STRING)
    Status status ;
}

