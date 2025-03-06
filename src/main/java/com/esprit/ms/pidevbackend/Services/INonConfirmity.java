package com.esprit.ms.pidevbackend.Services;


import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Entities.NonConfirmity;

import java.util.List;

public interface INonConfirmity {
    List<NonConfirmity> getAllNonConfirmity();
    NonConfirmity addNonConfirmity(NonConfirmity nonConfirmity );
    void deleteNonConfirmity(Long id);
    NonConfirmity updateNonConfirmity(NonConfirmity nonConfirmity );
    NonConfirmity getNonConfirmityById(Long id);

    public NonConfirmity addActionCorrective(Long nonConfirmityId, ActionCorrective actionCorrective);
}
