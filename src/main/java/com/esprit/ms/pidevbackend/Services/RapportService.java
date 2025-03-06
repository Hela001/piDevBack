package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.Inspection;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.RapportQualite;

import com.esprit.ms.pidevbackend.Repositorys.InspectionRepo;
import com.esprit.ms.pidevbackend.Repositorys.RapportRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RapportService implements IRapportService {
    @Autowired
    RapportRepository rapportRepository;

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    private InspectionRepo inspectionRepo;

    @Override
    public List<RapportQualite> getAllRapportQualite() {
        return rapportRepository.findAll();
    }


    @Override
    public void deleteRapportQualite(Long id) {
        rapportRepository.deleteById(id);

    }

    public void deleteRapport(Long idRapport) {
        RapportQualite rapport = rapportRepository.findById(idRapport)
                .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));

        // Vérifie si l'inspection associée existe
        if (rapport.getInspection() != null) {
            Inspection inspection = rapport.getInspection();
            inspection.setRapportQualite(null); // Dissocie le rapport de l'inspection
            inspectionRepo.save(inspection); // Sauvegarde l'inspection sans rapport
        }

        // Supprime uniquement le rapport
        rapportRepository.delete(rapport);
    }

    @Override
    public RapportQualite updateRapportQualite(RapportQualite rapportQualite) {
        return rapportRepository.save(rapportQualite);
    }

    @Override
    public RapportQualite getRapportQualiteId(Long id) {
        return rapportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RapportQualite not found for id: " + id));
    }

    public RapportQualite addrapport(Long idINS, String contenu, MultipartFile photoVideo, LocalDate dateCreation) throws IOException {
        // Vérifier si l'inspection existe
        Inspection inspection = inspectionRepo.findById(idINS)
                .orElseThrow(() -> new RuntimeException("Inspection introuvable avec ID : " + idINS));

        // Vérifier si l'inspection a déjà un rapport
        if (inspection.getRapportQualite() != null) {
            throw new RuntimeException("Cette inspection possède déjà un rapport !");
        }

        // Créer un nouveau rapport qualité
        RapportQualite rapportQualite = new RapportQualite();
        rapportQualite.setContenu(contenu);
        rapportQualite.setDateCreation(dateCreation);

        // Ajouter la photo/vidéo si fournie
        if (photoVideo != null && !photoVideo.isEmpty()) {
            String uploadedPhoto = cloudinaryService.uploadFile(photoVideo);
            rapportQualite.setPhotoVideo(uploadedPhoto);
        }

        // Lier le rapport à l'inspection
        rapportQualite.setInspection(inspection);
        inspection.setRapportQualite(rapportQualite);

        // Sauvegarde du rapport et de l'inspection
        RapportQualite savedRapport = rapportRepository.save(rapportQualite);
        inspectionRepo.save(inspection); // Mettre à jour l'inspection

        return savedRapport;
    }


    public RapportQualite updateRapportQualite(Long idR, String contenu, MultipartFile photoVideo, LocalDate dateCreation) throws IOException {
        RapportQualite rapport = rapportRepository.findById(idR)
                .orElseThrow(() -> new RuntimeException("Rapport non trouvé avec l'ID : " + idR));

        if (contenu != null && !contenu.isEmpty()) {
            rapport.setContenu(contenu);
        }

        if (dateCreation != null) {
            rapport.setDateCreation(dateCreation);
        }

        if (photoVideo != null && !photoVideo.isEmpty()) {
            String uploadedPhoto = cloudinaryService.uploadFile(photoVideo);
            rapport.setPhotoVideo(uploadedPhoto);
        }

        return rapportRepository.save(rapport);
    }

    public RapportQualite generateRapportForInspection(Long inspectionId) {
        Optional<Inspection> optionalInspection = inspectionRepo.findById(inspectionId);

        if (optionalInspection.isEmpty()) {
            throw new RuntimeException("Inspection not found!");
        }

        Inspection inspection = optionalInspection.get();

        // Building the report
        StringBuilder content = new StringBuilder();
        content.append("\n");
        content.append("         INSPECTION AND QUALITY INFORMATIONS       \n");
        content.append("\n\n");

        content.append(" **General Information**\n");
        content.append("   ➝ Inspection ID   : ").append(inspection.getIdINS()).append("\n");
        content.append("   ➝ Date            : ").append(inspection.getDateInspection()).append("\n");
        content.append("   ➝ Type            : ").append(inspection.getTypeInspection()).append("\n");
        content.append("   ➝ Status          : ").append(inspection.getStatusInspection()).append("\n\n");

        // Inspector information
        content.append(" **Responsible Inspector**\n");
        content.append("   ➝ Inspector ID    : ").append(inspection.getInspecteur().getIdInspecteur()).append("\n");
        content.append("   ➝ Name            : ").append(inspection.getInspecteur().getNomInspecteur()).append("\n");
        content.append("   ➝ Phone           : ").append(inspection.getInspecteur().getTelephoneInspecteur()).append("\n");
        content.append("   ➝ Email           : ").append(inspection.getInspecteur().getEmailInspecteur()).append("\n\n");

        // Project information
        content.append(" **Related Project**\n");
        content.append("   ➝ Project ID      : ").append(inspection.getProjet().getIdProjet()).append("\n");
        content.append("   ➝ Project Name    : ").append(inspection.getProjet().getNomProjet()).append("\n\n");

        // List of non-conformities
        if (!inspection.getNonConformities().isEmpty()) {
            content.append(" **Detected Non-Conformities**\n");
            for (NonConfirmity nc : inspection.getNonConformities()) {
                content.append("    Description    : ").append(nc.getDescription()).append("\n");
                content.append("   Type           : ").append(nc.getTypeNonConfirm()).append("\n");
                content.append("   Status         : ").append(nc.getStatutNonConfirm()).append("\n");

                // Associated Corrective Actions
                if (!nc.getActionCorrective().isEmpty()) {
                    content.append("    **Corrective Actions**\n");
                    for (ActionCorrective ac : nc.getActionCorrective()) {
                        content.append("      ➝ Description : ").append(ac.getDescription()).append("\n");
                        content.append("         Start     : ").append(ac.getDateDebut()).append("\n");
                        content.append("         End       : ").append(ac.getDateFin()).append("\n");
                        content.append("         Status    : ").append(ac.getStatusActionCorrective()).append("\n");
                    }
                }
                content.append("\n");
            }
        } else {
            content.append(" No non-conformities detected.\n\n");
        }

        content.append("\n");
        content.append("  Report generated on : ").append(LocalDate.now()).append("\n");
        content.append("\n");

        // Create and save the report
        RapportQualite report = new RapportQualite();
        report.setDateCreation(LocalDate.now());
        report.setContenu(content.toString());
        report.setInspection(inspection);
        report = rapportRepository.save(report);

        // Associate the report with the inspection and save
        inspection.setRapportQualite(report);
        inspectionRepo.save(inspection);

        return report;
    }
}

