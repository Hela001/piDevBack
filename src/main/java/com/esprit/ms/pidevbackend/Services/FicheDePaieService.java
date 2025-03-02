package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Repositories.FicheDePaieRepo;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;

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
    @Override
    public Fiche_de_paie updateFicheDePaie(Fiche_de_paie ficheDePaie) {
        return ficheDePaieRepo.save(ficheDePaie);
    }
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
                PdfWriter.getInstance(document, outputStream);

                // Open document and add content
                document.open();
                document.add(new Paragraph("Fiche de Paie"));
                document.add(new Paragraph("Montant Initial: " + fiche.getMontantInitial()));
                document.add(new Paragraph("Jours Non Travaillés: " + fiche.getJoursTravailles()));
                document.add(new Paragraph("Type de Paiement: " + fiche.getTypePaiement()));
                document.add(new Paragraph("Date de Paiement: " + fiche.getDatePaiement()));
                document.add(new Paragraph("Statut de Paiement: " + fiche.getStatutPaiementL()));
                document.add(new Paragraph("Montant Final: " + fiche.getMontantFinal()));
                document.add(new Paragraph("Nom Utilisateur: " + fiche.getNom()));
                document.close();

                // Flush and close the output stream
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
