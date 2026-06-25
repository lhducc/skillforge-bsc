package com.skillforge.bsc.bsc.b6.repository;

import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KpiWeightRepository extends JpaRepository<KpiWeight, UUID> {

    List<KpiWeight> findByBscStrategy_Id(UUID bscStrategyId);

    List<KpiWeight> findByDepartmentKpi_Id(UUID departmentKpiId);

    Optional<KpiWeight> findByBscStrategy_IdAndDepartmentKpi_Id(UUID bscStrategyId, UUID departmentKpiId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
