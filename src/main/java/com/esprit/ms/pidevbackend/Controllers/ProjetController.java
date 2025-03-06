package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Services.ProjetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/Projet")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {
    @Autowired
    private ProjetService projetService;

    @GetMapping("/getAllProjets")
    public List<Projet> getAllProjets() {
        return projetService.getAllProjets();
    }

    @GetMapping("/getProjetById{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable Long id) {
        Optional<Projet> projet = projetService.getProjetById(id);
        return projet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addprojet")
    public Projet createProjet(@RequestBody Projet projet) {
        return projetService.createProjet(projet);
    }
    @DeleteMapping("/deleteProjet{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }
}

