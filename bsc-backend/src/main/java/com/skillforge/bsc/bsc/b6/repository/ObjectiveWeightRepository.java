package com.skillforge.bsc.bsc.b6.repository;

import com.skillforge.bsc.bsc.b6.entity.ObjectiveWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObjectiveWeightRepository extends JpaRepository<ObjectiveWeight, UUID> {

    List<ObjectiveWeight> findByBscStrategy_Id(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
