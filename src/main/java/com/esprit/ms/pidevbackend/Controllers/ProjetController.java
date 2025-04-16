package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Services.ProjetServices;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    private org.springframework.mail.javamail.JavaMailSender mailSender;

    @Autowired
    private ProjetServices projetService;

    // 🔍 Obtenir tous les projets
    @GetMapping
    public ResponseEntity<List<Projet>> getAllProjets() {
        List<Projet> projets = projetService.getAllProjets();
        return ResponseEntity.ok(projets);  // Renvoie 200 OK
    }

    // 🔍 Obtenir un projet par ID
    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable Long id) {
        Optional<Projet> projet = Optional.ofNullable(projetService.getProjetById(id));
        return projet.map(ResponseEntity::ok)  // Si trouvé : 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());  // Sinon : 404 Not Found
    }

    // ➕ Ajouter un projet
    @PostMapping
    public ResponseEntity<Projet> addProjet(@RequestBody Projet projet) {
        Projet newProjet = projetService.addProjet(projet);
        return ResponseEntity.ok(newProjet);  // 200 OK (ou 201 Created si tu préfères)
    }

    // ✏️ Mettre à jour un projet
    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProjet(@PathVariable Long id, @RequestBody Projet projet) {
        Projet updatedProjet = projetService.updateProjet(id, projet);
        return updatedProjet != null ? ResponseEntity.ok(updatedProjet) : ResponseEntity.notFound().build();
    }

    // ❌ Supprimer un projet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        boolean deleted = projetService.deleteProjet(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 🔍 Obtenir un projet avec ses missions
    @GetMapping("/{projetId}/missions")
    @ResponseStatus(HttpStatus.OK)
    public Projet getProjetWithMissions(@PathVariable Long projetId) {
        return projetService.getProjetWithMissions(projetId);
    }
    @GetMapping("/search")
    public List<Projet> searchProjets(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Status status) {
        return projetService.searchProjets(nom, status);
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getProjetStats() {
        Map<String, Long> stats = projetService.getProjetStatsByStatus();
        return ResponseEntity.ok(stats);  // Renvoie les statistiques des projets par statut
    }
    @GetMapping("/{id}/export-pdf")
    public ResponseEntity<byte[]> exportProjetToPdf(@PathVariable Long id) throws IOException, IOException {
        byte[] pdfContent = projetService.generateProjetPdf(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=projet_" + id + ".pdf")
                .body(pdfContent);
    }
    @GetMapping("/{id}/weather")
    public ResponseEntity<String> getWeatherForProject(@PathVariable Long id) {
        String weatherData = projetService.getWeatherForecastForProject(id);
        return ResponseEntity.ok(weatherData);
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportProjetToExcel() throws IOException {
        byte[] excelContent = projetService.generateProjetExcel();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=projets.xlsx")
                .body(excelContent);
    }
    @GetMapping("/{id}/rapport")
    public ResponseEntity<Map<String, Object>> getRapportComplet(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.genererRapportProjetComplet(id));
    }
    @PostMapping("/api/email/send-report")
    public ResponseEntity<?> sendReportByEmail(@RequestParam("file") MultipartFile file,
                                               @RequestParam("email") String email) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Rapport Projet");
        helper.setText("Voici votre rapport projet.");
        helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
        mailSender.send(message);
        return ResponseEntity.ok("Email envoyé");
    }





}
