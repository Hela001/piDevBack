package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Categorie;
import com.esprit.ms.pidevbackend.Entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterielRepo extends JpaRepository<Materiel, Long> {
    List<Materiel> findAllByCategorie(Categorie categorie);
}
