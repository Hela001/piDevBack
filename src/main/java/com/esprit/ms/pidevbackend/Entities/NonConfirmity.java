package com.esprit.ms.pidevbackend.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)

public class NonConfirmity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idNC;


    LocalDate dateDetection;


    String description;

    @Enumerated(EnumType.STRING)
    Type typeNonConfirm;

    @Enumerated(EnumType.STRING)
    StatutNonConfirmity statutNonConfirm;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "NonComform_id")
    List<ActionCorrective> actionCorrective;

}


