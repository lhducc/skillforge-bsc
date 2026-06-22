package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.StrategicObjective;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StrategicObjectiveRepository extends JpaRepository<StrategicObjective, UUID> {

    long countBySelectedStrategy_IdAndStatus(UUID selectedStrategyId, StrategicObjectiveStatus status);

    List<StrategicObjective> findByStrategyMap_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
            UUID strategyMapId,
            StrategicObjectiveStatus status
    );

    List<StrategicObjective> findBySelectedStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
            UUID selectedStrategyId,
            StrategicObjectiveStatus status
    );

    List<StrategicObjective> findByStrategyMap_IdInAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
            List<UUID> strategyMapIds,
            StrategicObjectiveStatus status
    );
}
