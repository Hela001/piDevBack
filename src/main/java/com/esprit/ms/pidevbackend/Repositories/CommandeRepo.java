package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepo extends JpaRepository<Commande,Long> {
}
