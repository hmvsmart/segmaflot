package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Itinerario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Itinerario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {}
