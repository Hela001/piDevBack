package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.RapportFinancier;

import java.util.List;

public interface IRapportFinService {
    public RapportFinancier addRapport(RapportFinancier RF);
    public RapportFinancier getRapport( Long idRapport);
    public List<RapportFinancier> getAllRapport() ;
    public void deleteRapport( Long idRapport) ;
    public RapportFinancier modifyRapport( RapportFinancier RF);
}
