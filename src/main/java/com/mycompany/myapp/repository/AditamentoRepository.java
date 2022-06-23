package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aditamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aditamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AditamentoRepository extends JpaRepository<Aditamento, Long> {}
