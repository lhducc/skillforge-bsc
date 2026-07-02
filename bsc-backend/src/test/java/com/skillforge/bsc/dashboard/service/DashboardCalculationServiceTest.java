package com.skillforge.bsc.dashboard.service;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.service.KpiReportCalculator;
import com.skillforge.bsc.common.enums.*;
import com.skillforge.bsc.dashboard.dto.response.DashboardScoringDataStatus;
import com.skillforge.bsc.dashboard.dto.response.DashboardTaskSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DashboardCalculationServiceTest {

    private DashboardCalculationService service;

    @BeforeEach
    void setUp() {
        service = new DashboardCalculationService(new KpiReportCalculator());
    }

    @Test
    void selectsLatestApprovedBeforeNewerSubmittedReport() {
        KpiReport olderApproved = report(KpiReportStatus.APPROVED, "80", LocalDateTime.of(2026, 1, 10, 10, 0));
        KpiReport latestApproved = report(KpiReportStatus.APPROVED, "90", LocalDateTime.of(2026, 2, 10, 10, 0));
        KpiReport newerSubmitted = report(KpiReportStatus.SUBMITTED, "95", LocalDateTime.of(2026, 3, 10, 10, 0));

        DashboardCalculationService.ScoringReportSelection selection =
                service.selectScoringReport(List.of(olderApproved, newerSubmitted, latestApproved));

        assertThat(selection.report()).isSameAs(latestApproved);
        assertThat(selection.dataStatus()).isEqualTo(DashboardScoringDataStatus.APPROVED);
    }

    @Test
    void usesLatestSubmittedAsProvisionalWhenNoApprovedReportExists() {
        KpiReport older = report(KpiReportStatus.SUBMITTED, "70", LocalDateTime.of(2026, 1, 10, 10, 0));
        KpiReport latest = report(KpiReportStatus.SUBMITTED, "80", LocalDateTime.of(2026, 2, 10, 10, 0));
        KpiReport rejected = report(KpiReportStatus.REJECTED, "100", LocalDateTime.of(2026, 3, 10, 10, 0));

        DashboardCalculationService.ScoringReportSelection selection =
                service.selectScoringReport(List.of(rejected, older, latest));

        assertThat(selection.report()).isSameAs(latest);
        assertThat(selection.dataStatus()).isEqualTo(DashboardScoringDataStatus.PROVISIONAL);
    }

    @Test
    void draftAndRejectedReportsDoNotScore() {
        DashboardCalculationService.ScoringReportSelection selection = service.selectScoringReport(List.of(
                report(KpiReportStatus.DRAFT, "80", LocalDateTime.now()),
                report(KpiReportStatus.REJECTED, "90", LocalDateTime.now())
        ));

        assertThat(selection.report()).isNull();
        assertThat(selection.dataStatus()).isEqualTo(DashboardScoringDataStatus.MISSING_REPORT);
    }

    @Test
    void preservesRawCompletionButCapsWeightedScoreAtKpiWeight() {
        KpiMeasurement measurement = measurement(KpiDirection.HIGHER_IS_BETTER, "100");
        KpiReport report = report(KpiReportStatus.APPROVED, "150", LocalDateTime.now());

        DashboardCalculationService.KpiScore score = service.calculateKpiScore(
                measurement,
                new DashboardCalculationService.ScoringReportSelection(report, DashboardScoringDataStatus.APPROVED),
                new BigDecimal("10")
        );

        assertThat(score.getCompletionRate()).isEqualByComparingTo("150.000");
        assertThat(score.getScoreCompletion()).isEqualByComparingTo("100.000");
        assertThat(score.getWeightedScore()).isEqualByComparingTo("10.000");
        assertThat(score.getAchievementStatus()).isEqualTo(KpiAchievementStatus.EXCEEDED);
    }

    @Test
    void missingReportAlwaysScoresZeroEvenWhenTargetIsZero() {
        KpiMeasurement measurement = measurement(KpiDirection.HIGHER_IS_BETTER, "0");

        DashboardCalculationService.KpiScore score = service.calculateKpiScore(
                measurement,
                new DashboardCalculationService.ScoringReportSelection(null, DashboardScoringDataStatus.MISSING_REPORT),
                new BigDecimal("10")
        );

        assertThat(score.getDataStatus()).isEqualTo(DashboardScoringDataStatus.MISSING_REPORT);
        assertThat(score.getCompletionRate()).isEqualByComparingTo("0.000");
        assertThat(score.getWeightedScore()).isEqualByComparingTo("0.000");
    }

    @Test
    void missingMeasurementDoesNotFailAndScoresZero() {
        KpiReport report = report(KpiReportStatus.APPROVED, "50", LocalDateTime.now());

        DashboardCalculationService.KpiScore score = service.calculateKpiScore(
                null,
                new DashboardCalculationService.ScoringReportSelection(report, DashboardScoringDataStatus.APPROVED),
                new BigDecimal("10")
        );

        assertThat(score.getDataStatus()).isEqualTo(DashboardScoringDataStatus.MISSING_MEASUREMENT);
        assertThat(score.getWeightedScore()).isEqualByComparingTo("0.000");
    }

    @Test
    void usesPhaseNineSafeDivideByZeroBehavior() {
        KpiReport zeroActual = report(KpiReportStatus.APPROVED, "0", LocalDateTime.now());

        DashboardCalculationService.KpiScore higher = service.calculateKpiScore(
                measurement(KpiDirection.HIGHER_IS_BETTER, "0"),
                new DashboardCalculationService.ScoringReportSelection(zeroActual, DashboardScoringDataStatus.APPROVED),
                new BigDecimal("8")
        );
        DashboardCalculationService.KpiScore lower = service.calculateKpiScore(
                measurement(KpiDirection.LOWER_IS_BETTER, "10"),
                new DashboardCalculationService.ScoringReportSelection(zeroActual, DashboardScoringDataStatus.APPROVED),
                new BigDecimal("8")
        );

        assertThat(higher.getCompletionRate()).isEqualByComparingTo("100.000");
        assertThat(lower.getCompletionRate()).isEqualByComparingTo("100.000");
        assertThat(higher.getWeightedScore()).isEqualByComparingTo("8.000");
        assertThat(lower.getWeightedScore()).isEqualByComparingTo("8.000");
    }

    @Test
    void taskProgressExcludesCancelledAndOverdueUsesStatusAndDate() {
        LocalDate today = LocalDate.of(2026, 7, 2);
        Task done = task(TaskStatus.DONE, today.minusDays(2));
        Task cancelled = task(TaskStatus.CANCELLED, today.minusDays(2));
        Task overdue = task(TaskStatus.IN_PROGRESS, today.minusDays(1));
        Task dueToday = task(TaskStatus.BLOCKED, today);

        DashboardTaskSummary summary = service.summarizeTasks(
                List.of(done, cancelled, overdue, dueToday), today);

        assertThat(summary.getTotal()).isEqualTo(4);
        assertThat(summary.getValidTotal()).isEqualTo(3);
        assertThat(summary.getDone()).isEqualTo(1);
        assertThat(summary.getCancelled()).isEqualTo(1);
        assertThat(summary.getBlocked()).isEqualTo(1);
        assertThat(summary.getOverdue()).isEqualTo(1);
        assertThat(summary.getWorkProgressPercent()).isEqualByComparingTo("33.333");
    }

    @Test
    void aggregateCompletionUsesWeightedScoreAgainstAllocatedWeight() {
        BigDecimal completion = service.aggregateCompletion(
                new BigDecimal("30.5"),
                new BigDecimal("40")
        );

        assertThat(completion).isEqualByComparingTo("76.250");
        assertThat(service.aggregateStatusColor(completion)).isEqualTo(KpiStatusColor.YELLOW);
    }

    private KpiMeasurement measurement(KpiDirection direction, String target) {
        KpiMeasurement measurement = new KpiMeasurement();
        measurement.setDirection(direction);
        measurement.setTargetValue(new BigDecimal(target));
        measurement.setGreenThreshold(new BigDecimal("90"));
        measurement.setYellowThreshold(new BigDecimal("70"));
        measurement.setRedThreshold(BigDecimal.ZERO);
        return measurement;
    }

    private KpiReport report(KpiReportStatus status, String actual, LocalDateTime lifecycleTime) {
        KpiReport report = new KpiReport();
        report.setReviewStatus(status);
        report.setActualValue(new BigDecimal(actual));
        report.setCreatedAt(lifecycleTime.minusDays(1));
        report.setUpdatedAt(lifecycleTime);
        if (status == KpiReportStatus.APPROVED || status == KpiReportStatus.REJECTED) {
            report.setReviewedAt(lifecycleTime);
        }
        if (status == KpiReportStatus.SUBMITTED) {
            report.setSubmittedAt(lifecycleTime);
        }
        return report;
    }

    private Task task(TaskStatus status, LocalDate dueDate) {
        Task task = new Task();
        task.setStatus(status);
        task.setDueDate(dueDate);
        return task;
    }
}
