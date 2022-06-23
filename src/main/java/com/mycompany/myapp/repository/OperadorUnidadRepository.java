package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OperadorUnidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OperadorUnidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperadorUnidadRepository extends JpaRepository<OperadorUnidad, Long> {}
