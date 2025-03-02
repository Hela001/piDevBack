package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Projet;
import com.esprit.ms.pidevbackend.Entities.Status;
import com.esprit.ms.pidevbackend.Repositories.ProjetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public boolean deleteProjet(Long id) {
        projetRepository.deleteById(id);
        return true;
    }
    public Projet getProjetWithMissions(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID " + projetId));

        // Accéder aux missions, ce qui déclenchera le chargement des missions si LAZY est utilisé
        projet.getMissions().size();  // Cela force le chargement des missions (si LAZY)

        return projet;
    }
    public List<Projet> searchProjets(String nom, Status status) {
        return projetRepository.searchProjet(nom, status);
    }
    public Map<String, Long> getProjetStatsByStatus() {
        List<Projet> projets = projetRepository.findAll();

        Map<String, Long> stats = projets.stream()
                .collect(Collectors.groupingBy(projet -> projet.getStatus().name(), Collectors.counting()));

        return stats;
    }


}
