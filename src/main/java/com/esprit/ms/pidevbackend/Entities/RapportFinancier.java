package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class RapportFinancier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idRapport;

    Long idUtilisateur;
    double depense;
    double budget;
    float salaire;

    public Long getIdRapport() {
        return idRapport;
    }

    public void setIdRapport(Long idRapport) {
        this.idRapport = idRapport;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public double getDepense() {
        return depense;
    }

    public void setDepense(double depense) {
        this.depense = depense;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public RapportStatus getStatus() {
        return status;
    }

    public void setStatus(RapportStatus status) {
        this.status = status;
    }

    public Set<Fiche_de_paie> getFicheDePaies() {
        return ficheDePaies;
    }

    public void setFicheDePaies(Set<Fiche_de_paie> ficheDePaies) {
        this.ficheDePaies = ficheDePaies;
    }

    public Set<Facture> getFactures() {
        return factures;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }

    @Enumerated(EnumType.STRING)
    RapportStatus status;

    /** Les relations **/
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Fiche_de_paie> ficheDePaies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Facture> factures = new HashSet<>();
}