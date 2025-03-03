package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idProjet;

    @Column(nullable = false, unique = true)
    private String nom;  // Correction de "name" -> "nom"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeProjet typeProjet;

    @Enumerated(EnumType.STRING)
    Status status;

    private LocalDate dateDebut;
    private LocalDate dateFinPrevue;
    private LocalDate dateFinReelle;

    private BigDecimal budgetInitial;
    private BigDecimal budgetReel;

    private String adresse;
    private Double latitude;
    private Double longitude;

    private String maitreOuvrage;
    private String maitreOeuvre;
    private String entrepreneurPrincipal;

    @Column(nullable = false)
    private long chefProjetId = 0L;

    private Boolean permisConstruction;

    private Double progression;

    @Column(columnDefinition = "TEXT")
    private String risquesIdentifies;

    @Column(columnDefinition = "TEXT")
    private String contraintes;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Mission> missions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "projet_membres", joinColumns = @JoinColumn(name = "projet_id"))
    @Column(name = "utilisateur_id")
    private List<Long> membresEquipeIds = new ArrayList<>();
}
