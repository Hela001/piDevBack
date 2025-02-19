package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache,Long> {
}
