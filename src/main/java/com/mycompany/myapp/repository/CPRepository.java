package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPRepository extends JpaRepository<CP, Long> {}
