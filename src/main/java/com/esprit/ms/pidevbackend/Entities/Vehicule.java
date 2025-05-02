package com.esprit.ms.pidevbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Vehicule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idVehicule;
    String nomVehicule;
    Long idChauffeur ;
    String marque;
    String modele;
    String immatriculation;
    @Enumerated(EnumType.STRING)
    TypeVehicule typeVehicule;
    Boolean disponible;
    Double latitude;
    Double longitude;
    LocalDate dateAffectation;

}
