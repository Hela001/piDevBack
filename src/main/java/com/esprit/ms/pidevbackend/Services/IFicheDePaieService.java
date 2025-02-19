package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;

import java.util.List;

public interface IFicheDePaieService {
    Fiche_de_paie addFicheDePaie(Fiche_de_paie ficheDePaie);
    Fiche_de_paie getFicheDePaieById(Long idBulletinPaie);
    List<Fiche_de_paie> getAllFichesDePaie();
    void deleteFicheDePaie(Long idBulletinPaie);
    Fiche_de_paie updateFicheDePaie(Fiche_de_paie ficheDePaie);

}
