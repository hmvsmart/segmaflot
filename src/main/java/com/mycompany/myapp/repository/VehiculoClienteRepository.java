package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VehiculoCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehiculoCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculoClienteRepository extends JpaRepository<VehiculoCliente, Long> {}
