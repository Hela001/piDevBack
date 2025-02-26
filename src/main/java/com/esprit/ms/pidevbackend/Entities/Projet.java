package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idProjet;

    @Column(nullable = false, unique = true)
    private String nom;

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

    // Stocker uniquement l'ID du chef de projet
    @Column(nullable = false)
    private long chefProjetId  = 0L;

    private Boolean permisConstruction;

   // @ElementCollection
   // private List<String> documentsAnnexes;

    private Double progression;

    @Column(columnDefinition = "TEXT")
    private String risquesIdentifies;

    @Column(columnDefinition = "TEXT")
    private String contraintes;

  //  @ElementCollection
   // @CollectionTable(name = "indicateurs_performance", joinColumns = @JoinColumn(name = "projet_id"))
  //  @MapKeyColumn(name = "indicateur")
   // @Column(name = "valeur")
   // private Map<String, Double> indicateursPerformance;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Mission> missions = new ArrayList<>();


    // Stocker uniquement les IDs des membres de l'équipe
    @ElementCollection
    @CollectionTable(name = "projet_membres", joinColumns = @JoinColumn(name = "projet_id"))
    @Column(name = "utilisateur_id")
    private List<Long> membresEquipeIds = new ArrayList<>();




}
