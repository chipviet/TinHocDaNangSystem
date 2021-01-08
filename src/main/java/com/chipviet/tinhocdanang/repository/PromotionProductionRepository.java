package com.chipviet.tinhocdanang.repository;

import com.chipviet.tinhocdanang.domain.PromotionProduction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PromotionProduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PromotionProductionRepository extends JpaRepository<PromotionProduction, Long> {
}
