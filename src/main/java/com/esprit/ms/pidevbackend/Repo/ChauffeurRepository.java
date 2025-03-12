package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {
}
