package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Services.TacheServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TacheController {

    @Autowired
    private TacheServices tacheService;

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Tache>> getAllTaches() {
        List<Tache> taches = tacheService.getAllTaches();
        if (taches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(taches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable Long id) {
        Tache tache = tacheService.getTacheById(id);
        if (tache == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tache);
    }

    @PostMapping("/{idM}")
    public ResponseEntity<Tache> addTache(@RequestBody Tache tache, @PathVariable("idM") long idM) {
        Tache createdTache = tacheService.addTache(tache, idM);
        return new ResponseEntity<>(createdTache, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        Tache updatedTache = tacheService.updateTache(id, tache);
        if (updatedTache == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTache);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTache(@PathVariable Long id) {
        tacheService.deleteTache(id);
        return ResponseEntity.ok("Tâche supprimée avec succès.");
    }
}
