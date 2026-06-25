package com.skillforge.bsc.bsc.b8.repository;

import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionPlanRepository extends JpaRepository<ActionPlan, UUID> {

    List<ActionPlan> findByBscStrategy_IdOrderByStartDateAscCreatedAtAsc(UUID bscStrategyId);

    long countByBscStrategy_Id(UUID bscStrategyId);
}
