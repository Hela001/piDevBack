package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.FactureStatus;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IFactureService {
    Facture addFacture(Facture facture);
    List<Facture> getAllFactures();
    Facture getFactureById(Long idFacture);
    void deleteFacture(Long idFacture);
    Facture updateFacture(Long idFacture, Facture facture);
    Map<String, Long> getInvoiceStatistics();
    void sendEmail(String recipient , String body , String subject);

    Facture updateFactureStatus(Long idFacture, String newStatus);

    byte[] exportFacturesToExcel() throws IOException;
    void updateFactureStatus(Long factureId, FactureStatus status);

    Set<Facture> getFacturesByUtilisateur(Long idUtilisateur);
}
