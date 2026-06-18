package com.skillforge.bsc.bsc.workflow.service;

import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.dto.response.BscStepStatusResponse;
import com.skillforge.bsc.bsc.workflow.mapper.BscStepStatusMapper;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BscStepStatusService {

    private final BscStepStatusRepository bscStepStatusRepository;
    private final BscStrategyService bscStrategyService;
    private final BscStepStatusMapper bscStepStatusMapper;

    @Transactional(readOnly = true)
    public List<BscStepStatusResponse> listByStrategy(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return bscStepStatusRepository.findByBscStrategyIdOrderByStepCodeAsc(strategyId)
                .stream()
                .map(bscStepStatusMapper::toResponse)
                .toList();
    }
}
