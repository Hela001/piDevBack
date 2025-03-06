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
    Long idContrat ;
    Long idUtilisateur ;
     Float montantInitial;
    Float montantFinal;
    int  joursNonTravailles ;
    String nom;
    Date datePaiement ;

    @Enumerated(EnumType.STRING)
    methodePaiement  TypePaiement ;
    @Enumerated(EnumType.STRING) // Pour stocker le statut sous forme de chaîne
    private FicheStatus statutPaiementL= FicheStatus.Unpaid; // Enum pour les statuts

    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;

    public void setStatutPaiementL(FicheStatus statutPaiementL) {
        this.statutPaiementL = statutPaiementL;
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


    public FicheStatus getStatutPaiementL() {
        return statutPaiementL;
    }


 public int getJoursNonTravailles() {
        return joursNonTravailles;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public Float getMontantInitial() {
        return montantInitial;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setMontantInitial(Float montantInitial) {
        this.montantInitial = montantInitial;
    }

    public void setTypePaiement(methodePaiement typePaiement) {
        TypePaiement = typePaiement;
    }

    public Float getMontantFinal() {
        return montantFinal;
    }

    public void setMontantFinal(Float montantFinal) {
        this.montantFinal = montantFinal;
    }
}

