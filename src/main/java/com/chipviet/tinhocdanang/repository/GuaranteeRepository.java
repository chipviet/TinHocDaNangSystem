package com.chipviet.tinhocdanang.repository;

import com.chipviet.tinhocdanang.domain.Guarantee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Guarantee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, Long> {
}
