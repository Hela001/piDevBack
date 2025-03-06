package com.esprit.ms.pidevbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envoyerMail(String destinataire, String sujet, String contenu) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinataire);
        message.setSubject(sujet);
        message.setText(contenu);

        javaMailSender.send(message);
        System.out.println("E-mail envoyé avec succès à : " + destinataire);
    }
}