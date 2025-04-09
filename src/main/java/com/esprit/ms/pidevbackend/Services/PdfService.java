package com.esprit.ms.pidevbackend.Services;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@AllArgsConstructor
public class PdfService {
    public byte[] generateInvoicePdf(Facture facture) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Facture ID: " + facture.getIdFacture()));
        document.add(new Paragraph("Montant Total: " + facture.getMontantTotal()));
        document.close();
        return baos.toByteArray();
    }
}
