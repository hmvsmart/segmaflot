package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UnidadViaje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UnidadViaje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadViajeRepository extends JpaRepository<UnidadViaje, Long> {}
