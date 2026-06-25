package com.skillforge.bsc.bsc.b8.repository;

import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KpiReportRepository extends JpaRepository<KpiReport, UUID> {

    boolean existsByBscStrategy_IdAndDepartmentKpi_IdAndReportingPeriod(
            UUID bscStrategyId,
            UUID departmentKpiId,
            String reportingPeriod
    );

    List<KpiReport> findByBscStrategy_IdOrderByReportingPeriodDescCreatedAtDesc(UUID bscStrategyId);

    List<KpiReport> findByDepartmentKpi_IdOrderByReportingPeriodDescCreatedAtDesc(UUID departmentKpiId);
}
