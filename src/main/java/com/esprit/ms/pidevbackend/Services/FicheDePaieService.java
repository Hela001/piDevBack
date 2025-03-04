package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Repositories.FicheDePaieRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class FicheDePaieService implements IFicheDePaieService {
    FicheDePaieRepo ficheDePaieRepo;

    @Override
    public Fiche_de_paie addFicheDePaie(Fiche_de_paie ficheDePaie) {
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
  /*
  public List<Fiche_de_paie> getAllFichesDePaie() {
        return ficheDePaieRepo.findAllByOrderByIdBulletinPaieDesc();
    }
    */

    @Override
    public void deleteFicheDePaie(Long idBulletinPaie) {
        ficheDePaieRepo.deleteById(idBulletinPaie);

    }

    @Override
    public Fiche_de_paie updateFicheDePaie(Long idBulletinPaie, Fiche_de_paie ficheDePaie) {
        return ficheDePaieRepo.save(ficheDePaie);
    }

    /**
     * @Override public Fiche_de_paie updateFicheDePaie(Fiche_de_paie ficheDePaie) {
     * return ficheDePaieRepo.save(ficheDePaie);
     * }
     **/

    @Override
    public Fiche_de_paie calculerSalaire(Long idBulletinPaie) {
        Fiche_de_paie fiche = getFicheDePaieById(idBulletinPaie);
        if (fiche != null) {
            double salaireJournalier = fiche.getMontantInitial() / 30;
            float montantFinal = (float) (fiche.getMontantInitial() - (fiche.getJoursTravailles() * salaireJournalier));
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

                // Header: Company logo and name
                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);
                headerTable.setWidths(new float[]{50, 50}); // Left and Right column proportions

                // Left cell: Company contact info
                PdfPCell contactCell = new PdfPCell(new Phrase("(123) 1234-567-8901\ninfodomain.com\nMon - Sat 8:00 - 17:30, Sunday - CLOSED", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                contactCell.setBorder(0);
                contactCell.setVerticalAlignment(Element.ALIGN_LEFT);
                contactCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                contactCell.setPaddingLeft(10);
                headerTable.addCell(contactCell);

                // Right cell: Company name and logo
                PdfPCell logoCell = new PdfPCell();
                logoCell.setBorder(0);
                logoCell.setVerticalAlignment(Element.ALIGN_RIGHT);
                logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

                // Load company logo image (ensure you have a valid image path)
                Image logo = Image.getInstance("path_to_logo_image.jpg");  // Update with your image path
                logo.scaleToFit(100, 50);  // Scale logo to desired size
                logoCell.addElement(logo);
                logoCell.addElement(new Phrase("Construction Co.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                headerTable.addCell(logoCell);

                document.add(headerTable); // Add header to document

                // Add a space between header and the rest of the document content
                document.add(new Paragraph(" "));

                // Add pay slip content
                document.add(new Paragraph("Pay Slip"));
                document.add(new Paragraph("User Name: " + fiche.getNom()));
                document.add(new Paragraph("Payment Status: " + fiche.getStatutPaiementL()));
                document.add(new Paragraph("Payment Type: " + fiche.getTypePaiement()));
                document.add(new Paragraph("Payment Date: " + fiche.getDatePaiement()));
                document.add(new Paragraph("Initial Amount: " + fiche.getMontantInitial()));
                document.add(new Paragraph("Days Not Worked: " + fiche.getJoursTravailles()));
                document.add(new Paragraph("Final Amount: " + fiche.getMontantFinal()));

                // Footer: Contact info (aligned left)
                Paragraph footerLeft = new Paragraph("(123) 1234-567-8901\ninfodomain.com\nMon - Sat 8:00 - 17:30, Sunday - CLOSED",
                        FontFactory.getFont(FontFactory.HELVETICA, 8));
                footerLeft.setAlignment(Element.ALIGN_LEFT);
                document.add(footerLeft);

                // Footer: Company name (aligned right)
                Paragraph footerRight = new Paragraph("Construction Co.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
                footerRight.setAlignment(Element.ALIGN_RIGHT);
                document.add(footerRight);

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