package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Mission;

import java.util.List;

public interface IMissionServices {
    List<Mission> getAllMissions();
    Mission getMissionById(Long id);
    public Mission createMission(Mission mission, Long projetId);
    Mission updateMission(Long id, Mission mission);
    boolean deleteMission(Long id);
    List<Mission> findByProjetId(Long projetId);}
