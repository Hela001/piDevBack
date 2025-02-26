package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Services.IMissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MissionController {

    @Autowired
    private IMissionServices iMissionServices;

    // Gérer les requêtes OPTIONS pour le pré-vol CORS
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    // Récupérer toutes les missions
    @GetMapping
    public ResponseEntity<List<Mission>> getAllMissions() {
        List<Mission> missions = iMissionServices.getAllMissions();
        return missions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(missions);
    }

    // Récupérer une mission par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        Mission mission = iMissionServices.getMissionById(id);
        return mission != null ? ResponseEntity.ok(mission) : ResponseEntity.notFound().build();
    }

    // Ajouter une mission à un projet spécifique
    @PostMapping("/projet/{projetId}")
    public ResponseEntity<Mission> ajouterMission(@PathVariable Long projetId, @RequestBody Mission mission) {
        Mission nouvelleMission = iMissionServices.createMission(mission, projetId);
        return ResponseEntity.ok(nouvelleMission);
    }

    // Mettre à jour une mission existante
    @PutMapping("/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long id, @RequestBody Mission mission) {
        Mission updatedMission = iMissionServices.updateMission(id, mission);
        return updatedMission != null ? ResponseEntity.ok(updatedMission) : ResponseEntity.notFound().build();
    }

    // Supprimer une mission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        return iMissionServices.deleteMission(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Récupérer les missions d'un projet par son ID
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<Mission>> getMissionsByProjetId(@PathVariable Long projetId) {
        List<Mission> missions = iMissionServices.findByProjetId(projetId);
        return missions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(missions);
    }
}
