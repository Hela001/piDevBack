package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Controllers.PaiementController;
import com.esprit.ms.pidevbackend.Entities.Facture;
import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Repositories.FactureRepo;
import com.esprit.ms.pidevbackend.Repositories.PaiementRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PaiemetService implements  IPaiemetService{
    @Autowired
    private PaiementRepo paiementRepo ;
    @Autowired
    private FactureRepo factureRepo;
    private static final Logger logger = LoggerFactory.getLogger(PaiemetService.class);


//@Override
//public Paiement addPaiement(Paiement p, long idFacture) {
//    try {
//        Facture facture = factureRepo.findById(idFacture).orElseThrow();
//        p.setFacture(facture);
//        Paiement saved = paiementRepo.save(p);
//        logger.info("💾 Paiement sauvegardé : ID {}", saved.getIdPaiement());
//        return saved;
//    } catch (Exception e) {
//        logger.error("🔥 Erreur sauvegarde paiement", e);
//        throw e;
//    }
//}


    @Override
    public Paiement modifyPaiement(Paiement p) {
        return paiementRepo.save(p);
    }

    @Override
    public Paiement getPaiement(Long idPaiement) {
        return paiementRepo.findById(idPaiement).orElse(null);
    }

    @Override
    public List<Paiement> getAllPaiement() {
        return (List<Paiement>) paiementRepo.findAll();

    }

    @Override
    public void deletePaiement(Long idPaiement) {
        paiementRepo.deleteById(idPaiement);
    }

//    public boolean existsBySessionId(String sessionId) {
//        return paiementRepo.findBySessionId(sessionId) != null;
//    }

//    @Transactional
//@Override
//public Paiement addPaiement(Paiement paiement, long idFacture) {
//    try {
//        // Récupérer la facture
//        Facture facture = factureRepo.findById(idFacture)
//                .orElseThrow(() -> new RuntimeException("Facture non trouvée: " + idFacture));
//
//        // Établir la relation bidirectionnelle
//        paiement.setFacture(facture);
//        facture.setPaiement(paiement);
//
//        // Sauvegarder le paiement
//        Paiement savedPaiement = paiementRepo.save(paiement);
//        logger.info("Paiement sauvegardé avec ID: {}", savedPaiement.getIdPaiement());
//
//        // Sauvegarder la facture mise à jour
//        factureRepo.save(facture);
//
//        return savedPaiement;
//    } catch (Exception e) {
//        logger.error("Erreur lors de la sauvegarde du paiement: {}", e.getMessage());
//        throw new RuntimeException("Erreur lors de la sauvegarde du paiement", e);
//    }
//}
@Transactional
@Override
public Paiement addPaiement(Paiement paiement, long idFacture) {
    try {
        // Récupérer la facture
        Facture facture = factureRepo.findById(idFacture)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée: " + idFacture));

        // Établir la relation bidirectionnelle
        paiement.setFacture(facture);
        // No need to call facture.setPaiement(paiement) since setFacture handles it

        // Sauvegarder le paiement (this will cascade to facture if needed)
        Paiement savedPaiement = paiementRepo.save(paiement);
        logger.info("Paiement sauvegardé avec ID: {}", savedPaiement.getIdPaiement());

        return savedPaiement;
    } catch (Exception e) {
        logger.error("Erreur lors de la sauvegarde du paiement: {}", e.getMessage(), e);
        throw new RuntimeException("Erreur lors de la sauvegarde du paiement", e);
    }
}

    public boolean existsBySessionId(String sessionId) {
        try {
            return paiementRepo.findBySessionId(sessionId) != null;
        } catch (Exception e) {
            logger.error("Erreur lors de la vérification du sessionId: {}", e.getMessage());
            return false;
        }
    }
}
