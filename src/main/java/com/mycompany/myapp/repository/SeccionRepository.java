package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Seccion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Seccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {}
