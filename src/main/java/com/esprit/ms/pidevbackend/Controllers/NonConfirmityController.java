package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Repositorys.NonConformiteRepository;
import com.esprit.ms.pidevbackend.Services.NonConfirmityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Nonconform")
@CrossOrigin(origins = "http://localhost:4200")
public class NonConfirmityController {
    @Autowired
    NonConfirmityService nonConfirmityService;

    @Autowired
    private NonConformiteRepository nonConformiteRepository;


    @GetMapping("getAllNonConfirmity")
    public List<NonConfirmity> getAll() {

        return nonConfirmityService.getAllNonConfirmity();
    }

    @PostMapping("/addNonConfirmity")
    public ResponseEntity<NonConfirmity> addNonConformity(@RequestBody NonConfirmity nonConformity) {
        System.out.println("🛠️ Données reçues : " + nonConformity);
        NonConfirmity saved = nonConfirmityService.addNonConfirmity(nonConformity);
        System.out.println("✅ Donnée enregistrée : " + saved);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("delete/{id}")
    public void deletenonConfirmity(@PathVariable Long id) {
        nonConfirmityService.deleteNonConfirmity(id);
    }

    @PostMapping("/{id}/addActionCorrective")
    public ResponseEntity<NonConfirmity> addActionCorrective(
            @PathVariable Long id,
            @RequestBody ActionCorrective actionCorrective) {
        NonConfirmity updatedNonConfirmity = nonConfirmityService.addActionCorrective(id, actionCorrective);
        return ResponseEntity.ok(updatedNonConfirmity);
    }


    @PutMapping("updatenonConfirmity")
    public NonConfirmity updateNonConfirmity(@RequestBody NonConfirmity nonConfirmity)
    {

        return nonConfirmityService.updateNonConfirmity(nonConfirmity);
    }
    @PostMapping("/{id}/addAction")
    public ResponseEntity<NonConfirmity> addActionToNonConformity(
            @PathVariable Long id,
            @RequestBody ActionCorrective actionCorrective) {

        NonConfirmity updatedNonConfirmity = nonConfirmityService.addActionCorrective(id, actionCorrective);
        return ResponseEntity.ok(updatedNonConfirmity);
    }

    @GetMapping("getNonConfirmityById/{id}")
    public NonConfirmity getNonConfirmityById(@PathVariable Long id) {

        return nonConfirmityService.getNonConfirmityById(id);
    }
    @GetMapping("/{id}/getActionsCorrectives")
    public ResponseEntity<List<ActionCorrective>> getActionsCorrectivesByNonConfirmityId(@PathVariable Long id) {
        List<ActionCorrective> actionsCorrectives = nonConfirmityService.getActionsCorrectivesByNonConfirmityId(id);
        if (actionsCorrectives != null && !actionsCorrectives.isEmpty()) {
            return ResponseEntity.ok(actionsCorrectives);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourne un 404 si pas d'actions correctives
        }
    }

}
