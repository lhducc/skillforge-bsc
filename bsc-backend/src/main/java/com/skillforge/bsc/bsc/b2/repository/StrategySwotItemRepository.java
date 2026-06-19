package com.skillforge.bsc.bsc.b2.repository;

import com.skillforge.bsc.bsc.b2.entity.StrategySwotItem;
import com.skillforge.bsc.common.enums.CandidateStrategyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StrategySwotItemRepository extends JpaRepository<StrategySwotItem, UUID> {

    List<StrategySwotItem> findByCandidateStrategy_Id(UUID candidateStrategyId);

    List<StrategySwotItem> findByCandidateStrategy_IdIn(List<UUID> candidateStrategyIds);

    List<StrategySwotItem> findBySwotItem_IdInAndCandidateStrategy_Status(List<UUID> swotItemIds, CandidateStrategyStatus status);

    Optional<StrategySwotItem> findBySwotItem_IdAndCandidateStrategy_Status(UUID swotItemId, CandidateStrategyStatus status);

    void deleteByCandidateStrategy_Id(UUID candidateStrategyId);
}
