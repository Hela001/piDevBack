package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Inspecteur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idInspecteur ;
    String nomInspecteur ;
    String adresseInspecteur ;
    String telephoneInspecteur ;
    String emailInspecteur ;
    @OneToMany(mappedBy = "inspecteur", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Inspection> inspections;
}
