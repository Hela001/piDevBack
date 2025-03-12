package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToMany(mappedBy = "demande" , cascade = CascadeType.ALL)
    @JsonManagedReference  // Empêche la boucle infinie
    List<LigneDemande> ligneDemandes ;
    Long idUser ;
}
