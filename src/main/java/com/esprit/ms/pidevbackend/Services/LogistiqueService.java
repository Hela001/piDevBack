package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Categorie;
import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.LigneCommande;
import com.esprit.ms.pidevbackend.Entities.Materiel;
import com.esprit.ms.pidevbackend.Repo.CommandeRepo;
import com.esprit.ms.pidevbackend.Repo.LigneCommandeRepo;
import com.esprit.ms.pidevbackend.Repo.MaterielRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LogistiqueService implements ILogistiqueService {
    MaterielRepo materielRepo;
    LigneCommandeRepo ligneCommandeRepo;
    CommandeRepo commandeRepo;

    @Override
    public Materiel ajouterMateriel(Materiel materiel) {
        return materielRepo.save(materiel);
    }

    @Override
    public List<Materiel> getAllMateriel() {
        return materielRepo.findAll();
    }

    @Override
    public List<Materiel> getMaterielByCategorie(Categorie categorie) {
        List<Materiel> materiels = materielRepo.findAllByCategorie(categorie);
        return materiels;
    }

    @Override
    public Materiel getMaterielById(Long id) {
        return materielRepo.findById(id).get();
    }

    @Override
    public void supprimerMateriel(Long id) {
        Materiel materiel = materielRepo.findById(id).get();
        materielRepo.delete(materiel);
    }

    @Override
    public void modifierMateriel(Long id, Materiel updatedMateriel) {
        Materiel materiel = materielRepo.findById(id).get();
        materiel.setNomMateriel(updatedMateriel.getNomMateriel());
        materiel.setCategorie(updatedMateriel.getCategorie());
        materiel.setQuantite(updatedMateriel.getQuantite());
        materiel.setPrixMateriel(updatedMateriel.getPrixMateriel());
        materielRepo.save(materiel);
    }

    @Override
    public LigneCommande ajouterLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepo.save(ligneCommande);
    }

    @Override
    public List<LigneCommande> getAllLigneCommande() {
        return ligneCommandeRepo.findByAffecteFalse();
    }

    @Override
    public void supprimerLigneCommande(Long id) {
        LigneCommande ligneCommande = ligneCommandeRepo.findById(id).get();
        ligneCommandeRepo.delete(ligneCommande);
    }

    @Override
    public void modifierLigneCommande(Long id, LigneCommande ligneCommande) {
        LigneCommande ligneCommande1 = ligneCommandeRepo.findById(id).orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée"));
        ligneCommande1.setQuantite(ligneCommande.getQuantite());
        ligneCommande1.setPrixTotal(ligneCommande.getQuantite() * ligneCommande1.getPrixUnitaire());  // Recalculer le prix total
        ligneCommandeRepo.save(ligneCommande1);
    }

    @Override
    public Commande ajouterCommande(Commande commande) {
        try {
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setIdfournisseur(null);
            nouvelleCommande.setDateCreation(new Date());
            nouvelleCommande.setPrixTotal(0);

            // Sauvegarde initiale de la commande
            nouvelleCommande = commandeRepo.save(nouvelleCommande);

            float prixTotal = 0;
            List<LigneCommande> lignesCommande = new ArrayList<>();

            for (LigneCommande ligne : commande.getLigneCommandes()) {
                LigneCommande nouvelleLigneCommande = new LigneCommande();
                nouvelleLigneCommande.setPrixUnitaire(ligne.getPrixUnitaire());
                nouvelleLigneCommande.setQuantite(ligne.getQuantite());
                nouvelleLigneCommande.setPrixTotal(ligne.getPrixUnitaire() * ligne.getQuantite()); // Calcul du prix total
                nouvelleLigneCommande.setMateriel(ligne.getMateriel());
                nouvelleLigneCommande.setCommande(nouvelleCommande);
                nouvelleLigneCommande.setAffecte(true);

                prixTotal += nouvelleLigneCommande.getPrixTotal();
                lignesCommande.add(nouvelleLigneCommande);
            }

            // Enregistrer toutes les lignes de commande
            ligneCommandeRepo.saveAll(lignesCommande);

            // Mettre à jour le prix total de la commande et la sauvegarder
            nouvelleCommande.setPrixTotal(prixTotal);
            return commandeRepo.save(nouvelleCommande);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la commande : " + e.getMessage());
            e.printStackTrace();  // Afficher l'erreur complète dans les logs
            throw new RuntimeException("Erreur interne du serveur : " + e.getMessage());
        }
    }
}
