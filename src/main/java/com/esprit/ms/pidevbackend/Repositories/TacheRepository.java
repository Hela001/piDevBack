package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Tache;
import jakarta.persistence.JoinColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Long> {
    List<Tache> findByMission_idMission(long missionId);;
}
