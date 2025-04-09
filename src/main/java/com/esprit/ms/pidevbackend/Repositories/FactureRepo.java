package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FactureRepo extends JpaRepository<Facture,Long> {
    long countByStatus(String status);
        Set<Facture> findByIdUtilisateur(Long idUtilisateur);

}
