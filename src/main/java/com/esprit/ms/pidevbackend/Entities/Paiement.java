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
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPaiement;

    Long idUtilisateur;
    Long idContrat;

    public Long getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Long idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Long getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(Long idContrat) {
        this.idContrat = idContrat;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public methodePaiement getPayment() {
        return payment;
    }

    public void setPayment(methodePaiement payment) {
        this.payment = payment;
    }

    public int getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(int numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    float montant;
    @Column(unique = true)
    private String sessionId;
    @Temporal(TemporalType.DATE)
    Date datePaiement;

    @Enumerated(EnumType.STRING)
    methodePaiement payment;

    int numeroCarte;

    @OneToOne(mappedBy = "paiement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Facture facture;

    public Facture getFacture() {
        return facture;
    }

    public float getMontant() {
        return montant;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
        if (facture != null && facture.getPaiement() != this) {
            facture.setPaiement(this);
        }
    }
}