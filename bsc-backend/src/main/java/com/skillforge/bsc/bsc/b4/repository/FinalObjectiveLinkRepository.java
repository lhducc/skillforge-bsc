package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FinalObjectiveLinkRepository extends JpaRepository<FinalObjectiveLink, UUID> {

    boolean existsByBscStrategy_IdAndSourceFinalObjective_IdAndTargetFinalObjective_Id(
            UUID bscStrategyId,
            UUID sourceFinalObjectiveId,
            UUID targetFinalObjectiveId
    );

    List<FinalObjectiveLink> findByBscStrategy_IdOrderByDisplayOrderAscCreatedAtAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
