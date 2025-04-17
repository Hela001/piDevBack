package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import com.esprit.ms.pidevbackend.Repositories.PaiementRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiemetService implements  IPaiemetService{
    @Autowired
    PaiementRepo paiementRepo ;
    FactureRepo factureRepo;


    @Override
    public Paiement addPaiement(Paiement p, long idP) {
        Facture facture = factureRepo.findById(idP).orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        p.setFacture(facture); // Associer le paiement à la facture
        return paiementRepo.save(p);
    }

    @Override
    public Paiement getPaiement(Long idPaiement) {
        return paiementRepo.findById(idPaiement).orElse(null);
    }

    @Override
    public List<Paiement> getAllPaiement() {
        return (List<Paiement>) paiementRepo.findAll();

    }

    @Override
    public void deletePaiement(Long idPaiement) {
        paiementRepo.deleteById(idPaiement);
    }

    @Override
    public Paiement modifyPaiement(Paiement p) {
        return paiementRepo.save(p);
    }
}
