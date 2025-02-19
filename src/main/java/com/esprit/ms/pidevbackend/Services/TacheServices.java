package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Tache;
import com.esprit.ms.pidevbackend.Repositories.TacheRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TacheServices implements ITacheServices {
    @Autowired
    private TacheRepository tacheRepository;


    @Override
    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache getTacheById(Long id) {
        return tacheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id));
    }

    @Override
    public Tache addTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public Tache updateTache(Long id, Tache tache) {

        return tacheRepository.save(tache);
    }

    @Override
    public void deleteTache(Long id) {
        if (!tacheRepository.existsById(id)) {
            throw new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id);
        }
        tacheRepository.deleteById(id);
    }
}

