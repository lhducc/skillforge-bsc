package com.skillforge.bsc.bsc.b1.repository;

import com.skillforge.bsc.bsc.b1.entity.AssessmentTextItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssessmentTextItemRepository extends JpaRepository<AssessmentTextItem, UUID> {

    List<AssessmentTextItem> findByBscStrategy_IdOrderByCategoryAscDisplayOrderAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
