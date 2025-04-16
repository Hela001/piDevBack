package com.esprit.ms.pidevbackend.DTO;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Entities.Tache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class RapportDTO {
    private String projetNom;
    private List<Mission> missions;
    private List<Tache> taches;
}
