package com.esprit.ms.pidevbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class LigneDemande {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idLigneDemande;
    int Quantite;
    @ManyToOne
    Demande demande ;
    @OneToOne
    Materiel materiel ;
}
