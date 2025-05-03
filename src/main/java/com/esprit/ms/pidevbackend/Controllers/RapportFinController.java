package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.RapportFinancier;
import com.esprit.ms.pidevbackend.Services.IRapportFinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/rapportfinancier")
@Tag(name = "Gestion du rapporting")
public class RapportFinController {

    private static final Logger logger = LoggerFactory.getLogger(RapportFinController.class);

    @Autowired
    IRapportFinService iRapportFinService;

    @PostMapping("/generer/{idUtilisateur}")
    public ResponseEntity<RapportFinancier> genererRapport(@PathVariable Long idUtilisateur) {
        logger.info("Demande de génération de rapport pour l'utilisateur: {}", idUtilisateur);
        RapportFinancier rapport = iRapportFinService.genererRapport(idUtilisateur);
        return ResponseEntity.ok(rapport);
    }

    @GetMapping
    public ResponseEntity<List<RapportFinancier>> getAllRapports() {
        logger.info("Récupération de tous les rapports");
        List<RapportFinancier> rapports = iRapportFinService.getAllRapports();
        return ResponseEntity.ok(rapports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportFinancier> getRapport(@PathVariable Long id) {
        logger.info("Récupération du rapport avec l'ID: {}", id);
        RapportFinancier rapport = iRapportFinService.getRapportById(id);
        return ResponseEntity.ok(rapport);
    }
}