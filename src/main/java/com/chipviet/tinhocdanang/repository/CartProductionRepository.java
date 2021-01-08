package com.chipviet.tinhocdanang.repository;

import com.chipviet.tinhocdanang.domain.CartProduction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CartProduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartProductionRepository extends JpaRepository<CartProduction, Long> {
}
