package com.skillforge.bsc.bsc.b7.repository;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.common.enums.MeasurementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KpiMeasurementRepository extends JpaRepository<KpiMeasurement, UUID> {

    Optional<KpiMeasurement> findByBscStrategy_IdAndDepartmentKpi_Id(UUID bscStrategyId, UUID departmentKpiId);

    List<KpiMeasurement> findByBscStrategy_Id(UUID bscStrategyId);

    long countByBscStrategy_IdAndStatus(UUID bscStrategyId, MeasurementStatus status);
}
