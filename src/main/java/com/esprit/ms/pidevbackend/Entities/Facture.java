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

    /** Les relations **/
    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;


    @OneToOne
    @JsonIgnore
    Paiement paiement;

}
