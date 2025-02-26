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

public class Paiement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idPaiement ;
    Long idUtilisateur ;
    Long idContrat ;
    float  montant ;
    Date datePaiement ;
    @Enumerated(EnumType.STRING)
    methodePaiement  payment;
    int  numeroCarte ;

    @OneToOne(mappedBy = "paiement")
    @JsonIgnore
    private Facture facture;

    public void setFacture(Facture facture) {
        this.facture = facture;
        if (facture != null) {
            facture.setPaiement(this); // Mettre à jour la référence dans Facture
        }
    }


}
