package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonaFisica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonaFisica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long> {}
