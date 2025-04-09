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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idFacture;

    Long idCommande;
    Long idResponsableLogistique;
    Long idFournisseur;
    Long idUtilisateur;
    double montantTotal;

    @Temporal(TemporalType.DATE)
    Date dateFacture;

    @Temporal(TemporalType.DATE)
    Date dateEcheance;

    double montantTotalHorsTaxe;
    double tva;

    @Enumerated(EnumType.STRING)
    private FactureStatus status;

    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;

    @OneToOne
    @JsonIgnore
    Paiement paiement;


    public double getMontantTotal() {
        return montantTotal;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public double getMontantTotalHorsTaxe() {
        return montantTotalHorsTaxe;
    }

    public double getTva() {
        return tva;
    }

    public Long getIdFacture() {
        return idFacture;
    }

    public FactureStatus getStatus() {
        return status;
    }

    public void setStatus(FactureStatus status) {
        this.status = status;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
        if (paiement != null && paiement.getFacture() != this) {
            paiement.setFacture(this); // Mise à jour de la relation bidirectionnelle
        }
    }
}
