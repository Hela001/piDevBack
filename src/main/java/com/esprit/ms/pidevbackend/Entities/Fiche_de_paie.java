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
    int  joursNonTravailles ;
    String nom;



    public float getMontantInitial() {
        return montantInitial;
    }


    public float getMontantFinal() {
        return montantFinal;
    }

    public void setMontantFinal(float montantFinal) {
        this.montantFinal = montantFinal;
    }

    public int getJoursTravailles() {
        return joursNonTravailles;
    }

    public String getNom() {
        return nom;
    }


    public methodePaiement getTypePaiement() {
        return TypePaiement;
    }


    public Date getDatePaiement() {
        return datePaiement;
    }


    public FactureStatus getStatutPaiementL() {
        return statutPaiementL;
    }



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
