package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Priorite;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Entities.Tache;
import jakarta.persistence.JoinColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Long> {
    List<Tache> findByMission_idMission(long missionId);
    // Recherche par nom de tâche (insensible à la casse)
    List<Tache> findByNomContainingIgnoreCase(String nom);

    // Filtrer par état et priorité
    List<Tache> findByEtatTacheAndPriorite(Status etat, Priorite priorite);

    // Recherche avancée (nom + état + priorité)
    @Query("SELECT t FROM Tache t WHERE " +
            "(:nom IS NULL OR LOWER(t.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) " +
            "AND (:etat IS NULL OR t.etatTache = :etat) " +
            "AND (:priorite IS NULL OR t.priorite = :priorite)")
    List<Tache> searchTaches(@Param("nom") String nom,
                             @Param("etat") Status etat,
                             @Param("priorite") Priorite priorite);
}
