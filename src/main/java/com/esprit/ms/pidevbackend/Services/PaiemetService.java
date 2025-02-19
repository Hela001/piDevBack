package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Repositories.PaiementRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiemetService implements  IPaiemetService{
    PaiementRepo paiementRepo ;

    @Override
    public Paiement addPaiement(Paiement p) {
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

