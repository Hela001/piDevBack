package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Inspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
@Service
@EnableAsync
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendInspectionEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendInspectionCreatedEmail(Inspection inspection) {
        String subject = "New Inspection Assigned";
        String body = "Dear " + inspection.getInspecteur().getNomInspecteur() + ",\n\n"
                + "We would like to inform you that a new inspection has been assigned to you.\n\n"
                + "🔎 Inspection Details:\n"
                + "📌 Type: " + inspection.getTypeInspection() + "\n"
                + "📅 Date: " + inspection.getDateInspection() + "\n"
                + "📍 Project: " + inspection.getProjet().getNomProjet() + "\n\n"
                + "Please log in to your account for further details and to view the inspection tasks.\n\n"
                + "If you have any questions, do not hesitate to contact the team.\n\n"
                + "Best regards,\nThe Quality Assurance Team\n"
                + "------------------------------------------------\n"
                + "For assistance, please visit our support portal or contact us directly at [support@yourcompany.com].";

        sendInspectionEmail(inspection.getInspecteur().getEmailInspecteur(), subject, body);
    }
    public void sendInspectionUpdatedEmail(Inspection inspection, String updatedField, String oldValue, String newValue) {
        // Ensure the inspection is updated with the latest data
        //A5633P621ML3SVUWTW9DGQRM
        String subject = "Inspection Update Notification";
        String body = "Dear " + inspection.getInspecteur().getNomInspecteur() + ",\n\n"
                + "We would like to inform you that Inspection of the project" + inspection.getProjet().getNomProjet() + " has been updated.\n\n"
                + "🔄 Updated Field: " + updatedField + "\n"
                + "🔹 Previous Value: " + oldValue + "\n"
                + "🔹 New Value: " + newValue + "\n\n"
                + "Here are the updated details of the inspection:\n\n"
                + "📌 Inspection Type: " + inspection.getTypeInspection() + "\n"  // Updated type
                + "📅 Inspection Date: " + inspection.getDateInspection() + "\n"  // Date of inspection
                + "📍 Project: " + inspection.getProjet().getNomProjet() + "\n"  // Project name
                + "📝 Inspection Status: " + inspection.getStatusInspection() + "\n"  // Updated status
                + "🗣 Inspector: " + inspection.getInspecteur().getNomInspecteur() + "\n\n"
                + "Please review the updated information and ensure that all details are correct.\n\n"
                + "If you have any questions or concerns regarding the changes, do not hesitate to reach out.\n\n"
                + "Thank you for your attention to this matter.\n\n"
                + "Best regards,\nThe Quality Assurance Team\n"
                + "------------------------------------------------\n"
                + "For assistance, please visit our support portal or contact us directly at [support@yourcompany.com].";

        // Send the email to the inspector
        sendInspectionEmail(inspection.getInspecteur().getEmailInspecteur(), subject, body);
    }

}
