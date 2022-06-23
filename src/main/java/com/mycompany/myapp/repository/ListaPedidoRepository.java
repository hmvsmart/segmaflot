package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ListaPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListaPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListaPedidoRepository extends JpaRepository<ListaPedido, Long> {}
