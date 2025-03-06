package com.esprit.ms.pidevbackend.Controllers;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Services.IFicheDePaieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/ficheDePaie")
@Tag(name = "Gestion Fiche De Paie")
public class Fiche_De_PaiesController {
    @Autowired
    IFicheDePaieService iFicheDePaieService;

    @Operation(description = "Ajouter une fiche de paie dans la base de données")
    @PostMapping
    public Fiche_de_paie addFicheDePaie(@RequestBody Fiche_de_paie ficheDePaie) {
        return iFicheDePaieService.addFicheDePaie(ficheDePaie);
    }

    @Operation(description = "Récupérer une fiche de paie par ID")
    @GetMapping("/{id}/details")
    public Fiche_de_paie getFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        return iFicheDePaieService.getFicheDePaieById(idBulletinPaie);
    }

    @Operation(description = "Récupérer toutes les fiches de paie")
    @GetMapping
    public List<Fiche_de_paie> getAllFichesDePaie() {
        return iFicheDePaieService.getAllFichesDePaie();
    }

    @Operation(description = "Supprimer une fiche de paie par ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        try {
            iFicheDePaieService.deleteFicheDePaie(idBulletinPaie);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Modifier une fiche de paie")
    @PutMapping("/{id}")
    public ResponseEntity<Fiche_de_paie> updateFicheDePaie(@PathVariable("id") Long idBulletinPaie, @RequestBody Fiche_de_paie ficheDePaie) {
        Fiche_de_paie updatedFiche = iFicheDePaieService.updateFicheDePaie(idBulletinPaie, ficheDePaie);
        return ResponseEntity.ok(updatedFiche);
    }

    @Operation(description = "Calculer le salaire d'une fiche de paie")
    @PostMapping("/{id}") // Unique sub-path
    public ResponseEntity<Fiche_de_paie> calculerSalaire(@PathVariable("id") Long idBulletinPaie) {
        Fiche_de_paie fiche = iFicheDePaieService.calculerSalaire(idBulletinPaie);
        if (fiche != null) {
            return ResponseEntity.ok(fiche);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Imprimer une fiche de paie")
    @GetMapping("/{id}/print")
    public void imprimerFiche(@PathVariable("id") Long idBulletinPaie, HttpServletResponse response) {
        iFicheDePaieService.imprimerFiche(idBulletinPaie, response);
    }
}