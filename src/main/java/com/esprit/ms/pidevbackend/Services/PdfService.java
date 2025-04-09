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
    private static final BaseColor PRIMARY_COLOR = new BaseColor(51, 102, 153); // Bleu DND Serv
    private static final BaseColor LIGHT_GRAY = new BaseColor(240, 240, 240);
    private static final BaseColor DARK_GRAY = new BaseColor(80, 80, 80);

    public byte[] generateInvoicePdf(Facture facture) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();
        // Configuration des polices
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, PRIMARY_COLOR);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DARK_GRAY);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, DARK_GRAY);


        try {
            Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
            logo.scaleToFit(100, 60);
            logo.setAbsolutePosition(20, 750);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("Logo non chargé, continuation sans logo");
            // Optionnel : ajoutez un texte à la place
            document.add(new Paragraph("DND Serv", titleFont));
        }


        // En-tête de la société
        Paragraph company = new Paragraph("DND Serv", titleFont);
        company.setAlignment(Element.ALIGN_RIGHT);
        company.setSpacingBefore(30);
        document.add(company);

        Paragraph address = new Paragraph("Adresse de la société\nTéléphone: XX XX XX XX\nEmail: contact@dndserv.com", normalFont);
        address.setAlignment(Element.ALIGN_RIGHT);
        document.add(address);

        // Titre du document
        Paragraph invoiceTitle = new Paragraph("FACTURE", titleFont);
        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        invoiceTitle.setSpacingBefore(20);
        invoiceTitle.setSpacingAfter(20);
        document.add(invoiceTitle);

        // Informations de la facture
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(10);
        infoTable.setSpacingAfter(20);

        addInfoCell(infoTable, "Facture N°:", facture.getIdFacture().toString(), boldFont, normalFont);
        addInfoCell(infoTable, "Date d'émission:", String.valueOf(facture.getDateFacture()), boldFont, normalFont);
        addInfoCell(infoTable, "Date d'échéance:", String.valueOf(facture.getDateEcheance()), boldFont, normalFont);
        addInfoCell(infoTable, "Statut:", String.valueOf(facture.getStatus()), boldFont,
                new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,
                        "Paid".equals(facture.getStatus()) ? BaseColor.GREEN : BaseColor.RED));

        document.add(infoTable);

        // Détails des montants
        PdfPTable amountsTable = new PdfPTable(2);
        amountsTable.setWidthPercentage(50);
        amountsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        amountsTable.setSpacingBefore(20);

        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRANCE);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        addAmountRow(amountsTable, "Montant HT:", format.format(facture.getMontantTotalHorsTaxe()), boldFont);
        addAmountRow(amountsTable, "TVA:", format.format(facture.getTva()), boldFont);
        addAmountRow(amountsTable, "Montant TTC:", format.format(facture.getMontantTotal()),
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, PRIMARY_COLOR));

        document.add(amountsTable);

        document.close();
        return baos.toByteArray();
    }

    private void addInfoCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }

    private void addAmountRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valueCell);
    }
}
