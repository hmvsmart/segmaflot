package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ListaServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListaServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListaServicioRepository extends JpaRepository<ListaServicio, Long> {}
