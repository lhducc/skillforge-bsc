package com.skillforge.bsc.bsc.b1.repository;

import com.skillforge.bsc.bsc.b1.entity.AssessmentFinancial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssessmentFinancialRepository extends JpaRepository<AssessmentFinancial, UUID> {

    List<AssessmentFinancial> findByBscStrategy_IdOrderByYearAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
