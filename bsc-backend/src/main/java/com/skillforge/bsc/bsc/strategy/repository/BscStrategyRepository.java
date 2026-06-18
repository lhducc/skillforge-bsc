package com.skillforge.bsc.bsc.strategy.repository;

import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BscStrategyRepository extends JpaRepository<BscStrategy, UUID> {

    List<BscStrategy> findByCompanyId(UUID companyId);
}
