package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NonConformiteRepository extends JpaRepository<NonConfirmity, Long> {

    // Récupérer les statistiques par type de non-conformité
    @Query("SELECT n.typeNonConfirm, COUNT(n) FROM NonConfirmity n GROUP BY n.typeNonConfirm")
    List<Object[]> findNonConformityTypesStats();

    // Récupérer les statistiques par statut de non-conformité
    @Query("SELECT n.statutNonConfirm, COUNT(n) FROM NonConfirmity n GROUP BY n.statutNonConfirm")
    List<Object[]> findStatutNonConformityStats();

}