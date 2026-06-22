package com.skillforge.bsc.bsc.b5.repository;

import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentKpiRepository extends JpaRepository<DepartmentKpi, UUID> {

    long countByDepartmentParticipation_IdAndStatus(UUID departmentParticipationId, DepartmentKpiStatus status);

    long countByFinalStrategicObjective_IdAndStatus(UUID finalStrategicObjectiveId, DepartmentKpiStatus status);

    List<DepartmentKpi> findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
            UUID bscStrategyId,
            DepartmentKpiStatus status
    );

    List<DepartmentKpi> findByBscStrategy_IdAndDepartment_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
            UUID bscStrategyId,
            UUID departmentId,
            DepartmentKpiStatus status
    );

    List<DepartmentKpi> findByBscStrategy_IdAndFinalStrategicObjective_IdAndDepartment_IdAndStatus(
            UUID bscStrategyId,
            UUID finalStrategicObjectiveId,
            UUID departmentId,
            DepartmentKpiStatus status
    );
}
