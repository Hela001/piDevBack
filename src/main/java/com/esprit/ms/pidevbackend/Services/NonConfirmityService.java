package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Controllers.NonConfirmityController;
import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;
import com.esprit.ms.pidevbackend.Repositorys.ActionCorrectiveRepository;
import com.esprit.ms.pidevbackend.Repositorys.NonConformiteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
@Slf4j
@Service
@AllArgsConstructor
public class NonConfirmityService implements INonConfirmity{
@Autowired
    NonConformiteRepository nonConformiteRepository;
@Autowired
    ActionCorrectiveRepository actionCorrectiveRepository;


    @Override
    public List<NonConfirmity> getAllNonConfirmity()
    {

        return nonConformiteRepository.findAll();
    }






@Override
    public NonConfirmity addNonConfirmity(NonConfirmity nonConfirmity) {
    System.out.println("🔄 Enregistrement en cours : " + nonConfirmity);
    NonConfirmity saved = nonConformiteRepository.save(nonConfirmity);
    System.out.println("✅ Enregistré avec ID : " + saved.getIdNC());
    return saved;
    }
    @Override
    public void deleteNonConfirmity(Long id) {
           nonConformiteRepository.deleteById(id);
    }

    @Override
    public NonConfirmity updateNonConfirmity(NonConfirmity nonConfirmity) {
        // Récupérer la non-conformité existante
        NonConfirmity existingNonConfirmity = nonConformiteRepository.findById(nonConfirmity.getIdNC())
                .orElseThrow(() -> new EntityNotFoundException("NonConfirmity not found"));

        // Mettre à jour les champs de la non-conformité sans toucher aux actions correctives
        existingNonConfirmity.setDescription(nonConfirmity.getDescription());
        existingNonConfirmity.setDateDetection(nonConfirmity.getDateDetection());
        existingNonConfirmity.setTypeNonConfirm(nonConfirmity.getTypeNonConfirm());
        existingNonConfirmity.setTypeNonConfirm(nonConfirmity.getTypeNonConfirm());
        existingNonConfirmity.setStatutNonConfirm(nonConfirmity.getStatutNonConfirm());

        // Sauvegarder la non-conformité mise à jour
        return nonConformiteRepository.save(existingNonConfirmity);
    }


    @Override
    public NonConfirmity getNonConfirmityById(Long id) {
        return nonConformiteRepository.findById(id).get();
    }
    @Override
    public NonConfirmity addActionCorrective(Long nonConfirmityId, ActionCorrective actionCorrective) {
        NonConfirmity nonConfirmity = nonConformiteRepository.findById(nonConfirmityId)
                .orElseThrow(() -> new RuntimeException("NonConfirmity not found with ID: " + nonConfirmityId));

        // Ajouter l'action corrective à la liste existante
        nonConfirmity.getActionCorrective().add(actionCorrective);

        // Sauvegarder la non-conformité avec la nouvelle action corrective
        return nonConformiteRepository.save(nonConfirmity);
    }

    public List<ActionCorrective> getActionsCorrectivesByNonConfirmityId(Long nonConfirmityId) {
        NonConfirmity nonConfirmity = nonConformiteRepository.findById(nonConfirmityId)
                .orElseThrow(() -> new RuntimeException("NonConfirmity not found with ID: " + nonConfirmityId));

        // Retourner la liste des actions correctives associées à cette non-conformité
        return nonConfirmity.getActionCorrective();
    }
}
