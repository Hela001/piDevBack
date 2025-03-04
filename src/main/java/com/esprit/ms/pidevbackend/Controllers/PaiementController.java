package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Services.IPaiemetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Api/paiement")

public class PaiementController {
    @Autowired
    IPaiemetService iPaiemetService ;


    @PostMapping("addPaiement/{id}")
    public ResponseEntity<?> addPaiement(@RequestBody Paiement paiement, @PathVariable("id") Long idFacture) {
        // Vérifie que le montant reçu est valide (positif)
        if (paiement.getMontant() <= 0) {
            return ResponseEntity.badRequest().body("Montant invalide.");
        }

        // Enregistrement du paiement
        iPaiemetService.addPaiement(paiement, idFacture);
        return ResponseEntity.ok(paiement);
    }


    @Operation(description="Récupérer paiement par ID")
    @GetMapping("/{id}")
    public Paiement getPaiement(@PathVariable("id") Long idPaiement) {
        return iPaiemetService.getPaiement(idPaiement);
    }

    @Operation(description="Récupérer toutes les paiements")
    @GetMapping
    public List<Paiement> getAllPaiement() {
        return iPaiemetService.getAllPaiement();
    }

    @Operation(description="Supprimer un paiemnt par ID")
    @DeleteMapping("/{id}")
    public void deletePaiement(@PathVariable("id") Long idPaiement) {
        iPaiemetService.deletePaiement(idPaiement);
    }

    @Operation(description="Modifier paiement")
    @PutMapping
    public Paiement modifyPaiement(@RequestBody Paiement p) {
        return iPaiemetService.modifyPaiement(p);
    }


}

