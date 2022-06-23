package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonaMoral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonaMoral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaMoralRepository extends JpaRepository<PersonaMoral, Long> {}
