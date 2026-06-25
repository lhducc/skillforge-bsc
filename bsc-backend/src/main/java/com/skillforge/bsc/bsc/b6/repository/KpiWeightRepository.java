package com.skillforge.bsc.bsc.b6.repository;

import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KpiWeightRepository extends JpaRepository<KpiWeight, UUID> {

    List<KpiWeight> findByBscStrategy_Id(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
