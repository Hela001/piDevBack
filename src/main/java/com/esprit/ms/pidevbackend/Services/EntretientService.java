package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.DemandeEmploi;
import com.esprit.ms.pidevbackend.Entities.Entretien;
import com.esprit.ms.pidevbackend.Entities.StatusDemande;
import com.esprit.ms.pidevbackend.Entities.TypeEntretient;
import com.esprit.ms.pidevbackend.Repo.DemandeEmploiRepo;
import com.esprit.ms.pidevbackend.Repo.EntretienRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntretientService  implements IEntretienService{
    DemandeEmploiRepo demandeEmploiRepo;
    EntretienRepo entretienRepo;
    @Override
    public DemandeEmploi ajouterDemandeEmploi(DemandeEmploi demandeEmploi) {
        return demandeEmploiRepo.save(demandeEmploi);
    }

    @Override
    public DemandeEmploi getDemandeEmploiById(Long id) {
        return demandeEmploiRepo.findById(id).get();
    }

    @Override
    public DemandeEmploi updateDemandeEmploi(DemandeEmploi demandeEmploi) {
        // Vérifier si la demande d'emploi existe en base de données
        Optional<DemandeEmploi> existingDemande = demandeEmploiRepo.findById(demandeEmploi.getIdDemandeEmploi());

        if (existingDemande.isPresent()) {
            DemandeEmploi updatedDemande = existingDemande.get();

            // Mettre à jour les champs modifiables
            updatedDemande.setNom(demandeEmploi.getNom());
            updatedDemande.setPrenom(demandeEmploi.getPrenom());
            updatedDemande.setAdresseMail(demandeEmploi.getAdresseMail());
            updatedDemande.setSpecialite(demandeEmploi.getSpecialite());
            updatedDemande.setAge(demandeEmploi.getAge());
            updatedDemande.setExperience(demandeEmploi.getExperience());

            // Sauvegarder les modifications
            return demandeEmploiRepo.save(updatedDemande);
        } else {
            throw new RuntimeException("Demande d'emploi non trouvée avec l'ID: " + demandeEmploi.getIdDemandeEmploi());
        }
    }

    @Override
    public List<DemandeEmploi> getAllDemandeEmploi() {
        return demandeEmploiRepo.findAll();
    }

    @Override
    public void deleteDemandeEmploiById(Long id) {
        DemandeEmploi demande = demandeEmploiRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // Supprimer l'entretien lié avant la demande
        if (demande.getEntretien() != null) {
            entretienRepo.delete(demande.getEntretien());
        }

        demandeEmploiRepo.delete(demande);
    }

    @Override
    public Entretien ajouterEntretienPourDemande(Long idDemande) {
        Optional<DemandeEmploi> demandeOpt = demandeEmploiRepo.findById(idDemande);

        if (demandeOpt.isPresent()) {
            DemandeEmploi demande = demandeOpt.get();

            // Création d'un nouvel entretien
            Entretien entretien = new Entretien();
            entretien.setDateEntretien(null); // Date non définie
            entretien.setTypeEntretient(TypeEntretient.EnLigne); // Type par défaut
            entretien.setLienMeet(null); // Pas de lien Meet

            // Association de l'entretien à la demande d'emploi
            entretien.setDemandeEmploi(demande);
            demande.setEntretien(entretien);
            demande.setStatus(StatusDemande.En_attente);

            // Sauvegarde des entités
            entretienRepo.save(entretien);
            demandeEmploiRepo.save(demande);

            return entretien;
        } else {
            throw new RuntimeException("Demande d'emploi introuvable !");
        }
    }

    @Override
    public List<Object[]> getDemandesByDateEntretien(LocalDate date) {
        return demandeEmploiRepo.findDemandeByDateEntretien(date);
    }
    @Override
    public List<Entretien> getEntretiensDuJour() {
        LocalDate today = LocalDate.now();
        return entretienRepo.findByDateEntretien(today);
    }

    @Override
    public List<DemandeEmploi> getDemandeEmploiByStatus(StatusDemande statusDemande) {
        return demandeEmploiRepo.findByStatus(statusDemande);
    }

}
