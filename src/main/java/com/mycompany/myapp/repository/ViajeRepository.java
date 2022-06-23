package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Viaje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Viaje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {}
