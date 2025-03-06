package com.esprit.ms.pidevbackend.Controllers;


import com.esprit.ms.pidevbackend.Entities.Inspection;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Services.InspectionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/Qualite")
@CrossOrigin(origins = "http://localhost:4200")
public class InspectionController {
    @Autowired
    InspectionService inspectionService;
    @GetMapping("getAllInspections")
    public List<Inspection> getAll() {
        return inspectionService.getAllInspection();
    }
    @PostMapping("/ajouterInspection")
    public Inspection addInspection(@RequestBody Inspection inspection) {
        System.out.println("Inspection reçue : " + inspection);
        // Vérifiez ici que idInspecteur et idProjet sont bien présents
        if (inspection.getIdInspecteur() == null || inspection.getIdProjet() == null) {
            throw new IllegalStateException("L'inspecteur ou le projet n'est pas spécifié !");
        }
        return inspectionService.addInspection(inspection);
    }


    @PostMapping("/addNonConformite/{id}")
    public ResponseEntity<Inspection> addNonConformityToInspection(
            @PathVariable Long id,
            @RequestBody NonConfirmity nonConfirmity) {
        Inspection updatedInspection = inspectionService.addNonConformiteToInspection(id,nonConfirmity) ;
        return ResponseEntity.ok(updatedInspection);
    }


    @GetMapping("/{idINS}/nonConformities")
    public List<NonConfirmity> getNonConformities(@PathVariable Long idINS) {
        return inspectionService.getNonConformitiesByInspection(idINS);
    }


    @DeleteMapping("delete/{id}")
    public void deleteInspection(@PathVariable Long id) {

        inspectionService.deleteInspection(id);

    }
    @PutMapping("updateInspection")
    public Inspection updateInspection(@RequestBody Inspection inspection )
    {
        return inspectionService.updateInspection(inspection);
    }


    @GetMapping("getInspectionById/{id}")
    public Inspection getInspectionById(@PathVariable Long id) {

        return inspectionService.getInspectionById(id);
    }

    @PutMapping("/updateNonConformite/{idInspection}/{idNonConformity}")
    public ResponseEntity<NonConfirmity> updateNonConformity(
            @PathVariable Long idInspection,
            @PathVariable Long idNonConformity,
            @RequestBody NonConfirmity nonConfirmity) {

        NonConfirmity updatedNonConfirmity = inspectionService.updateNonConformity(idInspection, idNonConformity, nonConfirmity);

        if (updatedNonConfirmity != null) {
            return ResponseEntity.ok(updatedNonConfirmity);
        } else {
            return ResponseEntity.notFound().build(); // Si la non-conformité ou l'inspection n'est pas trouvée
        }
    }

}



