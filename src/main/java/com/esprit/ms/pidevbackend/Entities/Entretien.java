package com.esprit.ms.pidevbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Entretien {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idEntretien;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateEntretien;    @Enumerated(EnumType.STRING)
    TypeEntretient typeEntretient ;
    String lienMeet ;
    @OneToOne(mappedBy = "entretien" )
    DemandeEmploi demandeEmploi ;
}
