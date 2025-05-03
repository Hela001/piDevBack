package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.RapportFinancier;

import java.util.List;

public interface IRapportFinService {
    RapportFinancier genererRapport(Long idUtilisateur);

    List<RapportFinancier> getAllRapports();

    RapportFinancier getRapportById(Long id);
}
