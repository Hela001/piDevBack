package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class RapportQualite {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idR;
    LocalDate dateCreation;

    String PhotoVideo;
    @Column(columnDefinition = "VARCHAR(5000)")
    String Contenu;

    @OneToOne(mappedBy = "rapportQualite")
    @JsonIgnore

    private Inspection inspection;


}

