package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idMission;

    String nom;
    String description;

    Date startDate;
    Date finishDate;

    @Enumerated(EnumType.STRING)
    Status etatMission;

    @Enumerated(EnumType.STRING)
    Priorite priorite;

    Double budget;

    @ManyToOne
    @JoinColumn(name = "id_projet", nullable = false)  // Ensure it's not nullable
    @JsonBackReference
    private Projet projet;



    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Tache> taches= new ArrayList<>();

    // Stocker uniquement les IDs des utilisateurs assignés à la mission
    @ElementCollection
    @CollectionTable(name = "mission_utilisateurs", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "utilisateur_id")
    List<Integer> utilisateursIds;

    // Stocker uniquement l'ID du responsable de la mission
    private Integer responsableId;
}
