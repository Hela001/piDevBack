package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Notification;
import com.esprit.ms.pidevbackend.Repositories.NotificationRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    public NotificationService(NotificationRepository notificationRepository,
                               MailingService mailingService,
                               org.springframework.mail.javamail.JavaMailSender javaMailSender) {
        this.notificationRepository = notificationRepository;
        this.mailingService = mailingService;
        this.javaMailSender = javaMailSender;
    }

    private final NotificationRepository notificationRepository;
    private final MailingService mailingService;     // ← injection du service de mail
    private final org.springframework.mail.javamail.JavaMailSender javaMailSender; // injection de JavaMailSender

    /**
     * Crée la notification en base et envoie un e‑mail réel.
     */
    public void envoyerNotification(String destinataire, String nomProjet, String nomMission,
                                    String nomTache, String nouvelEtat, String employeNom) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(destinataire);
            helper.setSubject("Mise à jour de la tâche : " + nomTache);

            // Contenu HTML du mail
            String emailContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;'>" +
                    "  <div style='max-width: 600px; margin: auto; background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>" +
                    "    <div style='text-align: center;'>" +
                    "      <img src='cid:logoProjet' alt='Logo Projet' style='width: 80px; height: auto; margin-bottom: 20px;'/>" +
                    "      <h2 style='color: #2c3e50;'>Mise à jour d'une tâche</h2>" +
                    "    </div>" +
                    "    <p style='font-size: 16px; color: #333;'>Bonjour <strong>Chef de Projet</strong>,</p>" +
                    "    <p style='font-size: 15px; color: #555;'>L'employé <strong>" + employeNom + "</strong> a modifié l'état de la tâche suivante :</p>" +
                    "    <ul style='font-size: 15px; color: #444;'> " +
                    "      <li><b>Projet :</b> " + nomProjet + "</li>" +
                    "      <li><b>Mission :</b> " + nomMission + "</li>" +
                    "      <li><b>Tâche :</b> " + nomTache + "</li>" +
                    "      <li><b>Nouvel état :</b> <span style='color: #007bff;'>" + nouvelEtat + "</span></li>" +
                    "    </ul>" +
                    "    <p style='font-size: 14px; color: #888;'>Merci de suivre l’évolution des tâches dans votre espace de gestion.</p>" +
                    "    <hr style='margin: 30px 0;'/>" +
                    "    <p style='font-size: 13px; color: #aaa; text-align: center;'>Cet email est généré automatiquement par la plateforme de gestion de projet.</p>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);

            // Ajout du logo
            FileSystemResource logo = new FileSystemResource("C:/images/logo.png");
            helper.addInline("logoProjet", logo);

            javaMailSender.send(message);
            System.out.println("Mail envoyé au chef de projet avec succès.");

        } catch (Exception e) {
            System.err.println("Erreur envoi email : " + e.getMessage());
            e.printStackTrace();
        }
    }



    public List<Notification> getNotificationsNonLues(String destinataire) {
        return notificationRepository.findByDestinataireAndLueFalse(destinataire);
    }

    public void marquerCommeLue(Long id) {
        Notification notif = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        notif.setLue(true);
        notificationRepository.save(notif);
    }
}
