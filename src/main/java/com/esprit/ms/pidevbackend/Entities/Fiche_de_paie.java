package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class Fiche_de_paie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idBulletinPaie;
    Long  idRapport ;
    Long idContrat ;
    Long idUtilisateur ;
    float  montantInitial ;
    float  montantFinal ;
    int  joursTravailles ;
    String nom;
    @Enumerated(EnumType.STRING)
    methodePaiement  TypePaiement ;
    Date datePaiement ;
    @Enumerated(EnumType.STRING) // Pour stocker le statut sous forme de chaîne
    private FactureStatus statutPaiementL; // Enum pour les statuts


    /** Les relations **/
    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;

}
