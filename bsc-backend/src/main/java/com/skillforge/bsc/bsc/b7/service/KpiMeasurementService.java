package com.skillforge.bsc.bsc.b7.service;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import com.skillforge.bsc.bsc.b6.repository.KpiWeightRepository;
import com.skillforge.bsc.bsc.b7.dto.request.UpsertKpiMeasurementRequest;
import com.skillforge.bsc.bsc.b7.dto.response.KpiMeasurementResponse;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementObjectiveResponse;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementPerspectiveResponse;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementTreeResponse;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b7.mapper.KpiMeasurementMapper;
import com.skillforge.bsc.bsc.b7.repository.KpiMeasurementRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import com.skillforge.bsc.common.enums.MeasurementStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KpiMeasurementService {

    private static final BigDecimal DEFAULT_GREEN_THRESHOLD = new BigDecimal("90.000");
    private static final BigDecimal DEFAULT_YELLOW_THRESHOLD = new BigDecimal("70.000");
    private static final BigDecimal DEFAULT_RED_THRESHOLD = new BigDecimal("0.000");

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final KpiWeightRepository kpiWeightRepository;
    private final KpiMeasurementRepository kpiMeasurementRepository;
    private final EmployeeRepository employeeRepository;
    private final KpiMeasurementMapper kpiMeasurementMapper;

    @Transactional
    public MeasurementTreeResponse upsertMeasurement(UUID departmentKpiId, UpsertKpiMeasurementRequest request) {
        KpiWeight kpiWeight = getKpiWeightForMeasurement(departmentKpiId);
        BscStrategy strategy = getEditableStrategy(kpiWeight.getBscStrategy().getId());
        ensureB6Completed(strategy.getId());
        ensureB7StepEditable(strategy.getId());

        validateActiveWeightedKpi(kpiWeight);
        validateMeasurementRequest(request);

        Employee reportOwner = resolveReportOwner(strategy, request.getReportOwnerId());
        KpiMeasurement measurement = kpiMeasurementRepository
                .findByBscStrategy_IdAndDepartmentKpi_Id(strategy.getId(), departmentKpiId)
                .orElseGet(KpiMeasurement::new);

        applyMeasurement(measurement, strategy, kpiWeight, request, reportOwner);
        kpiMeasurementRepository.save(measurement);

        return buildMeasurementTree(strategy.getId());
    }

    @Transactional(readOnly = true)
    public MeasurementTreeResponse getMeasurements(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return buildMeasurementTree(strategyId);
    }

    @Transactional
    public MeasurementTreeResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        ensureB6Completed(strategyId);

        BscStepStatus b7Status = getStepStatus(strategyId, BscStepCode.B7_MEASUREMENT_TARGET);
        BscStepStatus b8Status = getStepStatus(strategyId, BscStepCode.B8_ACTION_PLAN);
        if (b7Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b7Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        validateAllWeightedKpisMeasured(strategyId);

        b7Status.setStatus(BscStepStatusValue.COMPLETED);
        b7Status.setCompletedAt(LocalDateTime.now());
        b7Status.setCompletedBy(null);
        b7Status.setInvalidatedReason(null);

        if (b8Status.getStatus() == BscStepStatusValue.LOCKED) {
            b8Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b7Status, b8Status));
        return buildMeasurementTree(strategyId);
    }

    private MeasurementTreeResponse buildMeasurementTree(UUID strategyId) {
        List<KpiWeight> activeKpiWeights = activeKpiWeights(strategyId);
        Map<UUID, KpiMeasurement> measurementsByKpi = kpiMeasurementRepository.findByBscStrategy_Id(strategyId)
                .stream()
                .collect(Collectors.toMap(
                        measurement -> measurement.getDepartmentKpi().getId(),
                        measurement -> measurement
                ));

        Map<BscPerspectiveCode, List<KpiWeight>> weightsByPerspective = activeKpiWeights.stream()
                .collect(Collectors.groupingBy(
                        KpiWeight::getPerspectiveCode,
                        () -> new EnumMap<>(BscPerspectiveCode.class),
                        Collectors.toList()
                ));

        List<MeasurementPerspectiveResponse> perspectiveResponses = Arrays.stream(BscPerspectiveCode.values())
                .map(perspectiveCode -> toPerspectiveTree(
                        perspectiveCode,
                        weightsByPerspective.getOrDefault(perspectiveCode, List.of()),
                        measurementsByKpi
                ))
                .toList();

        int totalWeightedKpis = activeKpiWeights.size();
        int configuredMeasurements = (int) activeKpiWeights.stream()
                .map(weight -> measurementsByKpi.get(weight.getDepartmentKpi().getId()))
                .filter(this::isActiveMeasurementValid)
                .count();
        int missingMeasurements = totalWeightedKpis - configuredMeasurements;
        boolean valid = totalWeightedKpis > 0
                && missingMeasurements == 0
                && perspectiveResponses.stream().allMatch(MeasurementPerspectiveResponse::isValid);

        return MeasurementTreeResponse.builder()
                .bscStrategyId(strategyId)
                .totalWeightedKpis(totalWeightedKpis)
                .configuredMeasurements(configuredMeasurements)
                .missingMeasurements(missingMeasurements)
                .valid(valid)
                .perspectives(perspectiveResponses)
                .build();
    }

    private MeasurementPerspectiveResponse toPerspectiveTree(
            BscPerspectiveCode perspectiveCode,
            List<KpiWeight> kpiWeights,
            Map<UUID, KpiMeasurement> measurementsByKpi
    ) {
        Map<UUID, List<KpiWeight>> weightsByObjective = kpiWeights.stream()
                .collect(Collectors.groupingBy(weight -> weight.getFinalStrategicObjective().getId()));
        List<MeasurementObjectiveResponse> objectiveResponses = weightsByObjective.values()
                .stream()
                .map(weights -> toObjectiveTree(weights.getFirst().getFinalStrategicObjective(), weights, measurementsByKpi))
                .toList();
        boolean valid = !objectiveResponses.isEmpty()
                && objectiveResponses.stream().allMatch(MeasurementObjectiveResponse::isValid);

        return kpiMeasurementMapper.toPerspectiveResponse(perspectiveCode, valid, objectiveResponses);
    }

    private MeasurementObjectiveResponse toObjectiveTree(
            FinalStrategicObjective finalObjective,
            List<KpiWeight> kpiWeights,
            Map<UUID, KpiMeasurement> measurementsByKpi
    ) {
        List<KpiMeasurementResponse> kpiResponses = kpiWeights.stream()
                .map(weight -> {
                    KpiMeasurement measurement = measurementsByKpi.get(weight.getDepartmentKpi().getId());
                    return kpiMeasurementMapper.toKpiResponse(weight, measurement, isActiveMeasurementValid(measurement));
                })
                .toList();
        boolean valid = !kpiResponses.isEmpty() && kpiResponses.stream().allMatch(KpiMeasurementResponse::isValid);
        return kpiMeasurementMapper.toObjectiveResponse(finalObjective, valid, kpiResponses);
    }

    private void validateAllWeightedKpisMeasured(UUID strategyId) {
        List<KpiWeight> activeKpiWeights = activeKpiWeights(strategyId);
        if (activeKpiWeights.isEmpty()) {
            throw new BusinessException(ErrorCode.B7_KPI_MEASUREMENT_MISSING);
        }

        Map<UUID, KpiMeasurement> measurementsByKpi = new HashMap<>();
        for (KpiMeasurement measurement : kpiMeasurementRepository.findByBscStrategy_Id(strategyId)) {
            if (measurement.getStatus() == MeasurementStatus.ACTIVE) {
                measurementsByKpi.put(measurement.getDepartmentKpi().getId(), measurement);
            }
        }

        for (KpiWeight kpiWeight : activeKpiWeights) {
            validateActiveWeightedKpi(kpiWeight);
            KpiMeasurement measurement = measurementsByKpi.get(kpiWeight.getDepartmentKpi().getId());
            if (!isActiveMeasurementValid(measurement)) {
                throw new BusinessException(ErrorCode.B7_KPI_MEASUREMENT_MISSING);
            }
            validateMeasurementMatchesWeight(measurement, kpiWeight);
        }
    }

    private List<KpiWeight> activeKpiWeights(UUID strategyId) {
        return kpiWeightRepository.findByBscStrategy_Id(strategyId)
                .stream()
                .filter(weight -> weight.getDepartmentKpi().getStatus() == DepartmentKpiStatus.ACTIVE)
                .toList();
    }

    private KpiWeight getKpiWeightForMeasurement(UUID departmentKpiId) {
        if (departmentKpiId == null) {
            throw new BusinessException(ErrorCode.B5_KPI_NOT_FOUND);
        }
        List<KpiWeight> weights = kpiWeightRepository.findByDepartmentKpi_Id(departmentKpiId);
        if (weights.isEmpty()) {
            throw new BusinessException(ErrorCode.B7_KPI_WEIGHT_REQUIRED);
        }
        if (weights.size() > 1) {
            throw new BusinessException(ErrorCode.B7_KPI_WEIGHT_REQUIRED, "Department KPI must have one KPI weight");
        }
        return weights.getFirst();
    }

    private void validateMeasurementRequest(UpsertKpiMeasurementRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR);
        }
        if (request.getUnit() == null || request.getUnit().isBlank()) {
            throw new BusinessException(ErrorCode.B7_UNIT_REQUIRED);
        }
        if (request.getTargetValue() == null || request.getTargetValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.B7_TARGET_REQUIRED);
        }
        if (request.getBaselineValue() != null && request.getBaselineValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "baselineValue must be greater than or equal to 0");
        }
        if (request.getDirection() == null) {
            throw new BusinessException(ErrorCode.B7_DIRECTION_REQUIRED);
        }
        if (request.getReportingFrequency() == null) {
            throw new BusinessException(ErrorCode.B7_REPORTING_FREQUENCY_REQUIRED);
        }

        BigDecimal green = defaultIfNull(request.getGreenThreshold(), DEFAULT_GREEN_THRESHOLD);
        BigDecimal yellow = defaultIfNull(request.getYellowThreshold(), DEFAULT_YELLOW_THRESHOLD);
        BigDecimal red = defaultIfNull(request.getRedThreshold(), DEFAULT_RED_THRESHOLD);
        validateThresholds(green, yellow, red);
    }

    private Employee resolveReportOwner(BscStrategy strategy, UUID reportOwnerId) {
        if (reportOwnerId == null) {
            return null;
        }
        Employee employee = employeeRepository.findById(reportOwnerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B7_REPORT_OWNER_INVALID));
        if (!employee.getCompany().getId().equals(strategy.getCompany().getId())) {
            throw new BusinessException(ErrorCode.B7_REPORT_OWNER_INVALID);
        }
        return employee;
    }

    private void applyMeasurement(
            KpiMeasurement measurement,
            BscStrategy strategy,
            KpiWeight kpiWeight,
            UpsertKpiMeasurementRequest request,
            Employee reportOwner
    ) {
        measurement.setBscStrategy(strategy);
        measurement.setDepartmentKpi(kpiWeight.getDepartmentKpi());
        measurement.setFinalStrategicObjective(kpiWeight.getFinalStrategicObjective());
        measurement.setDepartment(kpiWeight.getDepartment());
        measurement.setPerspectiveCode(kpiWeight.getPerspectiveCode());
        measurement.setUnit(request.getUnit().trim());
        measurement.setBaselineValue(request.getBaselineValue());
        measurement.setTargetValue(request.getTargetValue());
        measurement.setDirection(request.getDirection());
        measurement.setReportingFrequency(request.getReportingFrequency());
        measurement.setFormulaDescription(trimToNull(request.getFormulaDescription()));
        measurement.setGreenThreshold(defaultIfNull(request.getGreenThreshold(), DEFAULT_GREEN_THRESHOLD));
        measurement.setYellowThreshold(defaultIfNull(request.getYellowThreshold(), DEFAULT_YELLOW_THRESHOLD));
        measurement.setRedThreshold(defaultIfNull(request.getRedThreshold(), DEFAULT_RED_THRESHOLD));
        measurement.setReportOwner(reportOwner);
        measurement.setStatus(MeasurementStatus.ACTIVE);
        measurement.setCreatedBy(null);
        measurement.setUpdatedBy(null);
    }

    private void validateActiveWeightedKpi(KpiWeight kpiWeight) {
        DepartmentKpi kpi = kpiWeight.getDepartmentKpi();
        if (kpi.getStatus() != DepartmentKpiStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B7_KPI_WEIGHT_REQUIRED);
        }
        if (!kpi.getBscStrategy().getId().equals(kpiWeight.getBscStrategy().getId())
                || !kpi.getFinalStrategicObjective().getId().equals(kpiWeight.getFinalStrategicObjective().getId())
                || !kpi.getDepartment().getId().equals(kpiWeight.getDepartment().getId())
                || kpi.getFinalStrategicObjective().getPerspectiveCode() != kpiWeight.getPerspectiveCode()) {
            throw new BusinessException(ErrorCode.B7_KPI_WEIGHT_REQUIRED);
        }
        if (kpiWeight.getWeightPercent() == null || kpiWeight.getWeightPercent().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.B7_KPI_WEIGHT_REQUIRED);
        }
    }

    private void validateMeasurementMatchesWeight(KpiMeasurement measurement, KpiWeight kpiWeight) {
        if (!measurement.getBscStrategy().getId().equals(kpiWeight.getBscStrategy().getId())
                || !measurement.getDepartmentKpi().getId().equals(kpiWeight.getDepartmentKpi().getId())
                || !measurement.getFinalStrategicObjective().getId().equals(kpiWeight.getFinalStrategicObjective().getId())
                || !measurement.getDepartment().getId().equals(kpiWeight.getDepartment().getId())
                || measurement.getPerspectiveCode() != kpiWeight.getPerspectiveCode()) {
            throw new BusinessException(ErrorCode.B7_KPI_MEASUREMENT_MISSING);
        }
    }

    private boolean isActiveMeasurementValid(KpiMeasurement measurement) {
        if (measurement == null || measurement.getStatus() != MeasurementStatus.ACTIVE) {
            return false;
        }
        if (measurement.getUnit() == null || measurement.getUnit().isBlank()) {
            return false;
        }
        if (measurement.getTargetValue() == null || measurement.getTargetValue().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (measurement.getBaselineValue() != null && measurement.getBaselineValue().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (measurement.getDirection() == null || measurement.getReportingFrequency() == null) {
            return false;
        }
        return thresholdsValid(
                measurement.getGreenThreshold(),
                measurement.getYellowThreshold(),
                measurement.getRedThreshold()
        );
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB6Completed(UUID strategyId) {
        BscStepStatus b6Status = getStepStatus(strategyId, BscStepCode.B6_WEIGHT_ALLOCATION);
        if (b6Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B6_WEIGHT_ALLOCATION must be completed before B7");
        }
    }

    private void ensureB7StepEditable(UUID strategyId) {
        BscStepStatus b7Status = getStepStatus(strategyId, BscStepCode.B7_MEASUREMENT_TARGET);
        if (b7Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b7Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private void validateThresholds(BigDecimal green, BigDecimal yellow, BigDecimal red) {
        if (!thresholdsValid(green, yellow, red)) {
            throw new BusinessException(ErrorCode.B7_THRESHOLD_INVALID);
        }
    }

    private boolean thresholdsValid(BigDecimal green, BigDecimal yellow, BigDecimal red) {
        return green != null
                && yellow != null
                && red != null
                && green.compareTo(yellow) > 0
                && yellow.compareTo(red) > 0;
    }

    private BigDecimal defaultIfNull(BigDecimal value, BigDecimal defaultValue) {
        return value == null ? defaultValue : value;
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
