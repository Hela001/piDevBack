package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FactureService implements  IFactureService{
    @Autowired
    FactureRepo factureRepo;
    public Facture addFacture (Facture facture) {
        return  factureRepo.save(facture);
    }

    @Override
    public List<Facture> getAllFactures() {
        return (List<Facture>) factureRepo.findAll();
    }

    @Override
    public Facture getFactureById(Long idFacture) {
        return factureRepo.findById(idFacture).get();
    }

    @Override
    public void deleteFacture(Long idFacture) {
        factureRepo.deleteById(idFacture);

    }

    @Override
    public Facture updateFacture( Facture f) {
        return factureRepo.save(f);

    }





}
