package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Nivel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nivel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {}
