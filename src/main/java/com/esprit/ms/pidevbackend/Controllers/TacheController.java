package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Priorite;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Services.TacheServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TacheController {

    private final TacheServices tacheService;

    @Autowired
    public TacheController(TacheServices tacheService) {
        this.tacheService = tacheService;
    }

    // Permet de gérer les requêtes OPTIONS pour CORS
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }

    // Récupérer toutes les tâches
    @GetMapping
    public ResponseEntity<List<Tache>> getAllTaches() {
        List<Tache> taches = tacheService.getAllTaches();
        return taches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(taches);
    }

    // Récupérer une tâche par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable Long id) {
        Tache tache = tacheService.getTacheById(id);
        return tache != null ? ResponseEntity.ok(tache) : ResponseEntity.notFound().build();
    }

    // Ajouter une tâche à une mission spécifique
    @PostMapping("/mission/{missionId}")
    public ResponseEntity<Tache> addTache(@PathVariable long missionId, @RequestBody Tache tache) {
        Tache createdTache = tacheService.addTache(missionId, tache);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        Tache updatedTache = tacheService.updateTache(id, tache);
        return updatedTache != null ? ResponseEntity.ok(updatedTache) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        try {
            boolean deleted = tacheService.deleteTache(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public List<Tache> searchTaches(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Status etat,
            @RequestParam(required = false) Priorite priorite) {
        return tacheService.searchTaches(nom, etat, priorite);
    }


    // Récupérer toutes les tâches d'une mission spécifique
    @GetMapping("/mission/{missionId}")
    public ResponseEntity<List<Tache>> getTasksByMission(@PathVariable long missionId) {
        List<Tache> taches = tacheService.getTasksByMission(missionId);
        return taches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(taches);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Tache> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        Tache updatedTache = tacheService.changerStatutTache(id, status);
        return ResponseEntity.ok(updatedTache); // Renvoie l'objet de la tâche mise à jour
    }








}
