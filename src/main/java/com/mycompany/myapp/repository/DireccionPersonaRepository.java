package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DireccionPersona;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DireccionPersona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DireccionPersonaRepository extends JpaRepository<DireccionPersona, Long> {}
