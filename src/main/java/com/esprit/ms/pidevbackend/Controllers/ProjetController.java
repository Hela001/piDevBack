package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Services.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    private ProjetServices projetService;

    // Obtenir tous les projets

    @GetMapping
    public List<Projet> getAllProjets() {
       return projetService.getAllProjets();
    }

    // Obtenir un projet par ID
    @GetMapping("/{id}")
    public Projet getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id);
    }

    // Ajouter un projet
    @PostMapping
    public Projet addProjet(@RequestBody Projet projet) {
        return projetService.addProjet(projet);
    }

    // Mettre à jour un projet
    @PutMapping("/{id}")
    public Projet updateProjet(@PathVariable Long id, @RequestBody Projet projet) {
        return projetService.updateProjet(id, projet);
    }

    // Supprimer un projet
    @DeleteMapping("/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }
}
