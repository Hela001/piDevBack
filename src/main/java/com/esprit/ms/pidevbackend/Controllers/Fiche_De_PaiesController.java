package com.esprit.ms.pidevbackend.Controllers;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Services.IFicheDePaieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Api/ficheDePaie")
@Tag(name = "Gestion Fiche De Paie")
public class Fiche_De_PaiesController {
    @Autowired
    IFicheDePaieService iFicheDePaieService;

    @Operation(description = "Ajouter une fiche de paie dans la base de données")
    @PostMapping
    public Fiche_de_paie addFicheDePaie(@RequestBody Fiche_de_paie ficheDePaie) {
        return iFicheDePaieService.addFicheDePaie(ficheDePaie);
    }

    @Operation(description = "Récupérer une fiche de paie par ID")
    @GetMapping("/{id}/details") // Unique sub-path
    public Fiche_de_paie getFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        return iFicheDePaieService.getFicheDePaieById(idBulletinPaie);
    }

    @Operation(description = "Récupérer toutes les fiches de paie")
    @GetMapping
    public List<Fiche_de_paie> getAllFichesDePaie() {
        return iFicheDePaieService.getAllFichesDePaie();
    }

    @Operation(description = "Supprimer une fiche de paie par ID")
    @DeleteMapping("/{id}")
    public void deleteFicheDePaie(@PathVariable("id") Long idBulletinPaie) {
        iFicheDePaieService.deleteFicheDePaie(idBulletinPaie);
    }

    @Operation(description = "Modifier une fiche de paie")
    @PutMapping("/{id}")
    public ResponseEntity<Fiche_de_paie> updateFicheDePaie(@PathVariable("id") Long idBulletinPaie, @RequestBody Fiche_de_paie ficheDePaie) {
        Fiche_de_paie updatedFiche = iFicheDePaieService.updateFicheDePaie(idBulletinPaie, ficheDePaie);
        return ResponseEntity.ok(updatedFiche);
    }

    @Operation(description = "Calculer le salaire d'une fiche de paie")
    @PostMapping("/{id}") // Unique sub-path
    public ResponseEntity<Fiche_de_paie> calculerSalaire(@PathVariable("id") Long idBulletinPaie) {
        Fiche_de_paie fiche = iFicheDePaieService.calculerSalaire(idBulletinPaie);
        if (fiche != null) {
            return ResponseEntity.ok(fiche);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/print")
    public void imprimerFiche(@PathVariable("id") Long idBulletinPaie, HttpServletResponse response) {
        try {
            // Fetch the Fiche_de_paie object from the database
            Fiche_de_paie fiche = iFicheDePaieService.getFicheDePaieById(idBulletinPaie);

            if (fiche == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fiche de paie non trouvée");
                return;
            }

            // Set response headers for PDF download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=fiche_de_paie_" + idBulletinPaie + ".pdf");

            // Create the PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            // Open the document
            document.open();

            // Add the bordered name
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase(fiche.getNom()));
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(10);
            table.addCell(cell);
            document.add(table);

            // Add other details with <h3> styling
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Payment Status: " + fiche.getStatutPaiementL(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Payment Type: " + fiche.getTypePaiement(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Payment Date: " + fiche.getDatePaiement(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Initial Amount: " + fiche.getMontantInitial(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Days Not Worked: " + fiche.getJoursTravailles(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Final Amount: " + fiche.getMontantFinal(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

            // Close document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}