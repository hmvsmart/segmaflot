package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterialServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MaterialServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialServicioRepository extends JpaRepository<MaterialServicio, Long> {}
