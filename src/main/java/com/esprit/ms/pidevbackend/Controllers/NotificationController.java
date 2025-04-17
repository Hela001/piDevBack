package com.esprit.ms.pidevbackend.Controllers;

import com.esprit.ms.pidevbackend.Entities.Notification;
import com.esprit.ms.pidevbackend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{destinataire}")
    public List<Notification> getNonLues(@PathVariable String destinataire) {
        return notificationService.getNotificationsNonLues(destinataire);
    }

    @PutMapping("/{id}/lue")
    public void marquerCommeLue(@PathVariable Long id) {
        notificationService.marquerCommeLue(id);
    }
}
