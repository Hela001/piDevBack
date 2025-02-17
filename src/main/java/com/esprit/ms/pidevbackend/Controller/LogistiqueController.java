package com.esprit.ms.pidevbackend.Controller;

import com.esprit.ms.pidevbackend.Entities.Categorie;
import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.LigneCommande;
import com.esprit.ms.pidevbackend.Entities.Materiel;
import com.esprit.ms.pidevbackend.Repo.LigneCommandeRepo;
import com.esprit.ms.pidevbackend.Services.ILogistiqueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Api/logistique") // Consistent lowercase naming
@CrossOrigin(origins = "http://localhost:4200")
public class LogistiqueController {

    private final ILogistiqueService logistiqueService;
    LigneCommandeRepo ligneCommandeRepository;

    @PostMapping("/ajouterMateriel")
    public Materiel ajouterMateriel(@RequestBody Materiel materiel) {
        return logistiqueService.ajouterMateriel(materiel);
    }

    @GetMapping("/getAllMateriel")
    public List<Materiel> getAllMateriel() {
        return logistiqueService.getAllMateriel();
    }

    @GetMapping("/getMaterielByCategorie")
    public List<Materiel> getMaterielByCategorie(@RequestParam String categorie){
        return logistiqueService.getMaterielByCategorie(Categorie.valueOf(categorie));
    }

    @GetMapping("/getMaterielById/{id}")
    public Materiel getMaterielById(@PathVariable Long id) {
        return logistiqueService.getMaterielById(id);
    }

    @DeleteMapping("/deleteMateriel/{id}")
    public void deleteMateriel(@PathVariable Long id) {
        logistiqueService.supprimerMateriel(id);
    }

    @PutMapping("/modifierMateriel/{id}")
    public void modifierMateriel(@PathVariable Long id, @RequestBody Materiel materiel) {
        logistiqueService.modifierMateriel(id, materiel);
    }

    //-----------LigneCommande----
    @PostMapping("/ajouterLigneCommande")
    public ResponseEntity<LigneCommande> ajouterLigneCommande(@RequestBody LigneCommande ligneCommande) {
        LigneCommande savedLigneCommande = logistiqueService.ajouterLigneCommande(ligneCommande);
        return ResponseEntity.ok(savedLigneCommande); // Retourne la ligne créée avec ID
    }

    @DeleteMapping("/supprimerLigneCommande/{id}")
    public void supprimerLigneCommande(@PathVariable Long id){
        logistiqueService.supprimerLigneCommande(id);
    }
    @DeleteMapping("/supprimerLignesCommandeSansIdCommande")
    public void supprimerLignesSansCommande(){
        logistiqueService.supprimerLignesSansCommande();
    }
    @GetMapping("/lignesCommande/{idCommande}")
    public List<LigneCommande> getLignesCommandeByCommande(@PathVariable Long idCommande) {
        List<LigneCommande> lignesCommande = logistiqueService.getLignesCommandeByCommande(idCommande);
        System.out.println("Nombre de lignes récupérées : " + lignesCommande.size());

        return lignesCommande;
    }

    @PutMapping("/modifierLigneCommande/{id}")
    public ResponseEntity<LigneCommande> modifierLigneCommande(
            @PathVariable Long id,
            @RequestBody LigneCommande ligneCommande) {
        logistiqueService.modifierLigneCommande(id, ligneCommande);
        return ResponseEntity.ok(ligneCommande);
    }

    @GetMapping("getAllLigneCommande")
    public List<LigneCommande> getAllLigneCommande(){
        return logistiqueService.getAllLigneCommande();
    }

    //-------------Commande
    @PostMapping("/ajouterCommande")
    public ResponseEntity<?> ajouterCommande(@RequestBody Commande commande) {
        try {
            Commande savedCommande = logistiqueService.ajouterCommande(commande);
            return ResponseEntity.ok(savedCommande);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la commande.");
        }
    }

    @GetMapping("/getAllCommande")
    public List<Commande> getAllCommande(){
        return logistiqueService.getAllCommande();
    }

}
