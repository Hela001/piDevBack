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

public class Commande {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long  idCommande ;
    Long  idResponsableLogistique ;
    String nomCommande ;
    Integer quantite ;
    Long  idFacture ;

    @OneToOne(mappedBy = "commande")
    Facture facture;

}
