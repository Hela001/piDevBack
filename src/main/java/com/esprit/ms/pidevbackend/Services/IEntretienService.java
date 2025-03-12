package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.DemandeEmploi;
import com.esprit.ms.pidevbackend.Entities.Entretien;
import com.esprit.ms.pidevbackend.Entities.StatusDemande;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IEntretienService {
    DemandeEmploi ajouterDemandeEmploi(DemandeEmploi demandeEmploi);
    DemandeEmploi getDemandeEmploiById(Long id);
    DemandeEmploi updateDemandeEmploi(DemandeEmploi demandeEmploi);
    List<DemandeEmploi> getAllDemandeEmploi();
    void deleteDemandeEmploiById(Long id);

     Entretien ajouterEntretienPourDemande(Long idDemande);

    List<Object[]> getDemandesByDateEntretien(LocalDate date);

    List<Entretien> getEntretiensDuJour();
    List<DemandeEmploi> getDemandeEmploiByStatus(StatusDemande statusDemande);
}
