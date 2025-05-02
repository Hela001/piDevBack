package com.esprit.ms.pidevbackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Chauffeur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idChauffeur;
    Long numeroTel;
    String nomChauffeur;
    boolean disponible= true;
}
