package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Mission;
import com.esprit.ms.pidevbackend.Services.IMissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    @Autowired
    private IMissionServices iMissionServices;

    @GetMapping
    public List<Mission> getAllMissions() {
        return iMissionServices.getAllMissions();
    }

    @GetMapping("/{id}")
    public Mission getMissionById(@PathVariable Long id) {
        return iMissionServices.getMissionById(id);
    }

    @PostMapping
    public Mission addMission(@RequestBody Mission mission) {
        return iMissionServices.addMission(mission);
    }

    @PutMapping("/{id}")
    public Mission updateMission(@PathVariable Long id, @RequestBody Mission mission) {
        return iMissionServices.updateMission(id, mission);
    }

    @DeleteMapping("/{id}")
    public void deleteMission(@PathVariable Long id) {
        iMissionServices.deleteMission(id);

    }
}

