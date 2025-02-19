package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;

import java.util.List;

public interface IMissionServices {
    List<Mission> getAllMissions();
    Mission getMissionById(Long id);
    Mission addMission(Mission mission);
    Mission updateMission(Long id, Mission mission);
    void deleteMission(Long id);
}
