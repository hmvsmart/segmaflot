package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StatusCita;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusCita entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusCitaRepository extends JpaRepository<StatusCita, Long> {}
