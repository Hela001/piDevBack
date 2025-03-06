package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.ActionCorrective;

import java.util.List;

public interface IActionCorrectiveService {


    ActionCorrective addActionCorrective(ActionCorrective actionCorrective);
    List<ActionCorrective> getAllActionsCorrectives();
    ActionCorrective getActionCorrectiveById(Long id);

      ActionCorrective updateActionCorrective( ActionCorrective actionCorrective);
 void deleteActionCorrective(Long id);
}