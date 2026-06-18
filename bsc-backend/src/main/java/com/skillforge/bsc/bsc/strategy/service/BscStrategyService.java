package com.skillforge.bsc.bsc.strategy.service;

import com.skillforge.bsc.bsc.strategy.dto.request.CreateBscStrategyRequest;
import com.skillforge.bsc.bsc.strategy.dto.response.BscStrategyResponse;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.mapper.BscStrategyMapper;
import com.skillforge.bsc.bsc.strategy.repository.BscStrategyRepository;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BscStrategyService {

    private final BscStrategyRepository bscStrategyRepository;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final CompanyService companyService;
    private final BscStrategyMapper bscStrategyMapper;

    @Transactional
    public BscStrategyResponse create(UUID companyId, CreateBscStrategyRequest request) {
        Company company = companyService.getCompany(companyId);

        BscStrategy strategy = new BscStrategy();
        strategy.setCompany(company);
        strategy.setName(normalizeRequired(request.getName(), "name"));
        strategy.setDescription(normalize(request.getDescription()));
        strategy.setYear(validateYear(request.getYear()));
        strategy.setStatus(BscStrategyStatus.DRAFT);

        BscStrategy savedStrategy = bscStrategyRepository.save(strategy);
        bscStepStatusRepository.saveAll(createInitialStepStatuses(savedStrategy));

        return bscStrategyMapper.toResponse(savedStrategy);
    }

    @Transactional(readOnly = true)
    public BscStrategyResponse getById(UUID strategyId) {
        return bscStrategyMapper.toResponse(getStrategy(strategyId));
    }

    @Transactional(readOnly = true)
    public List<BscStrategyResponse> listByCompany(UUID companyId) {
        companyService.getCompany(companyId);
        return bscStrategyRepository.findByCompanyId(companyId)
                .stream()
                .map(bscStrategyMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BscStrategy getStrategy(UUID strategyId) {
        return bscStrategyRepository.findById(strategyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BSC_STRATEGY_NOT_FOUND));
    }

    private List<BscStepStatus> createInitialStepStatuses(BscStrategy strategy) {
        return Arrays.stream(BscStepCode.values())
                .map(stepCode -> {
                    BscStepStatus stepStatus = new BscStepStatus();
                    stepStatus.setBscStrategy(strategy);
                    stepStatus.setStepCode(stepCode);
                    stepStatus.setStatus(initialStatusFor(stepCode));
                    return stepStatus;
                })
                .toList();
    }

    private BscStepStatusValue initialStatusFor(BscStepCode stepCode) {
        if (stepCode == BscStepCode.B1_ASSESSMENT) {
            return BscStepStatusValue.NOT_STARTED;
        }
        return BscStepStatusValue.LOCKED;
    }

    private Integer validateYear(Integer year) {
        if (year == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "year is required");
        }
        return year;
    }

    private String normalizeRequired(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, fieldName + " is required");
        }
        return normalized;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
