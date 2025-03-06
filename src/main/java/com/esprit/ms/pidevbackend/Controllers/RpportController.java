package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.RapportQualite;
import com.esprit.ms.pidevbackend.Services.RapportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Rapport")
@CrossOrigin(origins = "http://localhost:4200")

public class RpportController {
    @Autowired
    RapportService rapportService;


    @GetMapping("getAllRapportQualite")
    public List<RapportQualite> getAll() {

        return rapportService.getAllRapportQualite();
    }
    @PostMapping("/generate/{inspectionId}")
    public ResponseEntity<RapportQualite> generateRapport(@PathVariable Long inspectionId) {
        System.out.println("🔍 ID reçu : " + inspectionId);
        try {
            RapportQualite rapport = rapportService.generateRapportForInspection(inspectionId);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Afficher l'erreur exacte
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("delete/{id}")
    public void deleteRapportQualite(@PathVariable Long id) {
        rapportService.deleteRapport(id);
    }

    @PutMapping("updateRapportQualite")
    public RapportQualite updateRapportQualite(@RequestBody RapportQualite rapportQualite) {

        return rapportService.updateRapportQualite(rapportQualite);
    }

    @GetMapping("/getRapportQualiteById/{id}")
    public ResponseEntity<RapportQualite> getRapportQualiteById(@PathVariable Long id) {
        // Vérification si l'ID est valide
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // ID invalide
        }

        // Récupérer le rapport depuis le service
        RapportQualite rapport = rapportService.getRapportQualiteId(id);

        // Si le rapport n'existe pas
        if (rapport == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Rapport non trouvé
        }

        return ResponseEntity.ok(rapport);
    }




    @PostMapping("/{idINS}/add")
    public ResponseEntity<RapportQualite> addRapport(
            @PathVariable Long idINS,
            @RequestParam(value = "Contenu") String Contenu,
            @RequestParam(value = "PhotoVideo", required = false) MultipartFile PhotoVideo,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateCreation) {

        try {
            RapportQualite rapport = rapportService.addrapport(idINS, Contenu, PhotoVideo, dateCreation);
            return ResponseEntity.ok(rapport);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{idR}/update")
    public ResponseEntity<RapportQualite> updateRapport(
            @PathVariable Long idR,
            @RequestParam(value = "Contenu", required = false) String Contenu,
            @RequestParam(value = "PhotoVideo", required = false) MultipartFile PhotoVideo,
            @RequestParam(value = "dateCreation", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateCreation) {

        try {
            RapportQualite updatedRapport = rapportService.updateRapportQualite(idR, Contenu, PhotoVideo, dateCreation);
            return ResponseEntity.ok(updatedRapport);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
