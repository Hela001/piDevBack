package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Repositories.ProjetRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetServices implements IProjetServices {

    private  ProjetRepository projetRepository;
    private WeatherService weatherService;


    @Override
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    @Override
    public Projet getProjetById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID " + id));
    }

    @Override
    public Projet addProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    @Override
    public Projet updateProjet(Long id, Projet projet) {
        if (!projetRepository.existsById(id)) {
            throw new EntityNotFoundException("Projet non trouvé avec l'ID " + id);
        }
        projet.setIdProjet(id);  // S'assurer que l'ID est bien maintenu
        return projetRepository.save(projet);
    }

    @Override
    public boolean deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new EntityNotFoundException("Projet non trouvé avec l'ID " + id);
        }
        projetRepository.deleteById(id);
        return true;
    }

    public Projet getProjetWithMissions(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID " + projetId));

        projet.getMissions().size();  // Forcer le chargement des missions si FetchType.LAZY
        return projet;
    }

    public List<Projet> searchProjets(String nom, Status status) {
        return projetRepository.searchProjet(nom, status);
    }

    public Map<String, Long> getProjetStatsByStatus() {
        List<Projet> projets = projetRepository.findAll();
        return projets.stream()
                .collect(Collectors.groupingBy(projet -> projet.getStatus().name(), Collectors.counting()));
    }

    public byte[] generateProjetPdf(Long projetId) throws IOException {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID " + projetId));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Ajout des détails du projet
        document.add(new Paragraph("Détails du Projet"));
        document.add(new Paragraph("Nom: " + projet.getNom()));  // Changement de getName() -> getNom()
        document.add(new Paragraph("Description: " + projet.getDescription()));
        document.add(new Paragraph("Manager: " + projet.getChefProjetId()));  // Vérifier le bon attribut
        document.add(new Paragraph("Status: " + projet.getStatus()));
        document.add(new Paragraph("Date de début: " + projet.getDateDebut()));
        document.add(new Paragraph("Date de fin prévue: " + projet.getDateFinPrevue()));

        document.close();
        return out.toByteArray();
    }
    public String getWeatherForecastForProject(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID " + projetId));

        double latitude = projet.getLatitude();
        double longitude = projet.getLongitude();

        return weatherService.getWeatherForecast(latitude, longitude);
    }
}
