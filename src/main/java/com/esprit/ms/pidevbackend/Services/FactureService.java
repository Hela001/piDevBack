package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.FactureStatus;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FactureService implements IFactureService {

    @Autowired
    FactureRepo factureRepo;

    @Autowired
    private JavaMailSender javaMailSender;


    public  void sendEmail(String recipient , String body , String subject){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String fromEmailId = null;
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);

        javaMailSender.send(simpleMailMessage);

    }

    @Override
    public Facture addFacture(Facture facture) {
        // Enregistrer la facture dans la base de données
        Facture savedFacture = factureRepo.save(facture);

        // Envoyer un e-mail après l'ajout de la facture
        String recipient = "akremizayneb99@gmail.com";
        String subject = "Sent successfuly";
        String body = "New invoice Sent successfuly with these details :\n\n"
                + "Total Amount : " + savedFacture.getMontantTotal() + "\n"
                + "Invoice Date : " + savedFacture.getDateFacture() + "\n"
                + "Due Date : " + savedFacture.getDateEcheance() + "\n"
                + "Total Amount (Excl. Tax) : " + savedFacture.getMontantTotalHorsTaxe() + "\n"
                + "Tax : " + savedFacture.getTva() + "\n"
                + "Status : " + savedFacture.getStatus();

        sendEmail(recipient, body, subject);

        return savedFacture;
    }

    @Override
    public List<Facture> getAllFactures() {
        return (List<Facture>) factureRepo.findAll();
    }



    @Override
    public Facture getFactureById(Long idFacture) {
        return factureRepo.findById(idFacture)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    }
    @Override
    public void deleteFacture(Long idFacture) {
        factureRepo.deleteById(idFacture);
    }

    @Override
    public Facture updateFacture(Long idFacture, Facture facture) {
        return factureRepo.save( facture);
    }

    @Override
    public Map<String, Long> getInvoiceStatistics() {
        long paidInvoices = factureRepo.countByStatus("Paid");
        long unpaidInvoices = factureRepo.countByStatus("Unpaid");
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("paidInvoices", paidInvoices);
        statistics.put("unpaidInvoices", unpaidInvoices);
        return statistics;
    }
    public Facture updateFactureStatus(Long idFacture, String status) {
        Facture facture = factureRepo.findById(idFacture)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        facture.setStatus(FactureStatus.valueOf(status));
        return factureRepo.save(facture);
    }

    @Override
    public byte[] exportFacturesToExcel() throws IOException {
        List<Facture> factures = getAllFactures();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Factures");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "ID", "Total Amount", "Invoice Date", "Due Date",
                    "Amount Excl. Tax", "Tax", "Status"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Facture facture : factures) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(facture.getIdFacture());
                row.createCell(1).setCellValue(facture.getMontantTotal());
                row.createCell(2).setCellValue(facture.getDateFacture().toString());
                row.createCell(3).setCellValue(facture.getDateEcheance().toString());
                row.createCell(4).setCellValue(facture.getMontantTotalHorsTaxe());
                row.createCell(5).setCellValue(facture.getTva());
                row.createCell(6).setCellValue(facture.getStatus().toString());
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }


}