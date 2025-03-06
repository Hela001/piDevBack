package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Inspection;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Repositorys.InspectionRepo;
import com.esprit.ms.pidevbackend.Repositorys.NonConformiteRepository;
import com.esprit.ms.pidevbackend.Repositorys.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InspectionService implements IinspectionService{
    @Autowired
        InspectionRepo inspectionRepo;
    @Autowired
    NonConformiteRepository nonConformiteRepository;

    @Autowired
    ProjetService projetService;
    @Autowired
    private SmsService smsService; // Inject the SmsService


    @Autowired
    private EmailService emailService;
    @Override
    public List<Inspection> getAllInspection() {

        return inspectionRepo.findAll();
    }

    public Inspection addNonConformiteToInspection(Long idInspection, NonConfirmity nonConformite) {
        Inspection inspection = inspectionRepo.findById(idInspection).
                orElseThrow(() -> new RuntimeException("NonConformity not found"));;

        inspection.getNonConformities().add(nonConformite);


        return inspectionRepo.save(inspection);
    }

    @Override
    public Inspection addInspection(Inspection inspection) {
        if (inspection.getIdINS() != null && inspectionRepo.existsById(inspection.getIdINS())) {
            throw new IllegalStateException("Inspection already exists");
        }
        return inspectionRepo.save(inspection);
    }

    @Override
    public void deleteInspection(Long id) {
        inspectionRepo.deleteById(id);
    }



    @Override
    public Inspection updateInspection(Inspection inspection) {
        // Récupérer l'inspection par ID
        Inspection existingInspection = inspectionRepo.findById(inspection.getIdINS())
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found"));

        boolean modified = false;

        // Vérifier les modifications et envoyer des e-mails et SMS
        if (!existingInspection.getDateInspection().equals(inspection.getDateInspection())) {
            String oldDate = existingInspection.getDateInspection().toString();
            String newDate = inspection.getDateInspection().toString();
            emailService.sendInspectionUpdatedEmail(existingInspection, "Inspection Date", oldDate, newDate);
            smsService.sendSms(existingInspection.getInspecteur().getTelephoneInspecteur(),
                    "The inspection date has been updated. \n" +
                            "Previous Date: " + oldDate + "\n" +
                            "New Date: " + newDate + "\n" +
                            "Please review the updated details and take necessary actions.");
            existingInspection.setDateInspection(inspection.getDateInspection());
            modified = true;
        }

        if (!existingInspection.getTypeInspection().equals(inspection.getTypeInspection())) {
            String oldType = existingInspection.getTypeInspection().toString();
            String newType = inspection.getTypeInspection().toString();
            emailService.sendInspectionUpdatedEmail(existingInspection, "Inspection Type", oldType, newType);
            smsService.sendSms(existingInspection.getInspecteur().getTelephoneInspecteur(),
                    "The inspection type has been updated. \n" +
                            "Previous Type: " + oldType + "\n" +
                            "New Type: " + newType + "\n" +
                            "Please review the updated details and adjust your work accordingly.");
            existingInspection.setTypeInspection(inspection.getTypeInspection());
            modified = true;
        }

        if (!existingInspection.getStatusInspection().equals(inspection.getStatusInspection())) {
            String oldStatus = existingInspection.getStatusInspection().toString();
            String newStatus = inspection.getStatusInspection().toString();
            emailService.sendInspectionUpdatedEmail(existingInspection, "Inspection Status", oldStatus, newStatus);
            smsService.sendSms(existingInspection.getInspecteur().getTelephoneInspecteur(),
                    "The inspection status has been updated. \n" +
                            "Previous Status: " + oldStatus + "\n" +
                            "New Status: " + newStatus + "\n" +
                            "Please review the updated details and proceed accordingly.");
            existingInspection.setStatusInspection(inspection.getStatusInspection());
            modified = true;
        }

        // Garder les non-conformités existantes
        List<NonConfirmity> currentNonConformities = existingInspection.getNonConformities();
        if (inspection.getNonConformities() != null) {
            currentNonConformities.addAll(inspection.getNonConformities());
        }
        existingInspection.setNonConformities(currentNonConformities);

        return modified ? inspectionRepo.save(existingInspection) : existingInspection;
    }

    public List<NonConfirmity> getNonConformitiesByInspection(Long idINS) {
            // Récupérer l'inspection par ID
            Inspection inspection = inspectionRepo.findByIdINS(idINS).stream().findFirst().orElse(null);

            if (inspection != null) {
                return inspection.getNonConformities(); // Retourner les non-conformités de l'inspection
            } else {
                // Gérer le cas où l'inspection n'est pas trouvée
                return null;
            }



    }


    public NonConfirmity updateNonConformity(Long idInspection, Long idNonConformity, NonConfirmity nonConfirmity) {
        // Récupérer l'inspection par ID
        Inspection inspection = inspectionRepo.findById(idInspection)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found"));

        // Trouver la non-conformité par ID dans la liste de non-conformités de l'inspection
        NonConfirmity existingNonConfirmity = inspection.getNonConformities().stream()
                .filter(nc -> nc.getIdNC().equals(idNonConformity))
                .findFirst()
                .orElse(null);

        if (existingNonConfirmity != null) {
            // Mettre à jour la non-conformité avec les nouvelles données
            existingNonConfirmity.setDescription(nonConfirmity.getDescription());
            existingNonConfirmity.setDateDetection(nonConfirmity.getDateDetection());
            existingNonConfirmity.setTypeNonConfirm(nonConfirmity.getTypeNonConfirm());
            existingNonConfirmity.setStatutNonConfirm(nonConfirmity.getStatutNonConfirm());

            // Sauvegarder la mise à jour dans la base de données
            nonConformiteRepository.save(existingNonConfirmity);

            // Retourner la non-conformité mise à jour
            return existingNonConfirmity;
        } else {
            // Si la non-conformité n'est pas trouvée
            return null;
        }
    }

    @Override
    public Inspection getInspectionById(Long id) {
        return inspectionRepo.findById(id).get();
    }
}
