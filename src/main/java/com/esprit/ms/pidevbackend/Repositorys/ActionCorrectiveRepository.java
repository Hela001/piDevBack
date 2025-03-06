package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionCorrectiveRepository extends JpaRepository<ActionCorrective, Long> {

    @Query("SELECT a.statusActionCorrective, COUNT(a) FROM ActionCorrective a GROUP BY a.statusActionCorrective")
    List<Object[]> findCorrectiveActionStats();
}
