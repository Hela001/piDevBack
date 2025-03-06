package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Entities.RapportFinancier;
import com.esprit.ms.pidevbackend.Entities.RapportStatus;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import com.esprit.ms.pidevbackend.Repositories.FicheDePaieRepo;
import com.esprit.ms.pidevbackend.Repositories.RapportfinRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RapportFinService implements IRapportFinService{
    RapportfinRepo rapportfinRepo ;

    @Autowired
    private FactureRepo factureRepo;

    @Autowired
    private FicheDePaieRepo ficheDePaieRepo;

    public RapportFinancier generateRapport(Long idUtilisateur, double budget) {
        // Récupérer toutes les factures pour l'utilisateur
        Set<Facture> factures = factureRepo.findByIdUtilisateur(idUtilisateur);

        // Récupérer toutes les fiches de paie pour l'utilisateur
        Set<Fiche_de_paie> fichesDePaie = ficheDePaieRepo.findByIdUtilisateur(idUtilisateur);

        // Calcul du total des dépenses à partir des factures (montantTotalHorsTaxe)
        double totalDépensesFactures = factures.stream()
                .mapToDouble(Facture::getMontantTotalHorsTaxe)
                .sum();

        // Calcul du total des dépenses à partir des fiches de paie (montantFinal)
        double totalDépensesFicheDePaie = fichesDePaie.stream()
                .mapToDouble(fiche -> fiche.getMontantFinal().doubleValue())
                .sum();

        // Total des dépenses (factures + fiches de paie)
        double totalDépenses = totalDépensesFactures + totalDépensesFicheDePaie;

        // Créer un rapport financier avec les résultats calculés
        RapportFinancier rapport = new RapportFinancier();
        rapport.setIdUtilisateur(idUtilisateur);
        rapport.setDépense(totalDépenses);
        rapport.setSalaire((float) totalDépensesFicheDePaie);  // Salaire total basé sur les fiches de paie
        rapport.setStatus(totalDépenses < budget ? RapportStatus.RENTABLE : RapportStatus.DEFICIT); // Comparaison des dépenses et du budget

        // Relier les factures et fiches de paie au rapport financier
        rapport.setFactures(factures);
        rapport.setFicheDePaies(fichesDePaie);

        // Sauvegarder le rapport si nécessaire (à adapter selon votre logique métier)
        // rapportRepository.save(rapport);

        return rapport;
    }


}
