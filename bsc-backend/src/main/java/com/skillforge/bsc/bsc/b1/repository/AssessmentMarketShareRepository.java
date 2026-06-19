package com.skillforge.bsc.bsc.b1.repository;

import com.skillforge.bsc.bsc.b1.entity.AssessmentMarketShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssessmentMarketShareRepository extends JpaRepository<AssessmentMarketShare, UUID> {

    List<AssessmentMarketShare> findByBscStrategy_IdOrderByPeriodTypeAscDisplayOrderAscCompanyNameAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
