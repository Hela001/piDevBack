package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Services.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("Api/facture")
@Tag(name = "Gestion Facturation")
public class FactureController {
    @Autowired
    private PdfService pdfService;
    @Autowired
    private IFactureService iFactureService;

    @Operation(description = "Ajouter une facture")
    @PostMapping
    public Facture AjoutFacture(@RequestBody Facture facture) {
        return iFactureService.addFacture(facture);
    }

//    @Operation(description = "Récupérer une facture par son ID")
//    @GetMapping("/{id}")
//    public Facture GetFactureById(@PathVariable("id") Long idFacture) {
//        return iFactureService.getFactureById(idFacture);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFactureById(@PathVariable("id") Long idFacture) {
        try {
            Facture facture = iFactureService.getFactureById(idFacture);
            if (facture == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Facture non trouvée"));
            }
            return ResponseEntity.ok(facture);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("sendEmail")
    public String sendEmail(){
        iFactureService.sendEmail("akremizayneb99@gmail.com","Invoice Added","Invoice");
   return "Sent successfuly";
    }

    @Operation(description = "Récupérer toutes les factures")
    @GetMapping
    public List<Facture> getAllFactures() {
        return iFactureService.getAllFactures();
    }

    @Operation(description = "Supprimer une facture par son ID")
    @DeleteMapping("/{id}")
    public void DeleteFacture(@PathVariable("id") Long idFacture) {
        iFactureService.deleteFacture(idFacture);
    }

    @PutMapping("/{id}")
    public Facture updateFacture(@PathVariable("id") Long idFacture, @RequestBody Facture facture) {
        return iFactureService.updateFacture(idFacture, facture);
    }
    @Operation(description = "Récupérer les statistiques des factures")
    @GetMapping("/statistics")
    public Map<String, Long> getInvoiceStatistics() {
        return iFactureService.getInvoiceStatistics();
    }

    @Operation(description = "Mettre à jour le statut d'une facture")
    @PutMapping("/{id}/status")
    public Facture updateFactureStatus(
            @PathVariable("id") Long idFacture,
            @RequestBody Map<String, String> statusRequest) {

        String newStatus = statusRequest.get("status");
        return iFactureService.updateFactureStatus(idFacture, newStatus);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long idFacture) {
        try {
            System.out.println("Tentative de génération PDF pour facture ID: " + idFacture);
            Facture facture = iFactureService.getFactureById(idFacture);

            if (facture == null) {
                System.out.println("Facture non trouvée pour ID: " + idFacture);
                return ResponseEntity.notFound().build();
            }

            byte[] pdf = pdfService.generateInvoicePdf(facture);
            System.out.println("PDF généré avec succès, taille: " + pdf.length + " bytes");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("facture_" + idFacture + ".pdf").build());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur génération PDF: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}