package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionRepo extends JpaRepository<Inspection, Long> {
    List<Inspection> findByIdINS(Long idINS);

    // Récupérer les statistiques des inspections par projet
    @Query("SELECT i.projet.NomProjet, COUNT(i) FROM Inspection i GROUP BY i.projet.NomProjet")
    List<Object[]> findInspectionsByProjectStats();

    @Query("SELECT i.statusInspection, COUNT(i) FROM Inspection i GROUP BY i.statusInspection")
    List<Object[]> countInspectionsByStatus();

    // Statistiques par type
    @Query("SELECT i.typeInspection, COUNT(i) FROM Inspection i GROUP BY i.typeInspection")
    List<Object[]> countInspectionsByType();

}


