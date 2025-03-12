package com.esprit.ms.pidevbackend.Controller;


import com.esprit.ms.pidevbackend.Entities.DemandeEmploi;
import com.esprit.ms.pidevbackend.Entities.Entretien;
import com.esprit.ms.pidevbackend.Entities.StatusDemande;
import com.esprit.ms.pidevbackend.Repo.DemandeEmploiRepo;
import com.esprit.ms.pidevbackend.Repo.EntretienRepo;
import com.esprit.ms.pidevbackend.Services.IEntretienService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/Api/entretien") // Consistent lowercase naming
@CrossOrigin(origins = "http://localhost:4200")
public class EntretienController {

    IEntretienService entretienService;
    EntretienRepo entretienRepo;
    private JavaMailSender mailSender;


    DemandeEmploiRepo demandeEmploiRepo;

    @PostMapping("/ajouterDemandeEmploi")
    public DemandeEmploi ajouterDemandeEmploi(@RequestBody DemandeEmploi demandeEmploi) {
        return entretienService.ajouterDemandeEmploi(demandeEmploi);
    }

    @GetMapping("/getDemandeEmploiById/{id}")
    public DemandeEmploi getDemandeEmploiById(@PathVariable Long id) {
        return entretienService.getDemandeEmploiById(id);
    }

    @PutMapping("/updateDemandeEmploi/{id}")
    public ResponseEntity<DemandeEmploi> updateDemandeEmploi(@PathVariable Long id, @RequestBody DemandeEmploi demandeEmploi) {
        demandeEmploi.setIdDemandeEmploi(id); // S'assurer que l'ID correspond
        DemandeEmploi updatedDemande = entretienService.updateDemandeEmploi(demandeEmploi);
        return ResponseEntity.ok(updatedDemande);
    }

    @GetMapping("/getAllDemandeEmploi")
    public List<DemandeEmploi> getAllDemandeEmploi() {
        return entretienService.getAllDemandeEmploi();
    }

    @PutMapping("/updateStatut/{id}")
    public ResponseEntity<DemandeEmploi> updateStatutDemande(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        DemandeEmploi demande = demandeEmploiRepo.findById(id).orElse(null);

        if (demande == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            demande.setStatus(StatusDemande.valueOf(newStatus));
            demandeEmploiRepo.save(demande);
            return ResponseEntity.ok(demande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteDemandeEmploiById/{id}")
    public void deleteDemandeEmploiById(@PathVariable Long id) {
        entretienService.deleteDemandeEmploiById(id);
    }

    @PostMapping("/ajouter/{idDemande}")
    public ResponseEntity<Entretien> ajouterEntretien(@PathVariable Long idDemande) {
        try {
            Entretien entretien = entretienService.ajouterEntretienPourDemande(idDemande);
            return ResponseEntity.ok(entretien);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/modifier/{idDemande}")
    public ResponseEntity<Entretien> modifierEntretien(@PathVariable Long idDemande, @RequestBody Entretien entretienDto) {
        Optional<DemandeEmploi> demandeOpt = demandeEmploiRepo.findById(idDemande);

        if (demandeOpt.isPresent()) {
            DemandeEmploi demande = demandeOpt.get();
            Entretien entretien = demande.getEntretien();

            if (entretien == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            entretien.setDateEntretien(entretienDto.getDateEntretien());
            entretien.setTypeEntretient(entretienDto.getTypeEntretient());
            entretien.setLienMeet(entretienDto.getLienMeet());

            entretienRepo.save(entretien);
            return ResponseEntity.ok(entretien);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-date-entretien")
    public List<Object[]> getDemandesByDateEntretien(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntretien) {
        return entretienService.getDemandesByDateEntretien(dateEntretien);
    }

    @PostMapping("/envoyer-email")
    public ResponseEntity<String> envoyerEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("L'adresse email est requise.");
        }

        try {
            // Créer un message MIME (pour le format HTML)
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // Le 'true' permet de gérer les pièces jointes

            helper.setTo(email);
            helper.setSubject("Confirmation de votre demande d'emploi");

            // Créer un contenu HTML avec une image et un peu de mise en forme
            String htmlContent = "<html><body>"
                    + "<h3>Bonjour,</h3>"
                    + "<p>Votre demande d'emploi a bien été reçue. Nous vous contacterons sous peu.</p>"
                    + "<p>Voici un aperçu de votre demande :</p>"
                    + "<img src='cid:logoImage' alt='Confirmation' width='300'/>"  // Utilisation de cid: pour l'image inline
                    + "<p>Merci pour votre intérêt.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true); // true pour indiquer que c'est du HTML

            // Ajouter l'image comme pièce jointe inline (Content-ID)
            FileSystemResource logoImage = new FileSystemResource(new File("C:\\alphaas\\back-end\\piDevBack\\src\\main\\resources\\image\\img.png"));
            helper.addInline("logoImage", logoImage);  // 'logoImage' doit correspondre au cid dans l'HTML

            // Envoi du message
            mailSender.send(message);

            return ResponseEntity.ok("Email envoyé avec succès.");
        } catch (MessagingException | MailException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email.");
        }
    }
    @PostMapping("/envoyer-email-refus")
    public ResponseEntity<String> envoyerEmailRefus(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("L'adresse email est requise.");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Refus de votre demande d'emploi");

            // Contenu HTML du mail de refus
            String htmlContent = "<html><body>"
                    + "<h3>Bonjour,</h3>"
                    + "<p>Nous avons examiné votre candidature avec attention.</p>"
                    + "<p>Malheureusement, nous ne pouvons pas donner une suite favorable à votre demande.</p>"
                    + "<p>Nous vous remercions pour l'intérêt porté à notre entreprise.</p>"
                    + "<p>Cordialement,</p>"
                    + "<p>Le service recrutement</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);

            // Envoi du message
            mailSender.send(message);

            return ResponseEntity.ok("Email de refus envoyé avec succès.");
        } catch (MessagingException | MailException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email de refus.");
        }
    }

    @GetMapping("/aujourdhui")
    public List<Entretien> getEntretiensDuJour() {
        return entretienService.getEntretiensDuJour();
    }
    @GetMapping("/getDemandeEmploiByStatus")
    public List<DemandeEmploi> getDemandeEmploiByStatus(@RequestParam StatusDemande statusDemande) {
        return entretienService.getDemandeEmploiByStatus(statusDemande);
    }



    }