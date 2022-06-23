package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Unidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Unidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {}
