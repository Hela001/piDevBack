package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Status;

import java.util.List;

public interface IMissionServices {
    List<Mission> getAllMissions();
    Mission getMissionById(Long id);
    public Mission createMission(Mission mission, Long projetId);
    Mission updateMission(Long id, Mission mission);
    boolean deleteMission(Long id);
    List<Mission> findByProjetId(Long projetId);
    public List<Mission> searchMissions(Long projetId, Status etatMission, String searchText);
}
