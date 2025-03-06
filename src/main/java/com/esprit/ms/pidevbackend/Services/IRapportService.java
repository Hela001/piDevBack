package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Entities.RapportQualite;

import java.util.List;

public interface IRapportService  {
    List<RapportQualite> getAllRapportQualite();

    void deleteRapportQualite(Long id);
    RapportQualite updateRapportQualite(RapportQualite rapportQualite );
    RapportQualite getRapportQualiteId(Long id);

}
