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
    double  budget ;
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



    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setDépense(double dépense) {
        this.dépense = dépense;
    }

    public void setSalaire(float salaire) {
        Salaire = salaire;
    }

    public void setStatus(RapportStatus status) {
        this.status = status;
    }

    public void setFicheDePaies(Set<Fiche_de_paie> ficheDePaies) {
        this.ficheDePaies = ficheDePaies;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }
}
