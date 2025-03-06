package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FicheDePaieRepo  extends JpaRepository<Fiche_de_paie,Long> {
    List<Fiche_de_paie> findAllByOrderByIdBulletinPaieDesc();

    @Query("SELECT f FROM Fiche_de_paie f WHERE f.nom = :nom ORDER BY f.datePaiement DESC")
    List<Fiche_de_paie> findByNom(@Param("nom") String nom);

    Set<Fiche_de_paie> findByIdUtilisateur(Long idUtilisateur);

}
