package com.skillforge.bsc.bsc.b4.repository;

import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FinalObjectiveSourceRepository extends JpaRepository<FinalObjectiveSource, UUID> {

    List<FinalObjectiveSource> findByFinalObjective_IdIn(List<UUID> finalObjectiveIds);

    void deleteByFinalObjective_BscStrategy_Id(UUID bscStrategyId);
}
