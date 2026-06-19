package com.skillforge.bsc.bsc.b2.repository;

import com.skillforge.bsc.bsc.b2.entity.AnalysisItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnalysisItemRepository extends JpaRepository<AnalysisItem, UUID> {

    List<AnalysisItem> findByBscStrategy_IdOrderByModelTypeAscFactorCodeAscDisplayOrderAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
