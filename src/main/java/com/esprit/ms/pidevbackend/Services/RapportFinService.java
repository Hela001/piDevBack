package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import com.esprit.ms.pidevbackend.Entities.RapportFinancier;
import com.esprit.ms.pidevbackend.Entities.RapportStatus;
import com.esprit.ms.pidevbackend.Repositories.RapportfinRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RapportFinService implements IRapportFinService{
    private static final Logger log = LoggerFactory.getLogger(RapportFinService.class);

    @Autowired
    private RapportfinRepo rapportfinRepo;

    @Autowired
    private FactureService factureService;

    @Autowired
    private FicheDePaieService ficheDePaieService;

    @Transactional
    public RapportFinancier genererRapport(Long idUtilisateur) {
        log.info("Génération du rapport financier pour l'utilisateur: {}", idUtilisateur);

        RapportFinancier rapport = new RapportFinancier();
        rapport.setIdUtilisateur(idUtilisateur);

        try {
            // Récupérer toutes les factures (retourne un Set vide si null)
            Set<Facture> factures = factureService.getFacturesByUtilisateur(idUtilisateur);
            rapport.setFactures(factures != null ? factures : Collections.emptySet());

            // Récupérer toutes les fiches de paie (retourne un Set vide si null)
            Set<Fiche_de_paie> fichePaies = ficheDePaieService.getFichesPaieByUtilisateur(idUtilisateur);
            rapport.setFicheDePaies(fichePaies != null ? fichePaies : Collections.emptySet());

            // Calculer les totaux
            calculerTotaux(rapport);

            // Déterminer le status
            determinerStatus(rapport);

            return rapportfinRepo.save(rapport);
        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport pour l'utilisateur {}: {}", idUtilisateur, e.getMessage());
            throw new RuntimeException("Erreur lors de la génération du rapport", e);
        }
    }

    private void calculerTotaux(RapportFinancier rapport) {
        // Calculer le total des dépenses (somme des factures)
        double totalDepenses = rapport.getFactures() != null ?
                rapport.getFactures().stream()
                        .mapToDouble(Facture::getMontantTotal)
                        .sum() : 0.0;
        rapport.setDepense(totalDepenses);

        // Calculer le total des salaires
        float totalSalaires = rapport.getFicheDePaies() != null ?
                (float) rapport.getFicheDePaies().stream()
                        .mapToDouble(Fiche_de_paie::getMontantFinal)
                        .sum() : 0.0f;
        rapport.setSalaire(totalSalaires);

        // Le budget devrait être défini selon vos règles métier
        rapport.setBudget(100000); // À adapter selon vos besoins
    }

    private void determinerStatus(RapportFinancier rapport) {
        // Calculer la différence entre budget et dépenses
        double difference = rapport.getBudget() - (rapport.getDepense() + rapport.getSalaire());

        // Si la différence est positive, le rapport est RENTABLE
        // Sinon, il est en DEFICIT
        rapport.setStatus(difference >= 0 ? RapportStatus.RENTABLE : RapportStatus.DEFICIT);
    }

    public List<RapportFinancier> getAllRapports() {
        return rapportfinRepo.findAll();
    }

    public RapportFinancier getRapportById(Long id) {
        return rapportfinRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));
    }
}