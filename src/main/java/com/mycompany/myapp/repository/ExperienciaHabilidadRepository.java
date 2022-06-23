package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExperienciaHabilidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExperienciaHabilidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienciaHabilidadRepository extends JpaRepository<ExperienciaHabilidad, Long> {}
