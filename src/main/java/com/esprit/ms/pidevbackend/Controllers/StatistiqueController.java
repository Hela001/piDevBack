package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Services.StatistiqueService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/statistiques")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    // Endpoint pour récupérer les statistiques des types de non-conformité
    @GetMapping("/nonConformityTypes")
    public Map<String, Long> getNonConformityTypesStats() {
        return statistiqueService.getNonConformityTypesStats();
    }

    // Endpoint pour récupérer les statistiques des statuts de non-conformité
    @GetMapping("/statutNonConformity")
    public Map<String, Long> getStatutNonConformityStats() {
        return statistiqueService.getStatutNonConformityStats();
    }

    // Endpoint pour récupérer les statistiques des actions correctives
    @GetMapping("/correctiveActions")
    public Map<String, Long> getCorrectiveActionStats() {
        return statistiqueService.getCorrectiveActionStats();
    }

    // Endpoint pour récupérer les statistiques des inspections par projet
    @GetMapping("/inspectionsByProject")
    public Map<String, Long> getInspectionsByProjectStats() {
        return statistiqueService.getInspectionsByProjectStats();
    }
    @GetMapping("/status")
    public List<Map<String, Object>> getStatisticsByStatus() {
        return statistiqueService.getStatisticsByStatus();
    }

    @GetMapping("/type")
    public List<Map<String, Object>> getStatisticsByType() {
        return statistiqueService.getStatisticsByType();
    }
}
