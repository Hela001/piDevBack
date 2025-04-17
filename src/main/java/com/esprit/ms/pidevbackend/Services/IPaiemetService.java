package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Paiement;

import java.util.List;

public interface IPaiemetService {
    public Paiement addPaiement(Paiement p, long idP);
    public Paiement getPaiement( Long idPaiement);
    public List<Paiement> getAllPaiement() ;
    public void deletePaiement( Long idPaiement) ;
    public Paiement modifyPaiement( Paiement p);
}