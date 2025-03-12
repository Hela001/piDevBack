package com.esprit.ms.pidevbackend.Controller;

import com.esprit.ms.pidevbackend.Entities.*;
import com.esprit.ms.pidevbackend.Repo.*;
import com.esprit.ms.pidevbackend.Services.IEntretienService;
import com.esprit.ms.pidevbackend.Services.ILogistiqueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/Api/logistique") // Consistent lowercase naming
@CrossOrigin(origins = "http://localhost:4200")
public class LogistiqueController {
    ChauffeurRepository chauffeurRepository;
    private final ILogistiqueService logistiqueService;
    private final MaterielRepo materielRepo;
    LigneDemandeRepo ligneDemandeRepo;
    DemandeRepo demandeRepo;
    VehiculeRepo vehiculeRepo;

    @PostMapping("/ajouterMateriel")
    public Materiel ajouterMateriel(@RequestBody Materiel materiel) {
        return logistiqueService.ajouterMateriel(materiel);
    }

    @GetMapping("/getAllMateriel")
    public List<Materiel> getAllMateriel() {
        return logistiqueService.getAllMateriel();
    }

    @GetMapping("/getMaterielByCategorie")
    public List<Materiel> getMaterielByCategorie(@RequestParam String categorie) {
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

    @GetMapping("/verifierQuantiteMateriel/{idDemande}")
    public ResponseEntity<Map<String, Object>> verifierQuantiteMateriel(@PathVariable Long idDemande) {
        Map<String, Object> response = logistiqueService.verifierQuantiteMateriel(idDemande);
        return ResponseEntity.ok(response);
    }

    //-----------LigneCommande----
    @PostMapping("/ajouterLigneCommande")
    public ResponseEntity<LigneCommande> ajouterLigneCommande(@RequestBody LigneCommande ligneCommande) {
        LigneCommande savedLigneCommande = logistiqueService.ajouterLigneCommande(ligneCommande);
        return ResponseEntity.ok(savedLigneCommande); // Retourne la ligne créée avec ID
    }

    @DeleteMapping("/supprimerLigneCommande/{id}")
    public void supprimerLigneCommande(@PathVariable Long id) {
        logistiqueService.supprimerLigneCommande(id);
    }

    @DeleteMapping("/supprimerLignesCommandeSansIdCommande")
    public void supprimerLignesSansCommande() {
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
    public List<LigneCommande> getAllLigneCommande() {
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
    public List<Commande> getAllCommande() {
        return logistiqueService.getAllCommande();
    }

    @DeleteMapping("/supprimerCommande/{id}")
    public void supprimerCommande(@PathVariable Long id) {
        logistiqueService.supprimerCommande(id);
    }

    @GetMapping("/getCommandeByFournisseur/{id}")
    public List<Commande> getCommandeByFournisseur(@PathVariable Long id) {
        return logistiqueService.getCommandeByFournisseur(id);
    }

    @GetMapping("/getCommandeByStatut")
    public List<Commande> getCommandeByStatut(String statut) {
        return logistiqueService.getCommandeByStatut(statut);
    }

    @PostMapping("/modifierCommande/{id}/{status}")
    public ResponseEntity<String> modifierCommande(
            @PathVariable Long id,
            @PathVariable Status status) {
        try {
            logistiqueService.modifierCommande(id, status);
            return ResponseEntity.ok("Statut de la commande mis à jour en " + status + ".");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/modifierCommandePrix/{idCommande}")
    public ResponseEntity<Void> modifierCommandePrix(@PathVariable Long idCommande) {
        logistiqueService.modifierCommandePrix(idCommande);
        return ResponseEntity.ok().build();
    }
    //----------------------Ligne Demande
    @PostMapping("/ajouterLigneDemande")
    public ResponseEntity<LigneDemande> ajouterLigneDemande(@RequestBody LigneDemande ligneDemande) {
        ligneDemande.setDemande(null); // La demande reste null
        LigneDemande nouvelleLigne = logistiqueService.ajouterLigneDemande(ligneDemande);
        return ResponseEntity.ok(nouvelleLigne);
    }

    @PutMapping("/modifierLigneDemande")
    public ResponseEntity<LigneDemande> modifierLigneDemande(@RequestBody LigneDemande ligneDemande) {
        LigneDemande ligneExistante = logistiqueService.getLigneDemandeById(ligneDemande.getIdLigneDemande());

        if (ligneExistante != null) {
            ligneExistante.setQuantite(ligneDemande.getQuantite());
            LigneDemande updatedLigne = logistiqueService.modifierLigneDemande(ligneExistante);
            return ResponseEntity.ok(updatedLigne);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getLigneDemandeByIDDemande/{idDemande}")
    public ResponseEntity<List<LigneDemande>> getLigneDemandeByIDDemande(@PathVariable Long idDemande) {
        try {
            List<LigneDemande> lignesDemande = logistiqueService.getLigneDemandeByIDDemande(idDemande);
            return ResponseEntity.ok(lignesDemande);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }
    @DeleteMapping("/deleteLigneDemandeById/{id}")
    public void deleteLigneDemandeById(@PathVariable Long id) {
        ligneDemandeRepo.deleteById(id);
    }


    //-----------------Demande
    @PostMapping("/ajouterDemande")
    public ResponseEntity<Demande> ajouterDemande(@RequestBody Demande demande) {
        try {
            demande.setDateDemande(new Date());
            demande.setStatus(Status.envoye);

            Demande savedDemande = logistiqueService.ajouterDemande(demande);
            return ResponseEntity.ok(savedDemande);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getDemandeById/{id}")
    public Demande getDemandeById(@PathVariable Long id){
        return logistiqueService.getDemandeById(id);
    }
    @GetMapping("/getAllDemande")
    public List<Demande> getAllDemande(){
        return logistiqueService.getAllDemande();
    }
    @GetMapping("/getDemandesByUser/{idUser}")
    public List<Demande> getDemandesByUser(@PathVariable Long idUser) {
        return logistiqueService.getDemandesByUser(idUser);
    }

    @DeleteMapping("/deleteDemande/{id}")
    public void deleteDemande(@PathVariable Long id) {
        demandeRepo.deleteById(id);
    }
    @PutMapping("/updateQuantiteLigneDemande/{idLigneDemande}")
    public ResponseEntity<LigneDemande> updateQuantiteLigneDemande(
            @PathVariable Long idLigneDemande,
            @RequestParam int nouvelleQuantite) {
        try {
            LigneDemande updatedLigne = logistiqueService.updateQuantiteLigneDemande(idLigneDemande, nouvelleQuantite);
            return ResponseEntity.ok(updatedLigne);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/updateStatusDemande/{idDemande}")
    public ResponseEntity<Demande> updateStatusDemande(
            @PathVariable Long idDemande,
            @RequestParam Status newStatus) {
        try {
            Demande updatedDemande = logistiqueService.updateStatusDemande(idDemande, newStatus);
            return ResponseEntity.ok(updatedDemande);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //----------------------Vehicule
    @PostMapping("/ajouterVehicule")
    public Vehicule ajouterVehicule(@RequestBody  Vehicule vehicule){
        return logistiqueService.ajouterVehicule(vehicule);
    }

    @GetMapping("/getAllVehicule")
    public List<Vehicule> getAllVehicule(){
        return logistiqueService.getAllVehicule();
    }

    @GetMapping("/getVehiculeByType")
    public List<Vehicule> getVehiculeByType(@RequestParam TypeVehicule type) {
        return logistiqueService.getVehiculeByType(type);
    }

    @DeleteMapping("/supprimerVehicule/{id}")
    public void supprimerVehicule(@PathVariable Long id){
        logistiqueService.supprimerVehicule(id);
    }

    @GetMapping("/getVehiculeById/{id}")
    public Vehicule getVehiculeById(@PathVariable Long id){
        return logistiqueService.getVehiculeById(id);
    }
    @PutMapping("/modifierVehicule/{id}")
    public void modifierVehicule(@PathVariable Long id,@RequestBody Vehicule vehicule) {
        logistiqueService.modifierVehicule(id , vehicule);
    }

    //------------Statistique---------------
    @GetMapping("/top-materiels")
    public List<TopMaterielDTO> getTopMateriels() {
        return logistiqueService.getTopMateriels();
    }

    @GetMapping("/categorie")
    public List<Object[]> getMaterielsParCategorie() {
        return logistiqueService.getMaterielsParCategorie();
    }


    // Endpoint pour obtenir le nombre de commandes passées par semaine
    @GetMapping("/commandes/semaine")
    public Long getCommandesByWeek(@RequestParam("year") int year, @RequestParam("week") int week) {
        return logistiqueService.countCommandesByWeek(year, week);
    }

    // Endpoint pour obtenir le nombre de commandes passées par mois
    @GetMapping("/commandes/mois")
    public Long getCommandesByMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        return logistiqueService.countCommandesByMonth(year, month);
    }

    // Endpoint pour obtenir le nombre de commandes passées par année
    @GetMapping("/commandes/annee")
    public Long getCommandesByYear(@RequestParam("year") int year) {
        return logistiqueService.countCommandesByYear(year);
    }

    @GetMapping("/materielss")
    public ResponseEntity<?> getStatistiquesMateriels(@RequestParam String type) {
        Long count = 0L;

        switch (type.toLowerCase()) {
            case "jour":
                count = logistiqueService.countCommandesByDay(new Date());  // Remplace par une vraie date si nécessaire
                break;
            case "semaine":
                count = logistiqueService.countCommandesByWeek(2025, 10); // Exemple de valeurs (ajuste selon ton besoin)
                break;
            case "mois":
                count = logistiqueService.countCommandesByMonth(2025, 3);  // Mars 2025
                break;
            case "annee":
                count = logistiqueService.countCommandesByYear(2025);
                break;
            default:
                return ResponseEntity.badRequest().body("Type invalide");
        }

        return ResponseEntity.ok(count);
    }
    @GetMapping("/statistiquesParAnnee")
    public List<Object[]> getStatistiquesParAnnee() {
        return logistiqueService.getStatistiquesParAnnee();
    }
    @PostMapping("/{idVehicule}/affecter/{idChauffeur}")
    public ResponseEntity<Vehicule> affecterChauffeurAVehicule(@PathVariable Long idVehicule, @PathVariable Long idChauffeur) {
        Vehicule vehicule = logistiqueService.affecterChauffeurAVehicule(idVehicule, idChauffeur);
        return new ResponseEntity<>(vehicule, HttpStatus.OK);
    }

    @PostMapping("/{idVehicule}/desaffecter")
    public ResponseEntity<Vehicule> desaffecterChauffeurDuVehicule(@PathVariable Long idVehicule) {
        Vehicule vehicule = logistiqueService.desaffecterChauffeurDuVehicule(idVehicule);
        return new ResponseEntity<>(vehicule, HttpStatus.OK);
    }

    @GetMapping("/getAllChauffeur")
    public List<Chauffeur> getAllChauffeur(){
        return logistiqueService.getAllChauffeur();
    }

    @GetMapping("/chauffeur/{idChauffeur}")
    public Vehicule getVehiculeByChauffeurId(@PathVariable Long idChauffeur) {
        return logistiqueService.getVehiculeByChauffeurId(idChauffeur);
    }

    @PutMapping("/vehicules/{idVehicule}/updatePosition")
    public ResponseEntity<Vehicule> updatePositionVehicule(
            @PathVariable Long idVehicule,
            @RequestBody Map<String, Double> position) {

        Vehicule vehicule = logistiqueService.getVehiculeById(idVehicule);
        if (vehicule == null) {
            return ResponseEntity.notFound().build();
        }

        vehicule.setLatitude(position.get("latitude"));
        vehicule.setLongitude(position.get("longitude"));
        vehiculeRepo.save(vehicule);

        return ResponseEntity.ok(vehicule);
    }

    @GetMapping("/disponibles")
    public List<Vehicule> getVehiculesDisponibles() {
        return logistiqueService.getVehiculesDisponibles();
    }




}