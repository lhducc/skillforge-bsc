package com.skillforge.bsc.bsc.b8.mapper;

import com.skillforge.bsc.bsc.b8.dto.response.KpiReportResponse;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import org.springframework.stereotype.Component;

@Component
public class KpiReportMapper {

    public KpiReportResponse toResponse(KpiReport report) {
        return KpiReportResponse.builder()
                .id(report.getId())
                .bscStrategyId(report.getBscStrategy().getId())
                .departmentKpiId(report.getDepartmentKpi().getId())
                .departmentKpiName(report.getDepartmentKpi().getName())
                .finalStrategicObjectiveId(report.getFinalStrategicObjective().getId())
                .finalStrategicObjectiveName(report.getFinalStrategicObjective().getName())
                .perspectiveCode(report.getFinalStrategicObjective().getPerspectiveCode())
                .departmentId(report.getDepartment().getId())
                .departmentName(report.getDepartment().getName())
                .departmentCode(report.getDepartment().getCode())
                .reportingPeriod(report.getReportingPeriod())
                .actualValue(report.getActualValue())
                .completionRate(report.getCompletionRate())
                .statusColor(report.getStatusColor())
                .achievementStatus(report.getAchievementStatus())
                .note(report.getNote())
                .evidenceUrl(report.getEvidenceUrl())
                .reporterId(report.getReporter() == null ? null : report.getReporter().getId())
                .reporterName(report.getReporter() == null ? null : report.getReporter().getFullName())
                .reviewStatus(report.getReviewStatus())
                .reviewNote(report.getReviewNote())
                .reviewedById(report.getReviewedBy() == null ? null : report.getReviewedBy().getId())
                .reviewedByName(report.getReviewedBy() == null ? null : report.getReviewedBy().getFullName())
                .reviewedAt(report.getReviewedAt())
                .submittedAt(report.getSubmittedAt())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }
}
