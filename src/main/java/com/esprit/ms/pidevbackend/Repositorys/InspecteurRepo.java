package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.Inspecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspecteurRepo extends JpaRepository<Inspecteur, Long> {

}
