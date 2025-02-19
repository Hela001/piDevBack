package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Services.TacheServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
public class TacheController {

    @Autowired
    private TacheServices tacheService;

    @GetMapping("/getAll")
    public List<Tache> getAllTaches() {
        return tacheService.getAllTaches();
    }

    @GetMapping("/getTache/{id}")
    public Tache getTacheById(@PathVariable Long id) {
        return tacheService.getTacheById(id);
    }

    @PostMapping("/addTache")
    public Tache addTache(@RequestBody Tache tache) {
        return tacheService.addTache(tache);
    }

    @PutMapping("/updateTache/{id}")
    public Tache updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        return tacheService.updateTache(id, tache);
    }

    @DeleteMapping("/deleteTache/{id}")
    public ResponseEntity<String> deleteTache(@PathVariable Long id) {
        tacheService.deleteTache(id);
        return ResponseEntity.ok("Tâche supprimée avec succès.");
    }
}