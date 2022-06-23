package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DiaHorario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DiaHorario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaHorarioRepository extends JpaRepository<DiaHorario, Long> {}
