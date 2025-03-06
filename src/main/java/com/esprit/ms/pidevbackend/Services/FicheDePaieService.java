package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.FicheStatus;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Entities.methodePaiement;
import com.esprit.ms.pidevbackend.Repositories.FicheDePaieRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FicheDePaieService implements IFicheDePaieService {
    FicheDePaieRepo ficheDePaieRepo;

    @Override
    public Fiche_de_paie addFicheDePaie(Fiche_de_paie ficheDePaie) {
        // Définir des valeurs par défaut si nécessaire
        if (ficheDePaie.getDatePaiement() == null) {
            ficheDePaie.setDatePaiement(new Date()); // Date actuelle
        }
        if (ficheDePaie.getTypePaiement() == null) {
            ficheDePaie.setTypePaiement(methodePaiement.ESPECES); // Valeur par défaut
        }
        if (ficheDePaie.getNom() == null) {
            ficheDePaie.setNom("Employé Inconnu"); // Valeur par défaut
        }
        if (ficheDePaie.getMontantInitial() == null) {
            ficheDePaie.setMontantInitial(0.0f); // Valeur par défaut
        }

//        // Calculer le montant final
//        double salaireJournalier = ficheDePaie.getMontantInitial() / 30;
//        float montantFinal = (float) (ficheDePaie.getMontantInitial() - (ficheDePaie.getJoursNonTravailles() * salaireJournalier));
//        ficheDePaie.setMontantFinal(montantFinal);

        // Définir le statut par défaut à "unpaid"
        ficheDePaie.setStatutPaiementL(FicheStatus.Unpaid);

        // Sauvegarder la fiche de paie
        return ficheDePaieRepo.save(ficheDePaie);
    }

    @Override
    public Fiche_de_paie getFicheDePaieById(Long idBulletinPaie) {
        return ficheDePaieRepo.findById(idBulletinPaie).orElse(null);
    }

    @Override
    public List<Fiche_de_paie> getAllFichesDePaie() {
        return ficheDePaieRepo.findAll();
    }

    @Override
    public void deleteFicheDePaie(Long idBulletinPaie) {
        ficheDePaieRepo.deleteById(idBulletinPaie);

    }

    @Override
    public Fiche_de_paie updateFicheDePaie(Long idBulletinPaie, Fiche_de_paie ficheDePaie) {
        Fiche_de_paie existingFiche = getFicheDePaieById(idBulletinPaie);
        if (existingFiche != null) {
            // Mettre à jour uniquement le statut de paiement
            existingFiche.setStatutPaiementL(ficheDePaie.getStatutPaiementL());
            return ficheDePaieRepo.save(existingFiche);
        }
        return null;
    }

    @Override
    public Fiche_de_paie calculerSalaire(Long idBulletinPaie) {
        Fiche_de_paie fiche = getFicheDePaieById(idBulletinPaie);
        if (fiche != null) {
            double salaireJournalier = fiche.getMontantInitial() / 30;
            float montantFinal = (float) (fiche.getMontantInitial() - (fiche.getJoursNonTravailles() * salaireJournalier));
            fiche.setMontantFinal(montantFinal);
            return updateFicheDePaie(idBulletinPaie, fiche);
        }
        return null;
    }

    @Override
    public void imprimerFiche(Long idBulletinPaie, HttpServletResponse response) {
        Fiche_de_paie fiche = getFicheDePaieById(idBulletinPaie);
        if (fiche != null) {
            try {
                // Set response headers for PDF download
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=fiche_de_paie_" + idBulletinPaie + ".pdf");

                // Create PDF document
                Document document = new Document();
                OutputStream outputStream = response.getOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);

                // Open document and add content
                document.open();

                // Header: Company contact info and name
                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);
                headerTable.setWidths(new float[]{50, 50}); // Left and Right column proportions

                // Left cell: Company contact info with emojis
                PdfPCell contactCell = new PdfPCell();
                contactCell.setBorder(0);
                contactCell.setVerticalAlignment(Element.ALIGN_LEFT);
                contactCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                contactCell.setPaddingLeft(10);

                // Add contact info with emojis
                Paragraph contactInfo = new Paragraph();
                contactInfo.add(new Chunk("📞 ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                contactInfo.add(new Chunk("+(123) 1234-567-8901\n", FontFactory.getFont(FontFactory.HELVETICA, 10)));
                contactInfo.add(new Chunk("📧 ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                contactInfo.add(new Chunk("infodomain.com\n", FontFactory.getFont(FontFactory.HELVETICA, 10)));
                contactInfo.add(new Chunk("🟢 ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                contactInfo.add(new Chunk("Mon - Sat 8:00 - 17:30, Sunday - CLOSED", FontFactory.getFont(FontFactory.HELVETICA, 10)));
                contactCell.addElement(contactInfo);
                headerTable.addCell(contactCell);

                // Right cell: Company name with logo 🏗️
                PdfPCell logoCell = new PdfPCell();
                logoCell.setBorder(0);
                logoCell.setVerticalAlignment(Element.ALIGN_RIGHT);
                logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

                // Add company name with logo 🏗️
                Paragraph companyName = new Paragraph();
                companyName.add(new Chunk("🏗️ ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                companyName.add(new Chunk("Construction", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                logoCell.addElement(companyName);
                headerTable.addCell(logoCell);

                document.add(headerTable); // Add header to document

                // Add a space between header and the rest of the document content
                document.add(new Paragraph(" "));

                // Add pay slip content
                Paragraph paySlipTitle = new Paragraph("Pay Slip", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                paySlipTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(paySlipTitle);

                // Add a space after the title
                document.add(new Paragraph(" "));

                // Add payslip details with bold labels
                Paragraph userInfo = new Paragraph();
                userInfo.add(new Chunk("User Name: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                userInfo.add(new Chunk(fiche.getNom(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(userInfo);

                Paragraph paymentStatus = new Paragraph();
                paymentStatus.add(new Chunk("Payment Status: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                paymentStatus.add(new Chunk(fiche.getStatutPaiementL().toString(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(paymentStatus);

                Paragraph paymentType = new Paragraph();
                paymentType.add(new Chunk("Payment Type: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                paymentType.add(new Chunk(fiche.getTypePaiement().toString(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(paymentType);

                Paragraph paymentDate = new Paragraph();
                paymentDate.add(new Chunk("Payment Date: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                paymentDate.add(new Chunk(fiche.getDatePaiement().toString(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(paymentDate);

                Paragraph initialAmount = new Paragraph();
                initialAmount.add(new Chunk("Initial Amount: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                initialAmount.add(new Chunk(String.valueOf(fiche.getMontantInitial()), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(initialAmount);

                Paragraph daysNotWorked = new Paragraph();
                daysNotWorked.add(new Chunk("Days Not Worked: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                daysNotWorked.add(new Chunk(String.valueOf(fiche.getJoursNonTravailles()), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(daysNotWorked);

                Paragraph finalAmount = new Paragraph();
                finalAmount.add(new Chunk("Final Amount: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                finalAmount.add(new Chunk(String.valueOf(fiche.getMontantFinal()), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(finalAmount);

                // Close document
                document.close();

                // Flush and close the output stream
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<Fiche_de_paie> getFichesByNom(String nom) {
        return ficheDePaieRepo.findByNom(nom);
    }
}