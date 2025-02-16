package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.LigneDemande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneDemandeRepo extends JpaRepository<LigneDemande, Long> {
}
