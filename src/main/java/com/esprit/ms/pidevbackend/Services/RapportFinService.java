package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.RapportFinancier;
import com.esprit.ms.pidevbackend.Repositories.RapportfinRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RapportFinService implements IRapportFinService{
    RapportfinRepo rapportfinRepo ;

    @Override
    public RapportFinancier addRapport(RapportFinancier RF) {
        return null;
    }

    @Override
    public RapportFinancier getRapport(Long idRapport) {
        return null;
    }

    @Override
    public List<RapportFinancier> getAllRapport() {
        return null;
    }

    @Override
    public void deleteRapport(Long idRapport) {

    }

    @Override
    public RapportFinancier modifyRapport(RapportFinancier RF) {
        return null;
    }
}

