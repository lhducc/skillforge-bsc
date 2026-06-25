package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.repository.DepartmentKpiRepository;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b7.repository.KpiMeasurementRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.enums.MeasurementStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class B8ValidationService {

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final DepartmentKpiRepository departmentKpiRepository;
    private final KpiMeasurementRepository kpiMeasurementRepository;
    private final EmployeeRepository employeeRepository;

    public BscStrategy getStrategy(UUID strategyId) {
        return bscStrategyService.getStrategy(strategyId);
    }

    public void ensureB7Completed(UUID strategyId) {
        BscStepStatus b7Status = getStepStatus(strategyId, BscStepCode.B7_MEASUREMENT_TARGET);
        if (b7Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B7_MEASUREMENT_TARGET must be completed before B8");
        }
    }

    public void ensureB8Accessible(UUID strategyId) {
        ensureB7Completed(strategyId);
        BscStepStatus b8Status = getStepStatus(strategyId, BscStepCode.B8_ACTION_PLAN);
        if (b8Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
    }

    public BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    public KpiMeasurement getMeasuredKpi(UUID strategyId, UUID departmentKpiId) {
        DepartmentKpi departmentKpi = departmentKpiRepository.findById(departmentKpiId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID));
        if (departmentKpi.getStatus() != DepartmentKpiStatus.ACTIVE
                || !departmentKpi.getBscStrategy().getId().equals(strategyId)) {
            throw new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID);
        }

        KpiMeasurement measurement = kpiMeasurementRepository
                .findByBscStrategy_IdAndDepartmentKpi_Id(strategyId, departmentKpiId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID));
        if (measurement.getStatus() != MeasurementStatus.ACTIVE
                || !measurement.getDepartmentKpi().getId().equals(departmentKpi.getId())
                || !measurement.getFinalStrategicObjective().getId().equals(departmentKpi.getFinalStrategicObjective().getId())
                || !measurement.getDepartment().getId().equals(departmentKpi.getDepartment().getId())) {
            throw new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID);
        }
        return measurement;
    }

    public Employee getValidEmployeeInDepartment(
            BscStrategy strategy,
            Department department,
            UUID employeeId,
            ErrorCode errorCode
    ) {
        Employee employee = getValidEmployeeInCompany(strategy, employeeId, errorCode);
        if (!employee.getDepartment().getId().equals(department.getId())) {
            throw new BusinessException(errorCode);
        }
        return employee;
    }

    public Employee getValidEmployeeInCompany(BscStrategy strategy, UUID employeeId, ErrorCode errorCode) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(errorCode));
        if (employee.getStatus() != EmployeeStatus.ACTIVE
                || !employee.getCompany().getId().equals(strategy.getCompany().getId())) {
            throw new BusinessException(errorCode);
        }
        return employee;
    }

    public String normalizeRequired(String value, ErrorCode errorCode, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(errorCode, message);
        }
        return normalized;
    }

    public String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
