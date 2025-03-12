package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
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
    @JsonManagedReference
    List<LigneCommande> ligneCommandes = new ArrayList<>();
    Long idfournisseur ;
    Date dateCreation ;
    @Enumerated(EnumType.STRING)
    Status status ;
}

