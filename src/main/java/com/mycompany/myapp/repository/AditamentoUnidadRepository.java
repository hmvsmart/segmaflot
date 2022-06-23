package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AditamentoUnidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AditamentoUnidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AditamentoUnidadRepository extends JpaRepository<AditamentoUnidad, Long> {}
