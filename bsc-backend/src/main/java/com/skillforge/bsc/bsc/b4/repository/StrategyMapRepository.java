package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.StrategyMap;
import com.skillforge.bsc.common.enums.StrategyMapType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StrategyMapRepository extends JpaRepository<StrategyMap, UUID> {

    boolean existsByBscStrategy_IdAndSelectedStrategy_IdAndMapType(
            UUID bscStrategyId,
            UUID selectedStrategyId,
            StrategyMapType mapType
    );

    Optional<StrategyMap> findByBscStrategy_IdAndSelectedStrategy_IdAndMapType(
            UUID bscStrategyId,
            UUID selectedStrategyId,
            StrategyMapType mapType
    );

    List<StrategyMap> findByBscStrategy_IdAndMapTypeOrderByCreatedAtAsc(UUID bscStrategyId, StrategyMapType mapType);
}
