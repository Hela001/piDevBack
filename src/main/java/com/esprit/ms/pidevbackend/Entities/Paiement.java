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
    float montant;

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

    public void setFacture(Facture facture) {
        this.facture = facture;
        if (facture != null && facture.getPaiement() != this) {
            facture.setPaiement(this);
        }
    }
}
