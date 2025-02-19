package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Repositories.MissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MissionServices implements IMissionServices {
    @Autowired

    private MissionRepository missionRepository;

    @Override
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @Override
    public Mission getMissionById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission non trouvée avec l'ID : " + id));
    }

    @Override
    public Mission addMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public Mission updateMission(Long id, Mission mission) {

        return missionRepository.save(mission);
    }

    @Override
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new EntityNotFoundException("Mission non trouvée avec l'ID : " + id);
        }
        missionRepository.deleteById(id);
    }
}
