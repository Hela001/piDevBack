package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Repositorys.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {
    @Autowired
    ProjectRepo projectRepo;
    public List<Projet>getAllProjets() {
        return projectRepo.findAll();
    }

    public Optional<Projet> getProjetById(Long id) {
        return projectRepo.findById(id);
    }

    public Projet createProjet(Projet projet) {
        return projectRepo.save(projet);
    }

    public Projet updateProjet(Long id, Projet projetDetails) {
        return projectRepo.findById(id).map(projet -> {
            projet.setNomProjet(projetDetails.getNomProjet());
            return projectRepo.save(projet);
        }).orElseThrow(() -> new RuntimeException("Projet non trouvé"));
    }

    public void deleteProjet(Long id) {
        projectRepo.deleteById(id);
    }



}
