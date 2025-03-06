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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
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
    MailingService  mailingService;
    private String chefProjetEmail;


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

        String weatherJson = weatherService.getWeatherForecast(latitude, longitude);

        // Vérifier si la météo est mauvaise et envoyer un mail si nécessaire
        if (weatherService.isBadWeather(weatherJson)) {
            String sujet = "⚠️ Alerte Météo pour le Projet : " + projet.getNom();
            String contenu = "Bonjour,\n\nLa météo prévue pour le projet " + projet.getNom() + " est mauvaise.\nPrenez vos précautions.\n\nCordialement,\nL'équipe Projet";
            mailingService.envoyerMail(chefProjetEmail, sujet, contenu);
        }

        return weatherJson;
    }
    public byte[] generateProjetExcel() throws IOException {
        List<Projet> projets = projetRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Projets");

        // En-têtes
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Nom", "Description", "Manager", "Status", "Date Début", "Date Fin"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Remplir les lignes
        int rowNum = 1;
        for (Projet projet : projets) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(projet.getIdProjet());
            row.createCell(1).setCellValue(projet.getNom());
            row.createCell(2).setCellValue(projet.getDescription());
            row.createCell(3).setCellValue(projet.getChefProjetId());
            row.createCell(4).setCellValue(projet.getStatus().name());
            row.createCell(5).setCellValue(projet.getDateDebut().toString());
            row.createCell(6).setCellValue(projet.getDateFinPrevue().toString());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerCellStyle.setFont(font);
        return headerCellStyle;
    }
}
