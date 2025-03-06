package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Inspecteur;
import com.esprit.ms.pidevbackend.Entities.Inspection;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Services.ServiceInspecteur;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/Inspecteur")
@CrossOrigin(origins = "http://localhost:4200")
public class InspecteurController {
@Autowired
    ServiceInspecteur inspecteurService;


    @PostMapping("Addininspecteur")
    public ResponseEntity<Inspecteur> createInspecteur(@RequestBody Inspecteur inspecteur) {
        Inspecteur savedInspecteur = inspecteurService.saveOrUpdateInspecteur(inspecteur);
        return new ResponseEntity<>(savedInspecteur, HttpStatus.CREATED);
    }


    @GetMapping("/getAllInspecteurs")
    public List<Inspecteur> getAllInspecteurs(){
        return inspecteurService.getAllInspecteurs();
    }


    @GetMapping("/getInspecteurById{id}")
    public ResponseEntity<Inspecteur> getInspecteurById(@PathVariable Long id) {
        Optional<Inspecteur> inspecteur = inspecteurService.getInspecteurById(id);
        return inspecteur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/{inspecteurId}/inspections")
    public ResponseEntity<Inspecteur> addInspectionsToInspecteur(
            @PathVariable Long inspecteurId,
            @RequestBody List<Inspection> inspections,
            @RequestParam Long projetId) {

        try {
            // Appeler le service pour ajouter les inspections
            Inspecteur updatedInspecteur = inspecteurService.addInspectionsToInspecteur(inspecteurId, inspections, projetId);

            // Retourner la réponse avec l'inspecteur mis à jour
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedInspecteur);
        } catch (EntityNotFoundException ex) {
            // Gérer les erreurs si l'inspecteur ou le projet n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Gérer les autres exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Mettre à jour un inspecteur
    @PutMapping("/updateInspecteur{id}")
    public ResponseEntity<Inspecteur> updateInspecteur(@PathVariable Long id, @RequestBody Inspecteur inspecteurDetails) {
        Optional<Inspecteur> optionalInspecteur = inspecteurService.getInspecteurById(id);
        if (optionalInspecteur.isPresent()) {
            Inspecteur inspecteurToUpdate = optionalInspecteur.get();
            inspecteurToUpdate.setNomInspecteur(inspecteurDetails.getNomInspecteur());
            inspecteurToUpdate.setAdresseInspecteur(inspecteurDetails.getAdresseInspecteur());
            inspecteurToUpdate.setTelephoneInspecteur(inspecteurDetails.getTelephoneInspecteur());
            inspecteurToUpdate.setEmailInspecteur(inspecteurDetails.getEmailInspecteur());
            // Mettez à jour d'autres champs si nécessaire

            Inspecteur updatedInspecteur = inspecteurService.saveOrUpdateInspecteur(inspecteurToUpdate);
            return new ResponseEntity<>(updatedInspecteur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un inspecteur
    @DeleteMapping("/deleteInspecteur{id}")
    public void deleteInspecteur(@PathVariable Long id) {
        inspecteurService.deleteInspecteur(id);

    }

}
