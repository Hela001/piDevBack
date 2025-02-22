package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Repositories.CommandeRepo;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeService implements  iCommandeService {
    @Autowired
    CommandeRepo commandeRepo;

    @Override
    public List<Commande> getAllComm() {
        return (List<Commande>) commandeRepo.findAll();
    }

    @Override
    public Commande getCommandeById(Long idCommande) {
        return commandeRepo.findById(idCommande).orElse(null);
    }

}
