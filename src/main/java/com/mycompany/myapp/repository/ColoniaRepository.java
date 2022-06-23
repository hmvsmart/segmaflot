package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Colonia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Colonia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {}
