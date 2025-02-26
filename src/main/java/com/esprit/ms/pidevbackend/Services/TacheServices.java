package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Repositories.MissionRepository;
import com.esprit.ms.pidevbackend.Repositories.TacheRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TacheServices implements ITacheServices {
    @Autowired
    private TacheRepository tacheRepository;
    MissionRepository missionRepository;


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
    public Tache addTache(Tache tache, long idM) {
        // Récupérer la mission à partir de l'ID idM
        Mission mission = missionRepository.findById(idM).orElse(null);
        if(mission != null) {
            // Associer la tâche à la mission
            tache.setMission(mission);
        } else {
            // Optionnel : gérer le cas où la mission n'est pas trouvée
            throw new RuntimeException("Mission not found with id: " + idM);
        }
        // Sauvegarder et retourner la tâche créée
        return tacheRepository.save(tache);
    }    @Override
    public Tache updateTache(Long id, Tache tache) {

        return tacheRepository.save(tache);
    }

    @Override
    public void deleteTache(Long id) {
        if (!tacheRepository.existsById(id)) {
            throw new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id);
        }
        tacheRepository.deleteById(id);
    }
}

