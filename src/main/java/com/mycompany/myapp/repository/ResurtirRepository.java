package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Resurtir;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Resurtir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResurtirRepository extends JpaRepository<Resurtir, Long> {}
