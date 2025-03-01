package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Repositories.MissionRepository;
import com.esprit.ms.pidevbackend.Repositories.ProjetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MissionServices implements IMissionServices {

    @Autowired
    private MissionRepository missionRepository;
    private ProjetRepository projetRepository;

    @Override
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @Override
    public Mission getMissionById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission non trouvée avec l'ID : " + id));
    }

    public Mission createMission(Mission mission, Long projetId) {
        // Retrieve the project from the database using the provided projetId
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID " + projetId));

        // Associate the project with the mission
        mission.setProjet(projet);

        // Save and return the mission
        return missionRepository.save(mission);
    }


    @Override
    public Mission updateMission(Long id, Mission mission) {
        Optional<Mission> existingMission = missionRepository.findById(id);
        if (existingMission.isPresent()) {
            Mission updatedMission = existingMission.get();
            updatedMission.setNom(mission.getNom());
            updatedMission.setDescription(mission.getDescription());
            updatedMission.setEtatMission(mission.getEtatMission());
            return missionRepository.save(updatedMission);
        }
        return null;
    }


    @Override
    public boolean deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new EntityNotFoundException("Mission non trouvée avec l'ID : " + id);
        }
        missionRepository.deleteById(id);
        return true;  // Retourne true si la mission a été supprimée
    }

    @Override
    public List<Mission> findByProjetId(Long projetId) {
        return missionRepository.findByProjet_IdProjet(projetId);  // Appel de la méthode du repository
    }
    public List<Mission> searchMissions(Long projetId, Status etatMission, String searchText) {
        return missionRepository.searchMissions(projetId, etatMission, searchText);
    }
}
