package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntretienRepo extends JpaRepository<Entretien, Long> {
    List<Entretien> findByDateEntretien(LocalDate today);
}
