package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FinalStrategicObjectiveRepository extends JpaRepository<FinalStrategicObjective, UUID> {

    List<FinalStrategicObjective> findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
            UUID bscStrategyId,
            StrategicObjectiveStatus status
    );

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
