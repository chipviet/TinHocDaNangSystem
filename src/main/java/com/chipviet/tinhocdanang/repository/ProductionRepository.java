package com.chipviet.tinhocdanang.repository;

import com.chipviet.tinhocdanang.domain.Production;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Production entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

    @Query(value = "SELECT * FROM tinhocdanang.production ORDER BY id DESC LIMIT 1;",nativeQuery = true)
    Production getLatestProduction();
}
