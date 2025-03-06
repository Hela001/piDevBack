package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Inspecteur;
import com.esprit.ms.pidevbackend.Entities.Inspection;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Repositorys.InspecteurRepo;
import com.esprit.ms.pidevbackend.Repositorys.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceInspecteur implements IServiceInspecteur {

    @Autowired
    InspecteurRepo inspecteurRepo;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    EmailService emailService;
    @Autowired
    private SmsService smsService;
    public Inspecteur addInspectionsToInspecteur(Long inspecteurId, List<Inspection> inspections, Long projetId) {
        // Récupérer l'inspecteur
        Inspecteur inspecteur = inspecteurRepo.findById(inspecteurId)
                .orElseThrow(() -> new EntityNotFoundException("Inspecteur non trouvé"));

        // Récupérer le projet
        Projet projet = projectRepo.findById(projetId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        // Associer chaque inspection à l'inspecteur et au projet
        for (Inspection inspection : inspections) {
            inspection.setInspecteur(inspecteur);
            inspection.setProjet(projet);

            // Envoyer un e-mail à l'inspecteur pour chaque inspection ajoutée
            emailService.sendInspectionCreatedEmail(inspection);
            String smsMessage = "A new inspection has been assigned to you. Details are as follows:\n" +
                    "Inspection Type: " + inspection.getTypeInspection() + "\n" +
                    "Inspection Date: " + inspection.getDateInspection() + "\n" +
                    "Please review the details and proceed accordingly.";
            smsService.sendSms(inspecteur.getTelephoneInspecteur(), smsMessage);

        }





        // Ajouter les inspections à l'inspecteur
        inspecteur.getInspections().addAll(inspections);

        // Sauvegarder l'inspecteur avec ses inspections
        return inspecteurRepo.save(inspecteur);
    }

    // Lire tous les inspecteurs
    public List<Inspecteur>getAllInspecteurs() {
        return inspecteurRepo.findAll();
    }

    public Inspecteur saveOrUpdateInspecteur(Inspecteur inspecteur) {
        return inspecteurRepo.save(inspecteur);
    }



    // Lire un inspecteur par ID
    public Optional<Inspecteur> getInspecteurById(Long id) {
        return inspecteurRepo.findById(id);
    }

    // Supprimer un inspecteur par ID
    public void deleteInspecteur(Long id) {
        inspecteurRepo.deleteById(id);
    }
}


