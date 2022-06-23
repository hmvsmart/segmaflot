package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RenglonVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RenglonVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RenglonVentaRepository extends JpaRepository<RenglonVenta, Long> {}
