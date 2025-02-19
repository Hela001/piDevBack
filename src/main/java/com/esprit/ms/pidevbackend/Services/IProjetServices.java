package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Projet;

import java.util.List;

public interface IProjetServices {
    List<Projet> getAllProjets();
    public Projet getProjetById(Long id);
    Projet addProjet(Projet projet);
    Projet updateProjet(Long id, Projet projet);
    void deleteProjet(Long id);
}
