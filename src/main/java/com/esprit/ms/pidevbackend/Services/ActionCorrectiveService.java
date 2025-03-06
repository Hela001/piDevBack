package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Repositorys.ActionCorrectiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActionCorrectiveService  implements IActionCorrectiveService {
    @Autowired
    ActionCorrectiveRepository actionCorrectiveRepository;
    @Override
    public ActionCorrective addActionCorrective(ActionCorrective actionCorrective) {
        return actionCorrectiveRepository.save(actionCorrective);
    }

    @Override
    public List<ActionCorrective> getAllActionsCorrectives() {
        return actionCorrectiveRepository.findAll();
    }

    @Override
    public ActionCorrective getActionCorrectiveById(Long id) {
        return actionCorrectiveRepository.findById(id).get();
    }

    @Override
    public ActionCorrective updateActionCorrective(ActionCorrective actionCorrective) {
        return actionCorrectiveRepository.save(actionCorrective);
    }

    @Override
    public void deleteActionCorrective(Long id) {
        actionCorrectiveRepository.deleteById(id);

    }
}
