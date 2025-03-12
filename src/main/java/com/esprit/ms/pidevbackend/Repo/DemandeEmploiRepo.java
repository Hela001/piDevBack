package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.DemandeEmploi;
import com.esprit.ms.pidevbackend.Entities.StatusDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DemandeEmploiRepo extends JpaRepository<DemandeEmploi, Long> {


    List<DemandeEmploi> findByStatus(StatusDemande statusDemande);


    @Query("SELECT e.typeEntretient, d.nom, d.prenom, d.specialite, e.lienMeet FROM DemandeEmploi d JOIN d.entretien e WHERE e.dateEntretien = :dateEntretien")
    List<Object[]> findDemandeByDateEntretien(@Param("dateEntretien") LocalDate dateEntretien);

}
