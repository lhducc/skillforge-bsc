package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.ObjectiveLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObjectiveLinkRepository extends JpaRepository<ObjectiveLink, UUID> {

    boolean existsByStrategyMap_IdAndSourceObjective_IdAndTargetObjective_Id(
            UUID strategyMapId,
            UUID sourceObjectiveId,
            UUID targetObjectiveId
    );

    List<ObjectiveLink> findByStrategyMap_IdOrderByDisplayOrderAscCreatedAtAsc(UUID strategyMapId);

    List<ObjectiveLink> findByStrategyMap_IdInOrderByDisplayOrderAscCreatedAtAsc(List<UUID> strategyMapIds);

    List<ObjectiveLink> findBySourceObjective_IdOrTargetObjective_Id(UUID sourceObjectiveId, UUID targetObjectiveId);
}
