package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByProjet_IdProjet(Long projetId);  // Correct référence à l'ID du projet
    @Query("SELECT m FROM Mission m WHERE m.projet.idProjet = :projetId " +
            "AND (:etatMission IS NULL OR m.etatMission = :etatMission) " +
            "AND (:searchText IS NULL OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    List<Mission> searchMissions(
            @Param("projetId") Long projetId,
            @Param("etatMission") Status etatMission,
            @Param("searchText") String searchText);
}
