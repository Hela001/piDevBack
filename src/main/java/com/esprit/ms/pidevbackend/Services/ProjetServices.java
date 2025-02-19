package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Repositories.ProjetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetServices implements IProjetServices {
    @Autowired
    private ProjetRepository projetRepository;

    @Override
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    @Override
    public Projet getProjetById(Long id) {
        return projetRepository.findById(id).get();
    }

    @Override
    public Projet addProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    @Override
    public Projet updateProjet(Long id, Projet projet) {

            return projetRepository.save(projet);


    }

    @Override
    public void deleteProjet(Long id) {
        projetRepository.deleteById(id);
    }
}
