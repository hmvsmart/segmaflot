package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VentaPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VentaPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaPedidoRepository extends JpaRepository<VentaPedido, Long> {}
