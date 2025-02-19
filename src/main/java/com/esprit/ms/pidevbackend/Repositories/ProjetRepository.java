package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet,Long> {
}
