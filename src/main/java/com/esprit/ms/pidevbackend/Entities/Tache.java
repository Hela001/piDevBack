package com.esprit.ms.pidevbackend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idTache;

    String nom;
    String description;

    Date startDate;
    Date finishDate;

    @Enumerated(EnumType.STRING)
    Status etatTache;

    @Enumerated(EnumType.STRING)
    Priorite priorite;

    Double chargeTravail; // Exemple : en heures

    @ManyToOne
    @JsonBackReference
    Mission mission;

    // Stocker uniquement l'ID du responsable de la tâche
    private Integer responsableId;

    // Stocker uniquement les IDs des utilisateurs assignés à la tâche
    @ElementCollection
    @CollectionTable(name = "tache_utilisateurs", joinColumns = @JoinColumn(name = "tache_id"))
    @Column(name = "utilisateur_id")
    List<Integer> assignesIds;
}
