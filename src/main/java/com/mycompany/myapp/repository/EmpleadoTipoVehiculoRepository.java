package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EmpleadoTipoVehiculo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmpleadoTipoVehiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoTipoVehiculoRepository extends JpaRepository<EmpleadoTipoVehiculo, Long> {}
