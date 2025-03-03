package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;

import java.util.List;
import java.util.Map;

public interface IFactureService {
    Facture addFacture(Facture facture);
    List<Facture> getAllFactures();
    Facture getFactureById(Long idFacture);
    void deleteFacture(Long idFacture);
    Facture updateFacture(Long idFacture, Facture facture);
    Map<String, Long> getInvoiceStatistics();
    void sendEmail(String recipient , String body , String subject);

}
