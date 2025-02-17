package com.esprit.ms.pidevbackend.Services;


import com.esprit.ms.pidevbackend.Entities.Categorie;
import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.LigneCommande;
import com.esprit.ms.pidevbackend.Entities.Materiel;

import java.util.List;

public interface ILogistiqueService {
    //---------------Materiel
    Materiel ajouterMateriel(Materiel materiel);
    List<Materiel> getAllMateriel();
    List<Materiel> getMaterielByCategorie(Categorie categorie);
    Materiel getMaterielById(Long id);
    void supprimerMateriel(Long id);
    void modifierMateriel(Long id , Materiel materiel);

    //----------LigneCommande

    LigneCommande ajouterLigneCommande(LigneCommande ligneCommande);
    List<LigneCommande> getAllLigneCommande();
    void supprimerLigneCommande(Long id);
    void modifierLigneCommande(Long id , LigneCommande ligneCommande);
     void supprimerLignesSansCommande();
    List<LigneCommande> getLignesCommandeByCommande(Long id);

    //----------Commande
     Commande ajouterCommande(Commande commande) ;
     List<Commande> getAllCommande();
}
