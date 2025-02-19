package com.esprit.ms.pidevbackend.Controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Services.IFicheDePaieService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/ficheDePaie")
@Tag(name = "Gestion Fiche De Paie")
public class Fiche_De_PaiesController {
    @Autowired
    IFicheDePaieService iFicheDePaieService;

    @Operation(description="Ajouter une fiche de paie dans la base de données")
    @PostMapping("/add")
    public Fiche_de_paie addFicheDePaie(@RequestBody Fiche_de_paie ficheDePaie) {
        return iFicheDePaieService.addFicheDePaie(ficheDePaie);
    }

    @Operation(description="Récupérer une fiche de paie par ID")
    @GetMapping("/get/{id}")
    public Fiche_de_paie getFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        return iFicheDePaieService.getFicheDePaieById(idBulletinPaie);
    }

    @Operation(description="Récupérer toutes les fiches de paie")
    @GetMapping("/getAll")
    public List<Fiche_de_paie> getAllFichesDePaie() {
        return iFicheDePaieService.getAllFichesDePaie();
    }

    @Operation(description="Supprimer une fiche de paie par ID")
    @DeleteMapping("/delete/{id}")
    public void deleteFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        iFicheDePaieService.deleteFicheDePaie(idBulletinPaie);
    }

    @Operation(description="Modifier une fiche de paie")
    @PutMapping("/modify")
    public Fiche_de_paie modifyFicheDePaie(@RequestBody Fiche_de_paie ficheDePaie) {
        return iFicheDePaieService.updateFicheDePaie(ficheDePaie);
    }

}
