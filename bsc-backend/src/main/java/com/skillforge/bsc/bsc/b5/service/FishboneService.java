package com.skillforge.bsc.bsc.b5.service;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b4.repository.FinalStrategicObjectiveRepository;
import com.skillforge.bsc.bsc.b5.dto.request.CreateDepartmentKpiRequest;
import com.skillforge.bsc.bsc.b5.dto.request.CreateDepartmentParticipationRequest;
import com.skillforge.bsc.bsc.b5.dto.request.UpdateDepartmentKpiRequest;
import com.skillforge.bsc.bsc.b5.dto.response.CompanyFishboneResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentFishboneResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentKpiResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentParticipationResponse;
import com.skillforge.bsc.bsc.b5.dto.response.FishboneObjectiveResponse;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.entity.DepartmentParticipation;
import com.skillforge.bsc.bsc.b5.mapper.FishboneMapper;
import com.skillforge.bsc.bsc.b5.repository.DepartmentKpiRepository;
import com.skillforge.bsc.bsc.b5.repository.DepartmentParticipationRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import com.skillforge.bsc.common.enums.DepartmentParticipationStatus;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.department.repository.DepartmentRepository;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FishboneService {

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final FinalStrategicObjectiveRepository finalStrategicObjectiveRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentParticipationRepository departmentParticipationRepository;
    private final DepartmentKpiRepository departmentKpiRepository;
    private final FishboneMapper fishboneMapper;

    @Transactional
    public DepartmentParticipationResponse joinFinalObjective(UUID strategyId, CreateDepartmentParticipationRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB4Completed(strategyId);
        ensureB5StepEditable(strategyId);

        FinalStrategicObjective finalObjective = getActiveFinalObjective(strategyId, request.getFinalStrategicObjectiveId());
        Department department = getValidDepartment(strategy, request.getDepartmentId());
        Employee departmentHead = getValidDepartmentHead(strategy, department, request.getDepartmentHeadId());

        DepartmentParticipation participation = departmentParticipationRepository
                .findByBscStrategy_IdAndFinalStrategicObjective_IdAndDepartment_Id(
                        strategyId,
                        finalObjective.getId(),
                        department.getId()
                )
                .map(existing -> reactivateParticipation(existing, departmentHead))
                .orElseGet(() -> createParticipation(strategy, finalObjective, department, departmentHead));

        return fishboneMapper.toParticipationResponse(departmentParticipationRepository.save(participation));
    }

    @Transactional
    public void removeParticipation(UUID participationId) {
        DepartmentParticipation participation = getActiveParticipation(participationId);
        UUID strategyId = participation.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB4Completed(strategyId);
        ensureB5StepEditable(strategyId);

        long activeKpiCount = departmentKpiRepository.countByDepartmentParticipation_IdAndStatus(
                participationId,
                DepartmentKpiStatus.ACTIVE
        );
        if (activeKpiCount > 0) {
            throw new BusinessException(ErrorCode.B5_PARTICIPATION_HAS_ACTIVE_KPIS);
        }

        participation.setStatus(DepartmentParticipationStatus.REMOVED);
        departmentParticipationRepository.save(participation);
    }

    @Transactional
    public DepartmentKpiResponse createDepartmentKpi(CreateDepartmentKpiRequest request) {
        BscStrategy strategy = getEditableStrategy(request.getBscStrategyId());
        ensureB4Completed(strategy.getId());
        ensureB5StepEditable(strategy.getId());

        DepartmentParticipation participation = validateKpiParticipation(
                request.getBscStrategyId(),
                request.getFinalStrategicObjectiveId(),
                request.getDepartmentId(),
                request.getDepartmentParticipationId()
        );
        String name = normalizeRequired(request.getName(), ErrorCode.B5_KPI_NAME_REQUIRED, "Department KPI name must not be blank");
        rejectDuplicateKpi(request.getBscStrategyId(), request.getFinalStrategicObjectiveId(), request.getDepartmentId(), name, null);

        DepartmentKpi kpi = new DepartmentKpi();
        kpi.setBscStrategy(strategy);
        kpi.setFinalStrategicObjective(participation.getFinalStrategicObjective());
        kpi.setDepartment(participation.getDepartment());
        kpi.setDepartmentParticipation(participation);
        kpi.setName(name);
        kpi.setDescription(normalize(request.getDescription()));
        kpi.setDisplayOrder(request.getDisplayOrder());
        kpi.setStatus(DepartmentKpiStatus.ACTIVE);
        kpi.setCreatedBy(null);

        return fishboneMapper.toKpiResponse(departmentKpiRepository.save(kpi));
    }

    @Transactional
    public DepartmentKpiResponse updateDepartmentKpi(UUID departmentKpiId, UpdateDepartmentKpiRequest request) {
        DepartmentKpi kpi = getActiveKpi(departmentKpiId);
        BscStrategy strategy = getEditableStrategy(request.getBscStrategyId());
        ensureB4Completed(strategy.getId());
        ensureB5StepEditable(strategy.getId());

        DepartmentParticipation participation = validateKpiParticipation(
                request.getBscStrategyId(),
                request.getFinalStrategicObjectiveId(),
                request.getDepartmentId(),
                request.getDepartmentParticipationId()
        );
        if (!kpi.getBscStrategy().getId().equals(request.getBscStrategyId())) {
            throw new BusinessException(ErrorCode.B5_KPI_MUST_BELONG_TO_PARTICIPATION);
        }

        String name = normalizeRequired(request.getName(), ErrorCode.B5_KPI_NAME_REQUIRED, "Department KPI name must not be blank");
        rejectDuplicateKpi(request.getBscStrategyId(), request.getFinalStrategicObjectiveId(), request.getDepartmentId(), name, kpi.getId());

        kpi.setFinalStrategicObjective(participation.getFinalStrategicObjective());
        kpi.setDepartment(participation.getDepartment());
        kpi.setDepartmentParticipation(participation);
        kpi.setName(name);
        kpi.setDescription(normalize(request.getDescription()));
        kpi.setDisplayOrder(request.getDisplayOrder());

        return fishboneMapper.toKpiResponse(departmentKpiRepository.save(kpi));
    }

    @Transactional
    public void deleteDepartmentKpi(UUID departmentKpiId) {
        DepartmentKpi kpi = getActiveKpi(departmentKpiId);
        UUID strategyId = kpi.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB4Completed(strategyId);
        ensureB5StepEditable(strategyId);

        kpi.setStatus(DepartmentKpiStatus.DELETED);
        departmentKpiRepository.save(kpi);
    }

    @Transactional(readOnly = true)
    public CompanyFishboneResponse getCompanyFishbone(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        ensureB4Completed(strategyId);
        List<FinalStrategicObjective> finalObjectives = activeFinalObjectives(strategyId);
        Map<UUID, List<DepartmentParticipation>> participationsByObjective = activeParticipationsByObjective(strategyId);
        Map<UUID, List<DepartmentKpi>> kpisByObjective = activeKpisByObjective(strategyId);

        return CompanyFishboneResponse.builder()
                .bscStrategyId(strategyId)
                .companyId(strategy.getCompany().getId())
                .companyName(strategy.getCompany().getName())
                .objectives(toFishboneObjectives(finalObjectives, participationsByObjective, kpisByObjective))
                .build();
    }

    @Transactional(readOnly = true)
    public DepartmentFishboneResponse getDepartmentFishbone(UUID strategyId, UUID departmentId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        ensureB4Completed(strategyId);
        Department department = getValidDepartment(strategy, departmentId);

        List<DepartmentParticipation> participations = departmentParticipationRepository
                .findByBscStrategy_IdAndDepartment_IdAndStatusOrderByCreatedAtAsc(
                        strategyId,
                        departmentId,
                        DepartmentParticipationStatus.ACTIVE
                );
        List<DepartmentKpi> kpis = departmentKpiRepository
                .findByBscStrategy_IdAndDepartment_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        departmentId,
                        DepartmentKpiStatus.ACTIVE
                );

        Map<UUID, FinalStrategicObjective> objectivesById = activeFinalObjectives(strategyId).stream()
                .collect(Collectors.toMap(FinalStrategicObjective::getId, objective -> objective));
        Map<UUID, List<DepartmentParticipation>> participationsByObjective = participations.stream()
                .collect(Collectors.groupingBy(participation -> participation.getFinalStrategicObjective().getId()));
        Map<UUID, List<DepartmentKpi>> kpisByObjective = kpis.stream()
                .collect(Collectors.groupingBy(kpi -> kpi.getFinalStrategicObjective().getId()));

        List<FinalStrategicObjective> joinedObjectives = participations.stream()
                .map(participation -> objectivesById.get(participation.getFinalStrategicObjective().getId()))
                .filter(objective -> objective != null)
                .distinct()
                .toList();

        return DepartmentFishboneResponse.builder()
                .bscStrategyId(strategyId)
                .departmentId(department.getId())
                .departmentName(department.getName())
                .departmentCode(department.getCode())
                .departmentColor(department.getColor())
                .objectives(toFishboneObjectives(joinedObjectives, participationsByObjective, kpisByObjective))
                .build();
    }

    @Transactional
    public CompanyFishboneResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        ensureB4Completed(strategyId);

        BscStepStatus b5Status = getStepStatus(strategyId, BscStepCode.B5_FISHBONE);
        BscStepStatus b6Status = getStepStatus(strategyId, BscStepCode.B6_WEIGHT_ALLOCATION);
        if (b5Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b5Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        List<FinalStrategicObjective> finalObjectives = activeFinalObjectives(strategyId);
        if (finalObjectives.isEmpty()) {
            throw new BusinessException(ErrorCode.B5_FINAL_OBJECTIVE_REQUIRED);
        }

        for (FinalStrategicObjective finalObjective : finalObjectives) {
            long participationCount = departmentParticipationRepository.countByFinalStrategicObjective_IdAndStatus(
                    finalObjective.getId(),
                    DepartmentParticipationStatus.ACTIVE
            );
            if (participationCount == 0) {
                throw new BusinessException(ErrorCode.B5_PARTICIPATION_REQUIRED);
            }

            long kpiCount = departmentKpiRepository.countByFinalStrategicObjective_IdAndStatus(
                    finalObjective.getId(),
                    DepartmentKpiStatus.ACTIVE
            );
            if (kpiCount == 0) {
                throw new BusinessException(ErrorCode.B5_KPI_REQUIRED);
            }
        }

        List<DepartmentParticipation> activeParticipations = departmentParticipationRepository
                .findByBscStrategy_IdAndStatusOrderByCreatedAtAsc(strategyId, DepartmentParticipationStatus.ACTIVE);
        for (DepartmentParticipation participation : activeParticipations) {
            long participationKpiCount = departmentKpiRepository.countByDepartmentParticipation_IdAndStatus(
                    participation.getId(),
                    DepartmentKpiStatus.ACTIVE
            );
            if (participationKpiCount == 0) {
                throw new BusinessException(ErrorCode.B5_KPI_REQUIRED);
            }
        }

        b5Status.setStatus(BscStepStatusValue.COMPLETED);
        b5Status.setCompletedAt(LocalDateTime.now());
        b5Status.setCompletedBy(null);
        b5Status.setInvalidatedReason(null);

        if (b6Status.getStatus() == BscStepStatusValue.LOCKED) {
            b6Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b5Status, b6Status));
        return getCompanyFishbone(strategyId);
    }

    private DepartmentParticipation createParticipation(
            BscStrategy strategy,
            FinalStrategicObjective finalObjective,
            Department department,
            Employee departmentHead
    ) {
        DepartmentParticipation participation = new DepartmentParticipation();
        participation.setBscStrategy(strategy);
        participation.setFinalStrategicObjective(finalObjective);
        participation.setDepartment(department);
        participation.setDepartmentHead(departmentHead);
        participation.setStatus(DepartmentParticipationStatus.ACTIVE);
        participation.setCreatedBy(null);
        return participation;
    }

    private DepartmentParticipation reactivateParticipation(DepartmentParticipation participation, Employee departmentHead) {
        if (participation.getStatus() == DepartmentParticipationStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B5_DEPARTMENT_ALREADY_JOINED_OBJECTIVE);
        }
        participation.setDepartmentHead(departmentHead);
        participation.setStatus(DepartmentParticipationStatus.ACTIVE);
        return participation;
    }

    private DepartmentParticipation validateKpiParticipation(
            UUID strategyId,
            UUID finalObjectiveId,
            UUID departmentId,
            UUID participationId
    ) {
        DepartmentParticipation participation = getActiveParticipation(participationId);
        if (!participation.getBscStrategy().getId().equals(strategyId)
                || !participation.getFinalStrategicObjective().getId().equals(finalObjectiveId)
                || !participation.getDepartment().getId().equals(departmentId)) {
            throw new BusinessException(ErrorCode.B5_KPI_MUST_BELONG_TO_PARTICIPATION);
        }
        getActiveFinalObjective(strategyId, finalObjectiveId);
        return participation;
    }

    private void rejectDuplicateKpi(UUID strategyId, UUID finalObjectiveId, UUID departmentId, String name, UUID currentKpiId) {
        boolean duplicated = departmentKpiRepository
                .findByBscStrategy_IdAndFinalStrategicObjective_IdAndDepartment_IdAndStatus(
                        strategyId,
                        finalObjectiveId,
                        departmentId,
                        DepartmentKpiStatus.ACTIVE
                )
                .stream()
                .anyMatch(kpi -> !kpi.getId().equals(currentKpiId) && kpi.getName().equalsIgnoreCase(name));
        if (duplicated) {
            throw new BusinessException(ErrorCode.B5_KPI_DUPLICATED);
        }
    }

    private List<FishboneObjectiveResponse> toFishboneObjectives(
            List<FinalStrategicObjective> finalObjectives,
            Map<UUID, List<DepartmentParticipation>> participationsByObjective,
            Map<UUID, List<DepartmentKpi>> kpisByObjective
    ) {
        return finalObjectives.stream()
                .map(objective -> fishboneMapper.toObjectiveResponse(
                        objective,
                        participationsByObjective.getOrDefault(objective.getId(), List.of()),
                        kpisByObjective.getOrDefault(objective.getId(), List.of())
                ))
                .toList();
    }

    private Map<UUID, List<DepartmentParticipation>> activeParticipationsByObjective(UUID strategyId) {
        return departmentParticipationRepository
                .findByBscStrategy_IdAndStatusOrderByCreatedAtAsc(strategyId, DepartmentParticipationStatus.ACTIVE)
                .stream()
                .collect(Collectors.groupingBy(participation -> participation.getFinalStrategicObjective().getId()));
    }

    private Map<UUID, List<DepartmentKpi>> activeKpisByObjective(UUID strategyId) {
        return departmentKpiRepository
                .findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(strategyId, DepartmentKpiStatus.ACTIVE)
                .stream()
                .collect(Collectors.groupingBy(kpi -> kpi.getFinalStrategicObjective().getId()));
    }

    private List<FinalStrategicObjective> activeFinalObjectives(UUID strategyId) {
        return finalStrategicObjectiveRepository
                .findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        StrategicObjectiveStatus.ACTIVE
                );
    }

    private DepartmentParticipation getActiveParticipation(UUID participationId) {
        DepartmentParticipation participation = departmentParticipationRepository.findById(participationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_PARTICIPATION_NOT_FOUND));
        if (participation.getStatus() != DepartmentParticipationStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B5_PARTICIPATION_NOT_FOUND);
        }
        return participation;
    }

    private DepartmentKpi getActiveKpi(UUID departmentKpiId) {
        DepartmentKpi kpi = departmentKpiRepository.findById(departmentKpiId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_KPI_NOT_FOUND));
        if (kpi.getStatus() != DepartmentKpiStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B5_KPI_NOT_FOUND);
        }
        return kpi;
    }

    private FinalStrategicObjective getActiveFinalObjective(UUID strategyId, UUID finalObjectiveId) {
        FinalStrategicObjective finalObjective = finalStrategicObjectiveRepository.findById(finalObjectiveId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_OBJECTIVE_NOT_FOUND));
        if (!finalObjective.getBscStrategy().getId().equals(strategyId)
                || finalObjective.getStatus() != StrategicObjectiveStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B5_OBJECTIVE_NOT_FOUND);
        }
        return finalObjective;
    }

    private Department getValidDepartment(BscStrategy strategy, UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_DEPARTMENT_NOT_FOUND));
        if (!department.getCompany().getId().equals(strategy.getCompany().getId())) {
            throw new BusinessException(ErrorCode.B5_DEPARTMENT_NOT_FOUND);
        }
        return department;
    }

    private Employee getValidDepartmentHead(BscStrategy strategy, Department department, UUID departmentHeadId) {
        if (departmentHeadId == null) {
            return null;
        }
        Employee employee = employeeRepository.findById(departmentHeadId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_DEPARTMENT_HEAD_INVALID));
        if (!employee.getCompany().getId().equals(strategy.getCompany().getId())
                || !employee.getDepartment().getId().equals(department.getId())) {
            throw new BusinessException(ErrorCode.B5_DEPARTMENT_HEAD_INVALID);
        }
        return employee;
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB4Completed(UUID strategyId) {
        BscStepStatus b4Status = getStepStatus(strategyId, BscStepCode.B4_STRATEGY_MAP);
        if (b4Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B4_STRATEGY_MAP must be completed before B5");
        }
    }

    private void ensureB5StepEditable(UUID strategyId) {
        BscStepStatus b5Status = getStepStatus(strategyId, BscStepCode.B5_FISHBONE);
        if (b5Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b5Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private String normalizeRequired(String value, ErrorCode errorCode, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(errorCode, message);
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
