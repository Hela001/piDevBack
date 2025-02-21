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

    @Operation(description="recuperer le rapport de la base de donné")
    @PostMapping("add")
    public RapportFinancier AjoutRapportFinancier (@RequestBody RapportFinancier RF) {
        return iRapportFinService.addRapport(RF);
    }

    @GetMapping("getRapport/{id}")
    public RapportFinancier GetRapportById(@PathVariable ("id") Long idRapport){
        return iRapportFinService.getRapportById(idRapport);
    }


    @GetMapping("getAll")
    public List<RapportFinancier> getAllRapport(){
        return iRapportFinService.getAllRapport();
    }


    @DeleteMapping("deleteRapport/{id}")
    public void DeleteRapport (@PathVariable("id") Long idRapport) {
        iRapportFinService.deleteRapport(idRapport);
    }


    @PutMapping("/modifyRapport")
    public RapportFinancier updateRapport(@RequestBody RapportFinancier Rp) {
        return iRapportFinService.modifyRapport(Rp);
    }
}



