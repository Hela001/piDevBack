package com.esprit.ms.pidevbackend.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envoyerMail(String destinataire, String sujet, String contenu) {
        try {
            // Création du message MIME
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Définir l'adresse de destination et le sujet
            helper.setTo(destinataire);
            helper.setSubject(sujet);

            // Contenu HTML avec des images et des icônes
            String emailContent = "<html><body>"
                    + "<h1 style='color: #4CAF50;'>Alerte Météo pour votre Projet</h1>"
                    + "<img src='cid:weatherIcon' alt='Météo' style='width: 50px; height: 50px;'/>"
                    + "<p>Bonjour,</p>"
                    + "<p><b>Le projet : </b>" + sujet + "</p>"
                    + "<p><b>La météo prévue est mauvaise, veuillez prendre vos précautions.</b></p>"
                    + "<p>Cordialement,</p>"
                    + "<p><i>L'équipe Projet</i></p>"
                    + "</body></html>";

            // Ajouter le contenu HTML
            helper.setText(emailContent, true);

            // Ajouter une icône dans l'email (ici un fichier image par exemple)
            // Assurez-vous que le chemin du fichier image est correct
            FileSystemResource weatherIcon = new FileSystemResource("C:/images/logo.png");
            helper.addInline("weatherIcon", weatherIcon);

            // Envoyer l'e-mail
            javaMailSender.send(message);
            System.out.println("E-mail envoyé avec succès à : " + destinataire);

        } catch (MessagingException e) {
            // Journalisation et gestion de l'exception
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Gestion des autres exceptions
            System.err.println("Erreur inconnue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
