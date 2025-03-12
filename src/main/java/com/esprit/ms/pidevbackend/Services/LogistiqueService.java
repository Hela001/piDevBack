package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.*;
import com.esprit.ms.pidevbackend.Repo.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LogistiqueService implements ILogistiqueService {
    MaterielRepo materielRepo;
    LigneCommandeRepo ligneCommandeRepo;
    CommandeRepo commandeRepo;
    LigneDemandeRepo ligneDemandeRepo;
    DemandeRepo demandeRepo;
    VehiculeRepo vehiculeRepo;
    ChauffeurRepository chauffeurRepository ;

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
    public Map<String, Object> verifierQuantiteMateriel(Long idDemande) {
        Demande demande = demandeRepo.findById(idDemande)
                .orElseThrow(() -> new EntityNotFoundException("Demande non trouvée avec l'ID : " + idDemande));

        Map<String, Object> response = new HashMap<>();
        boolean suffisant = true;
        StringBuilder message = new StringBuilder();

        for (LigneDemande ligne : demande.getLigneDemandes()) {
            Materiel materiel = ligne.getMateriel();
            if (materiel.getQuantite() < ligne.getQuantite()) {
                suffisant = false;
                message.append("Le matériel ").append(materiel.getNomMateriel()).append(" n'est pas disponible en quantité suffisante.\n");
            }
        }

        response.put("suffisant", suffisant);
        if (!suffisant) {
            response.put("message", message.toString());
        }

        return response;
    }

    @Override
    public Demande updateStatusDemande(Long idDemande, Status newStatus) {
        Demande demande = demandeRepo.findById(idDemande)
                .orElseThrow(() -> new EntityNotFoundException("Demande non trouvée avec l'ID : " + idDemande));

        if (newStatus == Status.Accepte) {
            for (LigneDemande ligne : demande.getLigneDemandes()) {
                Materiel materiel = ligne.getMateriel();
                materiel.setQuantite(materiel.getQuantite() - ligne.getQuantite());
                materielRepo.save(materiel);
            }
        }

        demande.setStatus(newStatus);
        return demandeRepo.save(demande);
    }
    //--------------Materiel

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
    public void supprimerLignesSansCommande() {
        ligneCommandeRepo.deleteLigneCommandeWithoutCommande();
    }

    @Override
    public List<LigneCommande> getLignesCommandeByCommande(Long idCommande) {
        return ligneCommandeRepo.findByCommandeId(idCommande);
    }


    @Override
    public void modifierLigneCommande(Long id, LigneCommande ligneCommande) {
        LigneCommande ligneCommande1 = ligneCommandeRepo.findById(id).orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée"));
        ligneCommande1.setQuantite(ligneCommande.getQuantite());
        ligneCommande1.setPrixTotal(ligneCommande.getQuantite() * ligneCommande1.getPrixUnitaire());  // Recalculer le prix total
        ligneCommandeRepo.save(ligneCommande1);
    }

    //--------------------Commande

    @Override
    public Commande ajouterCommande(Commande commande) {
        try {
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setIdfournisseur(null);
            nouvelleCommande.setDateCreation(new Date());
            nouvelleCommande.setPrixTotal(0);
            nouvelleCommande.setStatus(Status.En_attente);

            // Initialiser la liste des lignes de commande si elle est null
            if (commande.getLigneCommandes() == null) {
                commande.setLigneCommandes(new ArrayList<>());
            }

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

    @Override
    public List<Commande> getAllCommande() {
        return commandeRepo.findAll();
    }

    @Override
    public void supprimerCommande(Long id) {
        commandeRepo.deleteById(id);
    }

    @Override
    public List<Commande> getCommandeByFournisseur(Long id) {
        return commandeRepo.findByFournisseur(id);
    }

    @Override
    public List<Commande> getCommandeByStatut(String statut) {
        return commandeRepo.findByStatus(statut);
    }

    @Override
    public void modifierCommande(Long id, Status newStatus) {
        Optional<Commande> optionalCommande = commandeRepo.findById(id);

        if (optionalCommande.isPresent()) {
            Commande c = optionalCommande.get();

            // 🔥 Vérifie si le statut est déjà défini
            if (c.getStatus() == newStatus) {
                throw new IllegalStateException("La commande est déjà " + newStatus + ".");
            }

            c.setStatus(newStatus);
            commandeRepo.save(c); // 🔥 Sauvegarde dans la BDD

        } else {
            throw new EntityNotFoundException("Commande introuvable avec l'ID : " + id);
        }
    }

    @Override
    public void modifierCommandePrix(Long idCommande) {
        Commande commande = commandeRepo.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Recalculer le prix total
        double prixTotal = ligneCommandeRepo.findByCommandeId(idCommande)
                .stream()
                .mapToDouble(LigneCommande::getPrixTotal)
                .sum();

        commande.setPrixTotal((float) prixTotal);
        commandeRepo.save(commande);
    }

    //----------------ligne demande
    @Override
    public LigneDemande ajouterLigneDemande(LigneDemande ligneDemande) {
        return ligneDemandeRepo.save(ligneDemande);
    }

    @Override
    public LigneDemande modifierLigneDemande(LigneDemande ligneDemande) {
        return ligneDemandeRepo.save(ligneDemande);
    }

    @Override
    public LigneDemande getLigneDemandeById(Long id) {
        return ligneDemandeRepo.findById(id).orElse(null);
    }

    @Override
    public Demande ajouterDemande(Demande demande) {
        Demande nouvelleDemande = new Demande();
        nouvelleDemande.setDateDemande(new Date());
        nouvelleDemande.setStatus(Status.En_attente);

        // Sauvegarde initiale de la demande
        nouvelleDemande = demandeRepo.save(nouvelleDemande);

        List<LigneDemande> lignesDemande = new ArrayList<>();
        for (LigneDemande ligne : demande.getLigneDemandes()) {
            ligne.setDemande(nouvelleDemande);  // Associer la demande aux lignes de demande
            lignesDemande.add(ligne);
        }

        // Sauvegarde des lignes de demande
        ligneDemandeRepo.saveAll(lignesDemande);

        // Assigner les lignes de demande à la demande et sauvegarder à nouveau
        nouvelleDemande.setLigneDemandes(lignesDemande);
        return demandeRepo.save(nouvelleDemande);
    }


    @Override
    public Demande getDemandeById(Long id) {
        return demandeRepo.findById(id).get();
    }

    @Override
    public List<Demande> getAllDemande() {
        return demandeRepo.findAll();
    }

    @Override
    public List<LigneDemande> getLigneDemandeByIDDemande(Long idDemande) {
        // On récupère la demande par son ID
        Demande demande = demandeRepo.findById(idDemande)
                .orElseThrow(() -> new EntityNotFoundException("Demande non trouvée avec l'ID : " + idDemande));

        // On retourne les lignes de demande associées
        return demande.getLigneDemandes();
    }

    @Override
    public List<Demande> getDemandesByUser(Long idUser) {
        return demandeRepo.findByIdUser(idUser);
    }

    @Override
    public LigneDemande updateQuantiteLigneDemande(Long idLigneDemande, int nouvelleQuantite) {
        LigneDemande ligneDemande = ligneDemandeRepo.findById(idLigneDemande)
                .orElseThrow(() -> new EntityNotFoundException("Ligne de demande non trouvée avec l'ID : " + idLigneDemande));

        ligneDemande.setQuantite(nouvelleQuantite); // Mettre à jour la quantité
        return ligneDemandeRepo.save(ligneDemande); // Sauvegarder les modifications
    }



    @Override
    public Vehicule ajouterVehicule(Vehicule vehicule) {
        return vehiculeRepo.save(vehicule);
    }

    @Override
    public List<Vehicule> getAllVehicule() {
        return vehiculeRepo.findAll();
    }

    @Override
    public List<Vehicule> getVehiculeByType(TypeVehicule type) {
        List<Vehicule> vehicules = vehiculeRepo.findAllByTypeVehicule(type);
        return  vehicules;
    }

    @Override
    public void supprimerVehicule(Long id) {
        Vehicule vehicule = vehiculeRepo.findById(id).get();
        vehiculeRepo.delete(vehicule);
    }

    @Override
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepo.findById(id).get();
    }

    @Override
    public void modifierVehicule(Long id, Vehicule vehicule) {
        // Récupérer le véhicule existant par son ID
        Vehicule vehiculeExistant = vehiculeRepo.findById(id).orElseThrow(() -> new RuntimeException("Véhicule introuvable"));

        // Mettre à jour les champs du véhicule existant, sauf latitude, longitude et dateAffectation
        if (vehicule.getNomVehicule() != null) {
            vehiculeExistant.setNomVehicule(vehicule.getNomVehicule());
        }
        if (vehicule.getIdChauffeur() != null) {
            vehiculeExistant.setIdChauffeur(vehicule.getIdChauffeur());
        }
        if (vehicule.getMarque() != null) {
            vehiculeExistant.setMarque(vehicule.getMarque());
        }
        if (vehicule.getModele() != null) {
            vehiculeExistant.setModele(vehicule.getModele());
        }
        if (vehicule.getImmatriculation() != null) {
            vehiculeExistant.setImmatriculation(vehicule.getImmatriculation());
        }
        if (vehicule.getTypeVehicule() != null) {
            vehiculeExistant.setTypeVehicule(vehicule.getTypeVehicule());
        }
        if (vehicule.getDisponible() != null) {
            vehiculeExistant.setDisponible(vehicule.getDisponible());
        }

        // Sauvegarder le véhicule mis à jour
        vehiculeRepo.save(vehiculeExistant);
    }
    @Override
    public List<TopMaterielDTO> getTopMateriels() {
        List<Object[]> results = ligneDemandeRepo.findTopMateriels();

        return results.stream()
                .map(obj -> new TopMaterielDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Object[]> getMaterielsParCategorie() {
            return ligneDemandeRepo.findMaterielsParCategorie();
    }

    @Override
    public Long countCommandesByDay(Date dateCreation) {
        return commandeRepo.countCommandesByDay(dateCreation);
    }
    @Override
    public Long countCommandesByWeek(int year, int week) {
        return commandeRepo.countCommandesByWeek(year, week);
    }
    @Override
    // Nombre total de commandes passées par mois
    public Long countCommandesByMonth(int year, int month) {
        return commandeRepo.countCommandesByMonth(year, month);
    }
    @Override
    public Long countCommandesByYear(int year) {
        return commandeRepo.countCommandesByYear(year);
    }
    @Override
    public List<Object[]> getStatistiquesParAnnee() {
        // Exemple de requête pour récupérer les totaux des 3 dernières années
        int currentYear = LocalDate.now().getYear();
        int previousYear1 = currentYear - 1;
        int previousYear2 = currentYear - 2;

        return commandeRepo.findTotalCommandesByYear(currentYear, previousYear1, previousYear2);
    }

    @Override
    public Vehicule affecterChauffeurAVehicule(Long idVehicule, Long idChauffeur) {
        Vehicule vehicule = vehiculeRepo.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule non trouvé"));

        Chauffeur chauffeur = chauffeurRepository.findById(idChauffeur)
                .orElseThrow(() -> new EntityNotFoundException("Chauffeur non trouvé"));

        // Vérifier si le véhicule est déjà affecté à un autre chauffeur
        if (vehicule.getChauffeur() != null) {
            throw new IllegalStateException("Le véhicule est déjà affecté à un chauffeur.");
        }

        // Affecter le chauffeur au véhicule
        vehicule.setChauffeur(chauffeur);
        chauffeur.setVehicule(vehicule);
        vehicule.setDisponible(false); // Le véhicule n'est plus disponible
        vehiculeRepo.save(vehicule);
        chauffeurRepository.save(chauffeur);

        return vehicule;
    }



    @Override
    public Vehicule desaffecterChauffeurDuVehicule(Long idVehicule) {
        Vehicule vehicule = vehiculeRepo.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule non trouvé"));

        Chauffeur chauffeur = vehicule.getChauffeur();
        if (chauffeur == null) {
            throw new IllegalStateException("Le véhicule n'a pas de chauffeur affecté.");
        }

        // Désaffecter le chauffeur du véhicule
        vehicule.setChauffeur(null);
        chauffeur.setVehicule(null);
        vehicule.setDisponible(true); // Le véhicule devient disponible
        vehiculeRepo.save(vehicule);
        chauffeurRepository.save(chauffeur);

        return vehicule;
    }

    @Override
    public List<Chauffeur> getAllChauffeur() {
        return chauffeurRepository.findAll();
    }

    @Override
    public Vehicule getVehiculeByChauffeurId(Long idChauffeur) {
        Vehicule vehicule = vehiculeRepo.findByIdChauffeur(idChauffeur);
        return vehicule;
    }

    @Override
    public List<Vehicule> getVehiculesDisponibles() {
        return vehiculeRepo.findByDisponibleTrue();
    }

}