package com.skillforge.bsc.bsc.b3.repository;

import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SelectedStrategyRepository extends JpaRepository<SelectedStrategy, UUID> {

    List<SelectedStrategy> findByBscStrategy_IdOrderByPriorityOrderAsc(UUID bscStrategyId);

    void deleteByBscStrategy_Id(UUID bscStrategyId);
}
