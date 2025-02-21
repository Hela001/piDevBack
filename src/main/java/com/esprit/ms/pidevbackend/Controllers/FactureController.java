package com.esprit.ms.pidevbackend.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/facture")
@Tag(name = "Gestion Facturation")
public class FactureController {

    @Autowired
    private IFactureService iFactureService;

    @Operation(description = "Ajouter une facture")
    @PostMapping
    public Facture AjoutFacture(@RequestBody Facture facture) {
        return iFactureService.addFacture(facture);
    }

    @Operation(description = "Récupérer une facture par son ID")
    @GetMapping("/{id}")
    public Facture GetFactureById(@PathVariable("id") Long idFacture) {
        return iFactureService.getFactureById(idFacture);
    }

    @Operation(description = "Récupérer toutes les factures")
    @GetMapping
    public List<Facture> getAllFactures() {
        return iFactureService.getAllFactures();
    }

    @Operation(description = "Supprimer une facture par son ID")
    @DeleteMapping("/{id}")
    public void DeleteFacture(@PathVariable("id") Long idFacture) {
        iFactureService.deleteFacture(idFacture);
    }

    @PutMapping("/{id}")
    public Facture updateFacture(@PathVariable("id") Long idFacture, @RequestBody Facture facture) {
        return iFactureService.updateFacture(idFacture, facture);
    }
}