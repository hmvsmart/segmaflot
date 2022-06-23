package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Poliza;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Poliza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {}
