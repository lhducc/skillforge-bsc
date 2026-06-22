package com.skillforge.bsc.bsc.b5.repository;

import com.skillforge.bsc.bsc.b5.entity.DepartmentParticipation;
import com.skillforge.bsc.common.enums.DepartmentParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentParticipationRepository extends JpaRepository<DepartmentParticipation, UUID> {

    Optional<DepartmentParticipation> findByBscStrategy_IdAndFinalStrategicObjective_IdAndDepartment_Id(
            UUID bscStrategyId,
            UUID finalStrategicObjectiveId,
            UUID departmentId
    );

    List<DepartmentParticipation> findByBscStrategy_IdAndStatusOrderByCreatedAtAsc(
            UUID bscStrategyId,
            DepartmentParticipationStatus status
    );

    List<DepartmentParticipation> findByBscStrategy_IdAndDepartment_IdAndStatusOrderByCreatedAtAsc(
            UUID bscStrategyId,
            UUID departmentId,
            DepartmentParticipationStatus status
    );

    long countByFinalStrategicObjective_IdAndStatus(UUID finalStrategicObjectiveId, DepartmentParticipationStatus status);
}
