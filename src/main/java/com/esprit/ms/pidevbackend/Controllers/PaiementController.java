package com.esprit.ms.pidevbackend.Controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Services.IPaiemetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/paiement")
@Tag(name = "Gestion de paiement")

public class PaiementController {
    @Autowired
    IPaiemetService iPaiemetService ;

    @Operation(description="Ajouter un paiement dans la base de données")
    @PostMapping("/add")
    public Paiement addPaiement(@RequestBody Paiement p) {
        return iPaiemetService.addPaiement(p);
    }

//    @Operation(description="Récupérer paiement par ID")
//    @GetMapping("/get/{id}")
//    public Paiement getPaiement(@PathVariable("id") Long idPaiement) {
//        return idPaiement.getPaiement(idPaiement);
//    }

    @Operation(description="Récupérer toutes les paiements")
    @GetMapping("/getAll")
    public List<Paiement> getAllPaiement() {
        return iPaiemetService.getAllPaiement();
    }

//    @Operation(description="Supprimer un paiemnt par ID")
//    @DeleteMapping("/delete/{id}")
//    public void deletePaiement(@PathVariable("id") Long idPaiement) {
//        idPaiement.deletePaiement(idPaiement);
//    }

    @Operation(description="Modifier paiement")
    @PutMapping("/modify")
    public Paiement modifyPaiement(@RequestBody Paiement p) {
        return iPaiemetService.modifyPaiement(p);
    }
}

