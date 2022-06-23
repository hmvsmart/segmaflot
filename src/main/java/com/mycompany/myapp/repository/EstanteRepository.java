package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Estante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Estante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstanteRepository extends JpaRepository<Estante, Long> {}
