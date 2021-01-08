package com.chipviet.tinhocdanang.repository;

import com.chipviet.tinhocdanang.domain.GuaranteeProduction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GuaranteeProduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuaranteeProductionRepository extends JpaRepository<GuaranteeProduction, Long> {
}
