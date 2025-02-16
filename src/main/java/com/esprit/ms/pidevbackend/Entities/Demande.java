package com.esprit.ms.pidevbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Demande {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idDemande;
    @Enumerated(EnumType.STRING)
    Status status ;
    Date dateDemande ;
    @OneToMany(mappedBy = "demande")
    List<LigneDemande> ligneDemandes ;
}
