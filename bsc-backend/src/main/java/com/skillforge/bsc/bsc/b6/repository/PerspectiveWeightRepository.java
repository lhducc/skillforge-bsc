package com.skillforge.bsc.bsc.b6.repository;

import com.skillforge.bsc.bsc.b6.entity.PerspectiveWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerspectiveWeightRepository extends JpaRepository<PerspectiveWeight, UUID> {

    List<PerspectiveWeight> findByBscStrategy_Id(UUID bscStrategyId);

    List<PerspectiveWeight> findByBscStrategy_IdOrderByPerspectiveCodeAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
