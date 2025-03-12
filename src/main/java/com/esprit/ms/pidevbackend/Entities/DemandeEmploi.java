package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class DemandeEmploi {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idDemandeEmploi;
    String nom;
    String prenom;
    String adresseMail;
    Long idUser ;
    String specialite;
    int  age ;
    String experience ;
    @Enumerated(EnumType.STRING)
    StatusDemande status= StatusDemande.En_attente;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime dateDemandeEmploi = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Entretien entretien;
}
