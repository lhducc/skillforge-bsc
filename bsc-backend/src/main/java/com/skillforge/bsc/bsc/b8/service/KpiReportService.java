package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.repository.DepartmentKpiRepository;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.dto.request.CreateKpiReportRequest;
import com.skillforge.bsc.bsc.b8.dto.request.ReviewKpiReportRequest;
import com.skillforge.bsc.bsc.b8.dto.response.KpiReportResponse;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import com.skillforge.bsc.bsc.b8.mapper.KpiReportMapper;
import com.skillforge.bsc.bsc.b8.repository.KpiReportRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.enums.KpiReportStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KpiReportService {

    private final B8ValidationService validationService;
    private final DepartmentKpiRepository departmentKpiRepository;
    private final KpiReportRepository kpiReportRepository;
    private final KpiReportCalculator kpiReportCalculator;
    private final KpiReportMapper kpiReportMapper;

    @Transactional
    public KpiReportResponse create(CreateKpiReportRequest request) {
        DepartmentKpi departmentKpi = departmentKpiRepository.findById(request.getDepartmentKpiId())
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID));
        BscStrategy strategy = departmentKpi.getBscStrategy();
        validationService.ensureB8Accessible(strategy.getId());
        KpiMeasurement measurement = validationService.getMeasuredKpi(strategy.getId(), departmentKpi.getId());

        String reportingPeriod = validationService.normalizeRequired(
                request.getReportingPeriod(),
                ErrorCode.B8_KPI_REPORT_PERIOD_REQUIRED,
                "Reporting period must not be blank"
        );
        validateActualValue(request.getActualValue());
        if (kpiReportRepository.existsByBscStrategy_IdAndDepartmentKpi_IdAndReportingPeriod(
                strategy.getId(),
                departmentKpi.getId(),
                reportingPeriod
        )) {
            throw new BusinessException(ErrorCode.B8_KPI_REPORT_DUPLICATED_PERIOD);
        }

        KpiReportStatus reviewStatus = request.getReviewStatus() == null
                ? KpiReportStatus.SUBMITTED
                : request.getReviewStatus();
        if (reviewStatus != KpiReportStatus.DRAFT && reviewStatus != KpiReportStatus.SUBMITTED) {
            throw new BusinessException(ErrorCode.B8_KPI_REPORT_REVIEW_STATUS_INVALID);
        }

        Employee reporter = request.getReporterId() == null
                ? null
                : validationService.getValidEmployeeInDepartment(
                        strategy,
                        measurement.getDepartment(),
                        request.getReporterId(),
                        ErrorCode.B8_KPI_REPORT_REVIEWER_INVALID
                );
        KpiReportCalculator.Result calculation = kpiReportCalculator.calculate(measurement, request.getActualValue());

        KpiReport report = new KpiReport();
        report.setBscStrategy(strategy);
        report.setDepartmentKpi(measurement.getDepartmentKpi());
        report.setFinalStrategicObjective(measurement.getFinalStrategicObjective());
        report.setDepartment(measurement.getDepartment());
        report.setReportingPeriod(reportingPeriod);
        report.setActualValue(request.getActualValue());
        report.setCompletionRate(calculation.getCompletionRate());
        report.setStatusColor(calculation.getStatusColor());
        report.setAchievementStatus(calculation.getAchievementStatus());
        report.setNote(validationService.normalize(request.getNote()));
        report.setEvidenceUrl(validationService.normalize(request.getEvidenceUrl()));
        report.setReporter(reporter);
        report.setReviewStatus(reviewStatus);
        report.setSubmittedAt(reviewStatus == KpiReportStatus.SUBMITTED ? LocalDateTime.now() : null);

        return kpiReportMapper.toResponse(kpiReportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public List<KpiReportResponse> listByStrategy(
            UUID strategyId,
            UUID departmentKpiId,
            UUID departmentId,
            KpiReportStatus reviewStatus,
            String reportingPeriod
    ) {
        validationService.getStrategy(strategyId);
        String normalizedPeriod = validationService.normalize(reportingPeriod);
        return kpiReportRepository.findByBscStrategy_IdOrderByReportingPeriodDescCreatedAtDesc(strategyId)
                .stream()
                .filter(report -> departmentKpiId == null || report.getDepartmentKpi().getId().equals(departmentKpiId))
                .filter(report -> departmentId == null || report.getDepartment().getId().equals(departmentId))
                .filter(report -> reviewStatus == null || report.getReviewStatus() == reviewStatus)
                .filter(report -> normalizedPeriod == null || report.getReportingPeriod().equals(normalizedPeriod))
                .map(kpiReportMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KpiReportResponse> listByDepartmentKpi(UUID departmentKpiId) {
        departmentKpiRepository.findById(departmentKpiId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_MEASURED_KPI_INVALID));
        return kpiReportRepository.findByDepartmentKpi_IdOrderByReportingPeriodDescCreatedAtDesc(departmentKpiId)
                .stream()
                .map(kpiReportMapper::toResponse)
                .toList();
    }

    @Transactional
    public KpiReportResponse review(UUID reportId, ReviewKpiReportRequest request) {
        KpiReport report = kpiReportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_KPI_REPORT_NOT_FOUND));
        validationService.ensureB8Accessible(report.getBscStrategy().getId());
        if (request.getReviewStatus() != KpiReportStatus.APPROVED
                && request.getReviewStatus() != KpiReportStatus.REJECTED) {
            throw new BusinessException(ErrorCode.B8_KPI_REPORT_REVIEW_STATUS_INVALID);
        }

        Employee reviewer = request.getReviewerId() == null
                ? null
                : validationService.getValidEmployeeInDepartment(
                        report.getBscStrategy(),
                        report.getDepartment(),
                        request.getReviewerId(),
                        ErrorCode.B8_KPI_REPORT_REVIEWER_INVALID
                );

        report.setReviewStatus(request.getReviewStatus());
        report.setReviewNote(validationService.normalize(request.getNote()));
        report.setReviewedBy(reviewer);
        report.setReviewedAt(LocalDateTime.now());
        return kpiReportMapper.toResponse(kpiReportRepository.save(report));
    }

    private void validateActualValue(BigDecimal actualValue) {
        if (actualValue == null || actualValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.B8_KPI_REPORT_ACTUAL_REQUIRED);
        }
    }
}
