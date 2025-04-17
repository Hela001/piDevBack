package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Priorite;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Services.TacheServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TacheController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;  // Ajoute cette ligne pour l'injection


    private final TacheServices tacheService;

    @Autowired
    public TacheController(TacheServices tacheService) {
        this.tacheService = tacheService;
    }

    // Gère les requêtes OPTIONS pour CORS
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

    // Ajouter une tâche à une mission
    @PostMapping("/mission/{missionId}")
    public ResponseEntity<Tache> addTache(@PathVariable long missionId, @RequestBody Tache tache) {
        Tache createdTache = tacheService.addTache(missionId, tache);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    // Mettre à jour une tâche existante
    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        Tache updatedTache = tacheService.updateTache(id, tache);
        return updatedTache != null ? ResponseEntity.ok(updatedTache) : ResponseEntity.notFound().build();
    }

    // Supprimer une tâche
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        try {
            boolean deleted = tacheService.deleteTache(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Rechercher des tâches par nom, statut ou priorité
    @GetMapping("/search")
    public ResponseEntity<List<Tache>> searchTaches(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Status etat,
            @RequestParam(required = false) Priorite priorite) {
        List<Tache> result = tacheService.searchTaches(nom, etat, priorite);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    // Récupérer toutes les tâches d'une mission
    @GetMapping("/mission/{missionId}")
    public ResponseEntity<List<Tache>> getTasksByMission(@PathVariable long missionId) {
        List<Tache> taches = tacheService.getTasksByMission(missionId);
        return taches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(taches);
    }

    // Modifier le statut d'une tâche
    @PutMapping("/{id}/status")
    public ResponseEntity<Tache> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        Tache updatedTache = tacheService.changerStatutTache(id, status);
        return updatedTache != null ? ResponseEntity.ok(updatedTache) : ResponseEntity.notFound().build();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{id}/notify-update")
    public ResponseEntity<String> notifyTacheUpdate(@PathVariable Long id) {
        // Appeler la logique métier pour mettre à jour la tâche
        tacheService.notifyTaskUpdate(id);  // Appel à la logique métier (probablement une mise à jour de tâche)

        // Envoyer une notification via WebSocket
        simpMessagingTemplate.convertAndSend("/topic/taches", "Tâche mise à jour: " + id);

        return ResponseEntity.ok("Notification envoyée avec succès");
    }

}
