package com.esprit.ms.pidevbackend.Repositories;

import com.esprit.ms.pidevbackend.Entities.Fiche_de_paie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FicheDePaieRepo  extends JpaRepository<Fiche_de_paie,Long> {
    List<Fiche_de_paie> findAllByOrderByIdBulletinPaieDesc();


}
