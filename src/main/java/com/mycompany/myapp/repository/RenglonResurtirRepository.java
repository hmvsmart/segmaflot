package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RenglonResurtir;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RenglonResurtir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RenglonResurtirRepository extends JpaRepository<RenglonResurtir, Long> {}
