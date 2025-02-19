package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;

import java.util.List;

public interface IFactureService {
    Facture addFacture (Facture facture) ;
    List<Facture> getAllFactures();
    Facture getFactureById(Long idFacture);
    void deleteFacture ( Long idFacture);
    Facture updateFacture( Facture facture);
}
