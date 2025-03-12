package com.esprit.ms.pidevbackend.Services;


import com.esprit.ms.pidevbackend.Entities.*;
import com.esprit.ms.pidevbackend.Repo.LigneDemandeRepo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ILogistiqueService {

    //---------------Materiel
    Materiel ajouterMateriel(Materiel materiel);
    List<Materiel> getAllMateriel();
    List<Materiel> getMaterielByCategorie(Categorie categorie);
    Materiel getMaterielById(Long id);
    void supprimerMateriel(Long id);
    void modifierMateriel(Long id , Materiel materiel);
     Map<String, Object> verifierQuantiteMateriel(Long idDemande);

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
    void supprimerCommande(Long id);
    List<Commande> getCommandeByFournisseur(Long id);
    List<Commande> getCommandeByStatut(String statut);
    void modifierCommande(Long id, Status newStatus);
    void modifierCommandePrix(Long id);

    //--------------LigneDemande
    LigneDemande ajouterLigneDemande(LigneDemande ligneDemande);
    LigneDemande modifierLigneDemande(LigneDemande ligneDemande);
    LigneDemande getLigneDemandeById(Long id) ;
    List<LigneDemande> getLigneDemandeByIDDemande(Long idDemande);

    //-------------Demande
    Demande ajouterDemande(Demande demande);
    Demande getDemandeById(Long id);
    List<Demande> getAllDemande();
    List<Demande> getDemandesByUser(Long idUser);
    LigneDemande updateQuantiteLigneDemande(Long idLigneDemande, int nouvelleQuantite);
    Demande updateStatusDemande(Long idDemande, Status newStatus);

    //---------------Vehicule
    Vehicule ajouterVehicule(Vehicule vehicule);
    List<Vehicule> getAllVehicule();
    List<Vehicule> getVehiculeByType(TypeVehicule type);
    void supprimerVehicule(Long id);
    Vehicule getVehiculeById(Long id);
    void modifierVehicule(Long id , Vehicule vehicule);

    //-------Statistique----------
    List<TopMaterielDTO> getTopMateriels();

    List<Object[]> getMaterielsParCategorie();


    // Nombre total de commandes passées par jour
    Long countCommandesByDay(Date dateCreation);

    // Nombre total de commandes passées par semaine
    Long countCommandesByWeek(int year, int week);

    Long countCommandesByMonth(int year, int month);

    // Nombre total de commandes passées par année
    Long countCommandesByYear(int year);

    List<Object[]> getStatistiquesParAnnee();

    Vehicule affecterChauffeurAVehicule(Long idChauffeur, Long idVehicule);

    Vehicule desaffecterChauffeurDuVehicule(Long idVehicule);

    List<Chauffeur> getAllChauffeur();

    Vehicule getVehiculeByChauffeurId(Long idChauffeur);

    List<Vehicule> getVehiculesDisponibles();
}