package com.skillforge.bsc.bsc.b8.repository;

import com.skillforge.bsc.bsc.b8.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByBscStrategy_IdOrderByDueDateAscCreatedAtAsc(UUID bscStrategyId);

    List<Task> findByActionPlan_IdOrderByDueDateAscCreatedAtAsc(UUID actionPlanId);

    long countByActionPlan_Id(UUID actionPlanId);

    long countByBscStrategy_Id(UUID bscStrategyId);
}
