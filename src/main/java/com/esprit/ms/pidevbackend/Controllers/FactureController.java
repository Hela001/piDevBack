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
    IFactureService iFactureService;

    @Operation(description="recuperer le bloc de la base de donné")
    @PostMapping
    public Facture AjoutFacture (@RequestBody Facture facture) {
        return iFactureService.addFacture(facture);
    }

    @GetMapping("/{id}")
    public Facture GetFactureById(@PathVariable ("id") Long idFacture){
        return iFactureService.getFactureById(idFacture);
    }


    @GetMapping
    public List<Facture> getAllFactures(){
        return iFactureService.getAllFactures();
    }


    @DeleteMapping("/{id}")
    public void DeleteFacture (@PathVariable("id") Long idFacture) {
        iFactureService.deleteFacture(idFacture);
    }


    @PutMapping
    public Facture updateFacture(@RequestBody Facture f) {
        return iFactureService.updateFacture(f);
    }
}

