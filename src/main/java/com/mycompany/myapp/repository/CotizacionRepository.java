package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cotizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cotizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {}
