package com.skillforge.bsc.bsc.workflow.repository;

import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.common.enums.BscStepCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BscStepStatusRepository extends JpaRepository<BscStepStatus, UUID> {

    List<BscStepStatus> findByBscStrategyIdOrderByStepCodeAsc(UUID bscStrategyId);

    Optional<BscStepStatus> findByBscStrategyIdAndStepCode(UUID bscStrategyId, BscStepCode stepCode);
}
