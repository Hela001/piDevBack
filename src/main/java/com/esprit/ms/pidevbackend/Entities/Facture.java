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

public class Facture {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long  idFacture ;
    Long  idCommande ;
    Long  idResponsableLogistique ;
    Long  idFournisseur ;
    Long idUtilisateur ;
    double  montantTotal ;
    Date dateFacture ;
    Date dateEcheance ;
    double montantTotalHorsTaxe ;
    double tva ;
    @Enumerated(EnumType.STRING) // Pour stocker le statut sous forme de chaîne
    private FactureStatus status; // Enum pour les statuts
    /** Les relations **/
    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Paiement paiement;
    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
        if (paiement != null) {
            paiement.setFacture(this); // Mettre à jour la référence dans Paiement
        }
    }




}
