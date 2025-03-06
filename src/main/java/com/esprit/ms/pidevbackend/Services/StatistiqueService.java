package com.esprit.ms.pidevbackend.Services;



import com.esprit.ms.pidevbackend.Repositorys.ActionCorrectiveRepository;
import com.esprit.ms.pidevbackend.Repositorys.InspectionRepo;
import com.esprit.ms.pidevbackend.Repositorys.NonConformiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    @Autowired
    private NonConformiteRepository nonConfirmityRepository;

    @Autowired
    private ActionCorrectiveRepository actionCorrectiveRepository;

    @Autowired
    private InspectionRepo inspectionRepository;

    // Récupérer les statistiques par type de non-conformité
    public Map<String, Long> getNonConformityTypesStats() {
        List<Object[]> result = nonConfirmityRepository.findNonConformityTypesStats();
        return result.stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(), // type
                        obj -> (Long) obj[1])); // count
    }

    // Récupérer les statistiques par statut de non-conformité
    public Map<String, Long> getStatutNonConformityStats() {
        List<Object[]> result = nonConfirmityRepository.findStatutNonConformityStats();
        return result.stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(), // statut
                        obj -> (Long) obj[1])); // count
    }

    // Récupérer les statistiques des actions correctives
    public Map<String, Long> getCorrectiveActionStats() {
        List<Object[]> result = actionCorrectiveRepository.findCorrectiveActionStats();
        return result.stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(), // statut
                        obj -> (Long) obj[1])); // count
    }

    // Récupérer les statistiques des inspections par projet
    public Map<String, Long> getInspectionsByProjectStats() {
        List<Object[]> result = inspectionRepository.findInspectionsByProjectStats();
        return result.stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(), // projet
                        obj -> (Long) obj[1])); // count
    }
    public List<Map<String, Object>> getStatisticsByStatus() {
        return inspectionRepository.countInspectionsByStatus().stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label", result[0]);  // Status de l'inspection
            map.put("count", result[1]);  // Nombre d'inspections
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getStatisticsByType() {
        return inspectionRepository.countInspectionsByType().stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label", result[0]);  // Type d'inspection
            map.put("count", result[1]);  // Nombre d'inspections
            return map;
        }).collect(Collectors.toList());
    }
}
