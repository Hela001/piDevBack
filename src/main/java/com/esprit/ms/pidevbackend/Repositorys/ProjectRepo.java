package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Projet, Long> {
    @Query("SELECT p.NomProjet, COUNT(i.idINS) FROM Projet p " +
            "LEFT JOIN Inspection i ON p.idProjet = i.projet.idProjet " +
            "GROUP BY p.NomProjet")
    List<Object[]> getInspectionCountByProject();
}
