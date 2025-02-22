package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Commande;
import com.esprit.ms.pidevbackend.Entities.Facture;

import java.util.List;

public interface iCommandeService {
    List<Commande> getAllComm();
    Commande getCommandeById(Long idCommande);
    }



