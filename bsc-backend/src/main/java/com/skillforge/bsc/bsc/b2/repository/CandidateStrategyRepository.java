package com.skillforge.bsc.bsc.b2.repository;

import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.common.enums.CandidateStrategyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateStrategyRepository extends JpaRepository<CandidateStrategy, UUID> {

    long countByBscStrategy_IdAndStatus(UUID bscStrategyId, CandidateStrategyStatus status);

    List<CandidateStrategy> findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
            UUID bscStrategyId,
            CandidateStrategyStatus status
    );
}
