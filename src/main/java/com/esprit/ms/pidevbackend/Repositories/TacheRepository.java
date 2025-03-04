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
    List<Tache> findByNomContainingIgnoreCase(String nom);
    List<Tache> findByEtatTacheAndPriorite(Status etat, Priorite priorite);

    @Query("SELECT t FROM Tache t WHERE " +
            "(:nom IS NULL OR LOWER(t.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) " +
            "AND (:etat IS NULL OR t.etatTache = :etat) " +
            "AND (:priorite IS NULL OR t.priorite = :priorite)")
    List<Tache> searchTaches(@Param("nom") String nom,
                             @Param("etat") Status etat,
                             @Param("priorite") Priorite priorite);
}
