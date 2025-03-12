package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommandeRepo extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c WHERE c.idfournisseur = :idfournisseur")
    List<Commande> findByFournisseur(@Param("idfournisseur") Long idfournisseur);
    List<Commande> findByStatus(String statut);
    @Query("SELECT COUNT(c) FROM Commande c WHERE c.dateCreation = :dateCreation")
    Long countCommandesByDay(@Param("dateCreation") Date dateCreation);

    @Query("SELECT COUNT(c) FROM Commande c WHERE YEAR(c.dateCreation) = :year AND WEEK(c.dateCreation) = :week")
    Long countCommandesByWeek(@Param("year") int year, @Param("week") int week);

    @Query("SELECT COUNT(c) FROM Commande c WHERE YEAR(c.dateCreation) = :year AND MONTH(c.dateCreation) = :month")
    Long countCommandesByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(c) FROM Commande c WHERE YEAR(c.dateCreation) = :year")
    Long countCommandesByYear(@Param("year") int year);
    @Query("SELECT YEAR(c.dateCreation), SUM(c.prixTotal) FROM Commande c WHERE YEAR(c.dateCreation) IN (:year1, :year2, :year3) GROUP BY YEAR(c.dateCreation)")
    List<Object[]> findTotalCommandesByYear(@Param("year1") int year1, @Param("year2") int year2, @Param("year3") int year3);


}
