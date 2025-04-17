package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Priorite;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Repositories.MissionRepository;
import com.esprit.ms.pidevbackend.Repositories.TacheRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TacheServices implements ITacheServices {
    @Autowired
    private NotificationService notificationService;
    private final TacheRepository tacheRepository;
    private final MissionRepository missionRepository;

    @Autowired
    public TacheServices(TacheRepository tacheRepository, MissionRepository missionRepository) {
        this.tacheRepository = tacheRepository;
        this.missionRepository = missionRepository;
    }

    @Override
    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache getTacheById(Long id) {
        return tacheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id));
    }

    @Override
    public Tache addTache(long idM, Tache tache) {
        Mission mission = missionRepository.findById(idM).orElseThrow(() ->
                new EntityNotFoundException("Mission non trouvée avec l'ID : " + idM));

        tache.setMission(mission); // Associer la tâche à la mission
        return tacheRepository.save(tache); // Sauvegarder et retourner la tâche créée
    }

    @Override
    public Tache updateTache(Long id, Tache tache) {
        Tache existingTache = tacheRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id));

        // Mettre à jour les propriétés de l'objet existant avec les nouvelles valeurs
        existingTache.setNom(tache.getNom());
        existingTache.setPriorite(tache.getPriorite());
        existingTache.setEtatTache(tache.getEtatTache());
        // Mettre à jour toutes les autres propriétés nécessaires

        return tacheRepository.save(existingTache); // Sauvegarder et retourner la tâche mise à jour
    }

    @Override
    public boolean deleteTache(Long id) {
        Optional<Tache> tache = tacheRepository.findById(id);
        if (tache.isPresent()) {
            tacheRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id);
        }
    }

    @Override
    public List<Tache> getTasksByMission(long missionId) {
        return tacheRepository.findByMission_idMission(missionId);
    }

    @Override
    public List<Tache> searchTaches(String nom, Status etat, Priorite priorite) {
        return tacheRepository.searchTaches(nom, etat, priorite);
    }
    @Transactional
    public Tache changerStatutTache(Long id, Status nouveauStatut) {
        Optional<Tache> tacheOptional = tacheRepository.findById(id);
        if (!tacheOptional.isPresent()) {
            throw new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id);
        }
        Tache tache = tacheOptional.get();
        tacheRepository.updateStatus(id, nouveauStatut);
        notifyTaskUpdate(id);
        return tache;
    }




    public void notifyTaskUpdate(Long tacheId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + tacheId));

        String destinataire = "neflahela@gmail.com"; // destinataire fixe
        String nomProjet = tache.getMission().getProjet().getNom(); // tu dois avoir une relation Mission → Projet
        String nomMission = tache.getMission().getNom();
        String nomTache = tache.getNom();
        String nouvelEtat = tache.getEtatTache().toString();
        String employeNom = "Hela Nefla"; // à récupérer dynamiquement plus tard si possible

        notificationService.envoyerNotification(
                destinataire,
                nomProjet,
                nomMission,
                nomTache,
                nouvelEtat,
                employeNom
        );
    }










}
