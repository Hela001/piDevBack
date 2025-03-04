package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;

import java.util.List;
import java.util.Map;

public interface IProjetServices {
    List<Projet> getAllProjets();
    public Projet getProjetById(Long id);
    Projet addProjet(Projet projet);
    Projet updateProjet(Long id, Projet projet);
    boolean deleteProjet(Long id);
    public Projet getProjetWithMissions(Long projetId);
    public List<Projet> searchProjets(String nom, Status status);
    public Map<String, Long> getProjetStatsByStatus();
    public String getWeatherForecastForProject(Long projetId);}
