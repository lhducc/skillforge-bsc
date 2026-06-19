package com.skillforge.bsc.bsc.b2.repository;

import com.skillforge.bsc.bsc.b2.entity.SwotItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SwotItemRepository extends JpaRepository<SwotItem, UUID> {

    List<SwotItem> findByBscStrategy_IdOrderBySwotTypeAscCreatedAtAsc(UUID bscStrategyId);

    Optional<SwotItem> findByBscStrategy_IdAndSourceAnalysisItem_Id(UUID bscStrategyId, UUID sourceAnalysisItemId);

    List<SwotItem> findBySourceAnalysisItem_IdIn(List<UUID> sourceAnalysisItemIds);
}
