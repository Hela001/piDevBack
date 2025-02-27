package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Tache;

import java.util.List;

public interface ITacheServices {
    List<Tache> getAllTaches();
    Tache getTacheById(Long id);
    Tache addTache(long id,Tache tache) ;
    Tache updateTache(Long id, Tache tache);
    boolean deleteTache(long id);
    public List<Tache> getTasksByMission(long missionId);
}
