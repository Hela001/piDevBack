package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeRepo extends JpaRepository<LigneCommande, Long> {
    List<LigneCommande> findByAffecteFalse();
}
