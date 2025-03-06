package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Entities.ActionCorrective;
import com.esprit.ms.pidevbackend.Services.ActionCorrectiveService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/actioncorrective")
@CrossOrigin(origins = "http://localhost:4200")
public class ActionCorrectiveController
{
    @Autowired
    ActionCorrectiveService actionCorrectiveService;

    @GetMapping("getActionCorrective")
    public List<ActionCorrective> getAll() {

        return actionCorrectiveService.getAllActionsCorrectives();
    }

    @PostMapping("addActionCorrective")
    public ActionCorrective addActionCorrective(@RequestBody ActionCorrective actionCorrective) {

        return actionCorrectiveService.addActionCorrective(actionCorrective);
    }
    @DeleteMapping("delete/{id}")
    public void deleteActionCorrective(@PathVariable Long id) {
      actionCorrectiveService.deleteActionCorrective(id);
    }

    @PutMapping("updateAc")
    public ActionCorrective updateActionCorrective(@RequestBody ActionCorrective actionCorrective)
    {

        return actionCorrectiveService.updateActionCorrective(actionCorrective);
    }

    @GetMapping("getActionCorrectiveById/{id}")
    public ActionCorrective getActionCorrectiveById(@PathVariable Long id) {

        return actionCorrectiveService.getActionCorrectiveById(id);
    }


}
