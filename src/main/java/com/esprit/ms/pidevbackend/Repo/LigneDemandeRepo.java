package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Demande;
import com.esprit.ms.pidevbackend.Entities.LigneDemande;
import com.esprit.ms.pidevbackend.Entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LigneDemandeRepo extends JpaRepository<LigneDemande, Long> {
    Optional<LigneDemande> findByDemandeAndMateriel(Demande demande, Materiel materiel);
    @Query("SELECT l.materiel.nomMateriel, SUM(l.Quantite) " +
            "FROM LigneDemande l " +
            "GROUP BY l.materiel.nomMateriel " +
            "ORDER BY SUM(l.Quantite) DESC")
    List<Object[]> findTopMateriels();
    @Query("SELECT m.categorie, SUM(ld.materiel.quantite) " +
            "FROM LigneDemande ld JOIN ld.materiel m " +
            "GROUP BY m.categorie " +
            "ORDER BY SUM(ld.materiel.quantite) DESC")
    List<Object[]> findMaterielsParCategorie();

}
