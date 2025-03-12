package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.LigneCommande;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LigneCommandeRepo extends JpaRepository<LigneCommande, Long> {
    List<LigneCommande> findByAffecteFalse();

    @Modifying
    @Transactional
    @Query("DELETE FROM LigneCommande lc WHERE lc.commande IS NULL")
    void deleteLigneCommandeWithoutCommande();

    @Query("SELECT lc FROM LigneCommande lc WHERE lc.commande.idCommande = :idCommande")
    List<LigneCommande> findByCommandeId(@Param("idCommande") Long idCommande);


   }

