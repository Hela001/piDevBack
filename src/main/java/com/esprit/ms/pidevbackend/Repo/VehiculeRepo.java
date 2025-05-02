package com.esprit.ms.pidevbackend.Repo;

import com.esprit.ms.pidevbackend.Entities.TypeVehicule;
import com.esprit.ms.pidevbackend.Entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepo extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findAllByTypeVehicule(TypeVehicule type);

    Vehicule findByIdChauffeur(Long idChauffeur);

    List<Vehicule> findByDisponibleFalse();
}
