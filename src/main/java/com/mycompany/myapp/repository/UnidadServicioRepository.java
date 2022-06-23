package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UnidadServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UnidadServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadServicioRepository extends JpaRepository<UnidadServicio, Long> {}
