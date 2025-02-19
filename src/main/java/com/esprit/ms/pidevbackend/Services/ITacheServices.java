package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Tache;

import java.util.List;

public interface ITacheServices {
    List<Tache> getAllTaches();
    Tache getTacheById(Long id);
    Tache addTache(Tache tache);
    Tache updateTache(Long id, Tache tache);
    void deleteTache(Long id);
}
