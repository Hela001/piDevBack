package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.FicheStatus;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Entities.methodePaiement;
import com.esprit.ms.pidevbackend.Repositories.FicheDePaieRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
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
        if (fiche == null) return;

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=fiche_de_paie_" + idBulletinPaie + ".pdf");

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // Footer implementation
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    try {
                        PdfPTable footerTable = new PdfPTable(1);
                        footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());

                        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
                        Paragraph footer = new Paragraph("Document généré le " + new Date(), normalFont);
                        footer.setAlignment(Element.ALIGN_CENTER);

                        PdfPCell cell = new PdfPCell(footer);
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        footerTable.addCell(cell);

                        footerTable.writeSelectedRows(0, -1,
                                document.leftMargin(),
                                document.bottomMargin() + 20,
                                writer.getDirectContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            document.open();

            // Header with logo
            Image logo = null;
            try {
                InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/dnd.jpg");
                if (logoStream != null) {
                    logo = Image.getInstance(IOUtils.toByteArray(logoStream));
                    logo.scaleToFit(100, 100);
                }
            } catch (Exception e) {
                System.err.println("Logo not loaded: " + e.getMessage());
            }

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{60, 40});

            // Contact information
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Paragraph contactInfo = new Paragraph();
            contactInfo.add(new Chunk("62, Av. UMA 2036 – La Soukra\n", normalFont));
            contactInfo.add(new Chunk("Omrane centre RDC B0.6\n", normalFont));
            contactInfo.add(new Chunk("Tél: +216 97 129 749 / +216 98 268 013\n", normalFont));
            contactInfo.add(new Chunk("Contact@dndserv.com", normalFont));

            PdfPCell contactCell = new PdfPCell(contactInfo);
            contactCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(contactCell);

            // Logo
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (logo != null) {
                logoCell.addElement(new Chunk(logo, 0, 0));
            }
            headerTable.addCell(logoCell);
            document.add(headerTable);

            // Title with spacing
            document.add(Chunk.NEWLINE);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("FICHE DE PAIE", boldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f); // Space after title
            document.add(title);

            // Compact employee information block
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{30, 70});
            infoTable.setSpacingBefore(0f);
            infoTable.setSpacingAfter(0f);

            // Add information without extra spacing
            addCompactTableCell(infoTable, "Nom:", fiche.getNom());
            addCompactTableCell(infoTable, "Date:", fiche.getDatePaiement().toString());
            addCompactTableCell(infoTable, "Statut:", fiche.getStatutPaiementL().toString());
            addCompactTableCell(infoTable, "Méthode:", fiche.getTypePaiement().toString());
            addCompactTableCell(infoTable, "Salaire Brut:", String.format("%.2f DT", fiche.getMontantInitial()));
            addCompactTableCell(infoTable, "Jours Absents:", String.valueOf(fiche.getJoursNonTravailles()));
            addCompactTableCell(infoTable, "Salaire Net:", String.format("%.2f DT", fiche.getMontantFinal()));

            document.add(infoTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method for compact table cells
    private void addCompactTableCell(PdfPTable table, String label, String value) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(2f);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(2f);
        table.addCell(valueCell);
    }
    public List<Fiche_de_paie> getFichesByNom(String nom) {
        return ficheDePaieRepo.findByNom(nom);
    }

    @Override
    public Map<String, Long> getFicheStatistics() {
        long paidFiches = ficheDePaieRepo.countBystatutPaiementL("Paid");
        long unpaidFiches = ficheDePaieRepo.countBystatutPaiementL("Unpaid");
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("paidFiches", paidFiches);
        statistics.put("unpaidFiches", unpaidFiches);
        return statistics;
    }

    @Override
    public long countBystatutPaiementL(String statutPaiementL) {
        return 0;
    }

    @Override
    public Map<String, Object> getUserInfo(String nom) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("salaireBase", 3000);
        userInfo.put("joursAbsence", 2);
        userInfo.put("poste", "Développeur");
        return userInfo;
    }

    @Override
    public Set<Fiche_de_paie> getFichesPaieByUtilisateur(Long idUtilisateur) {
        return null;
    }
}