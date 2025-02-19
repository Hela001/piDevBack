package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FicheDePaieRepo  extends JpaRepository<Fiche_de_paie,Long> {
}
