package com.esprit.ms.pidevbackend.Repositorys;

import com.esprit.ms.pidevbackend.Entities.RapportQualite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository extends JpaRepository<RapportQualite,Long> {
}
