package com.esprit.ms.pidevbackend.Services;

import com.esprit.ms.pidevbackend.Entities.Inspection;

import java.util.List;

public interface IinspectionService {
    List<Inspection> getAllInspection();
    Inspection addInspection(Inspection inspection );
    void deleteInspection(Long id);
    public Inspection updateInspection( Inspection inspection);
    Inspection getInspectionById(Long id);

}
