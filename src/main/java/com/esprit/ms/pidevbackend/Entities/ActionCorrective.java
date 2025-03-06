package com.esprit.ms.pidevbackend.Entities;


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

public class ActionCorrective {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idAC;
    String description;
    Date dateDebut;
    Date dateFin;
    @Enumerated(EnumType.STRING)
    StatusInspection statusActionCorrective;



}
