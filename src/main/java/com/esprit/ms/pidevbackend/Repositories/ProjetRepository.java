package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet,Long> {
    // Rechercher un projet par son nom (partiel)
    List<Projet> findByNomContainingIgnoreCase(String nom);

    // Rechercher un projet par statut
    List<Projet> findByStatus(Status status);

    // Recherche avancée combinant nom et statut
    @Query("SELECT p FROM Projet p WHERE " +
            "(:nom IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) " +
            "AND (:status IS NULL OR p.status = :status)")
    List<Projet> searchProjet(@Param("nom") String nom, @Param("status") Status status);
}
