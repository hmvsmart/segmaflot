package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UbicacionInventario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UbicacionInventario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UbicacionInventarioRepository extends JpaRepository<UbicacionInventario, Long> {}
