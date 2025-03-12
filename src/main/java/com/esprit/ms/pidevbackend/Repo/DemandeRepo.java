package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepo extends JpaRepository<Demande, Long> {
    List<Demande> findByIdUser(Long idUser);
}
