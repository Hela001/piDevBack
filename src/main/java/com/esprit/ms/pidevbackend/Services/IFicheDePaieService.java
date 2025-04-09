package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface IFicheDePaieService {
    Fiche_de_paie addFicheDePaie(Fiche_de_paie ficheDePaie);
    Fiche_de_paie getFicheDePaieById(Long idBulletinPaie);
    List<Fiche_de_paie> getAllFichesDePaie();
    void deleteFicheDePaie(Long idBulletinPaie);
    Fiche_de_paie updateFicheDePaie(Long idBulletinPaie ,Fiche_de_paie ficheDePaie);
    Fiche_de_paie calculerSalaire(Long idBulletinPaie);

    void imprimerFiche(Long idBulletinPaie, HttpServletResponse response);

    Map<String, Long> getFicheStatistics();
    long countBystatutPaiementL(String statutPaiementL);
    public Map<String, Object> getUserInfo(String nom);


}
