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
    int  joursTravailles ;
    @Enumerated(EnumType.STRING)
    methodePaiement  TypePaiement ;
    Date datePaiement ;


    /** Les relations **/
    @ManyToOne
    @JsonIgnore
    RapportFinancier rapportFinancier;

}
