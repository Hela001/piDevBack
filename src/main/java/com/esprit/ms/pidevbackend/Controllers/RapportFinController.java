package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Entities.RapportFinancier;
import com.esprit.ms.pidevbackend.Services.IRapportFinService;
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
@RequestMapping("Api/rapportfinancier")
@Tag(name = "Gestion du rapporting ")
public class RapportFinController {
    @Autowired
    IRapportFinService iRapportFinService;
    @Operation(summary = "Générer un rapport financier pour un utilisateur")
    @GetMapping("/{id}/{budget}")
    public RapportFinancier generateRapport(
            @PathVariable("id") Long idUtilisateur,
            @PathVariable("budget") double budget) {
        return iRapportFinService.generateRapport(idUtilisateur, budget);
    }

}



