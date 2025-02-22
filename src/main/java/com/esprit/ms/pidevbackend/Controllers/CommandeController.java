package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import com.esprit.ms.pidevbackend.Services.iCommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/commande")
@Tag(name = "Gestion commande")
public class CommandeController {

    @Autowired
    private iCommandeService iCommandeService;

    @Operation(description = "Récupérer toutes les commandes")
    @GetMapping
    public List<Commande> getAllComm() {
        return iCommandeService.getAllComm();
    }

 @PostMapping("/{idCommande}/add-finance")
 public ResponseEntity<String> createFacture(@PathVariable Long idCommande) {
     // Logic to create a facture
     return ResponseEntity.ok("Facture créée avec succès pour la commande " + idCommande);
 }
}