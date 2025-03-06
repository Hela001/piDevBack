package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class RapportFinancier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long  idRapport ;
    Long idUtilisateur ;
    double  dépense ;
    float  Salaire ;
    @Enumerated(EnumType.STRING)
    RapportStatus  status ;

    /** Les relations **/
//(cascade = CascadeType.ALL)
    @OneToMany
    @JsonIgnore
    private Set<Fiche_de_paie> ficheDePaies;

    @OneToMany
    @JsonIgnore
    private Set<Facture> factures;


}
