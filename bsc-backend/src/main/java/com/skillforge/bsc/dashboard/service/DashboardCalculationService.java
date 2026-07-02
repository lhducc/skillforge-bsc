package com.skillforge.bsc.dashboard.service;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.service.KpiReportCalculator;
import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiReportStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import com.skillforge.bsc.common.enums.TaskStatus;
import com.skillforge.bsc.dashboard.dto.response.DashboardScoringDataStatus;
import com.skillforge.bsc.dashboard.dto.response.DashboardTaskSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardCalculationService {

    private static final BigDecimal HUNDRED = new BigDecimal("100.000");
    private static final BigDecimal NINETY = new BigDecimal("90.000");
    private static final BigDecimal SEVENTY = new BigDecimal("70.000");
    private static final BigDecimal ZERO = new BigDecimal("0.000");

    private final KpiReportCalculator kpiReportCalculator;

    public ScoringReportSelection selectScoringReport(List<KpiReport> reports) {
        KpiReport approved = reports.stream()
                .filter(report -> report.getReviewStatus() == KpiReportStatus.APPROVED)
                .max(reportComparator(true))
                .orElse(null);
        if (approved != null) {
            return new ScoringReportSelection(approved, DashboardScoringDataStatus.APPROVED);
        }

        KpiReport submitted = reports.stream()
                .filter(report -> report.getReviewStatus() == KpiReportStatus.SUBMITTED)
                .max(reportComparator(false))
                .orElse(null);
        if (submitted != null) {
            return new ScoringReportSelection(submitted, DashboardScoringDataStatus.PROVISIONAL);
        }
        return new ScoringReportSelection(null, DashboardScoringDataStatus.MISSING_REPORT);
    }

    public KpiScore calculateKpiScore(
            KpiMeasurement measurement,
            ScoringReportSelection selection,
            BigDecimal weightPercent
    ) {
        BigDecimal weight = scale(weightPercent);
        if (measurement == null) {
            return zeroScore(DashboardScoringDataStatus.MISSING_MEASUREMENT);
        }
        if (selection.report() == null) {
            return KpiScore.builder()
                    .dataStatus(DashboardScoringDataStatus.MISSING_REPORT)
                    .completionRate(ZERO)
                    .scoreCompletion(ZERO)
                    .weightedScore(ZERO)
                    .statusColor(colorForThresholds(measurement, ZERO))
                    .achievementStatus(KpiAchievementStatus.IN_PROGRESS)
                    .build();
        }

        // Dashboard intentionally recalculates from the selected actual value and the current B7 measurement.
        // The stored B8 report is read-only and is never updated here.
        KpiReportCalculator.Result result = kpiReportCalculator.calculate(
                measurement,
                selection.report().getActualValue()
        );
        BigDecimal scoreCompletion = result.getCompletionRate().min(HUNDRED).max(ZERO);
        BigDecimal weightedScore = weight
                .multiply(scoreCompletion)
                .divide(HUNDRED, 3, RoundingMode.HALF_UP);
        return KpiScore.builder()
                .dataStatus(selection.dataStatus())
                .completionRate(scale(result.getCompletionRate()))
                .scoreCompletion(scale(scoreCompletion))
                .weightedScore(scale(weightedScore))
                .statusColor(result.getStatusColor())
                .achievementStatus(result.getAchievementStatus())
                .build();
    }

    public BigDecimal aggregateCompletion(BigDecimal weightedScore, BigDecimal allocatedWeight) {
        if (allocatedWeight == null || allocatedWeight.compareTo(BigDecimal.ZERO) <= 0) {
            return ZERO;
        }
        return scale(weightedScore)
                .multiply(HUNDRED)
                .divide(allocatedWeight, 3, RoundingMode.HALF_UP);
    }

    public KpiStatusColor aggregateStatusColor(BigDecimal completionRate) {
        if (completionRate.compareTo(NINETY) >= 0) {
            return KpiStatusColor.GREEN;
        }
        if (completionRate.compareTo(SEVENTY) >= 0) {
            return KpiStatusColor.YELLOW;
        }
        return KpiStatusColor.RED;
    }

    public DashboardTaskSummary summarizeTasks(List<Task> tasks, LocalDate currentDate) {
        long todo = count(tasks, TaskStatus.TODO);
        long inProgress = count(tasks, TaskStatus.IN_PROGRESS);
        long review = count(tasks, TaskStatus.REVIEW);
        long done = count(tasks, TaskStatus.DONE);
        long blocked = count(tasks, TaskStatus.BLOCKED);
        long cancelled = count(tasks, TaskStatus.CANCELLED);
        long validTotal = tasks.size() - cancelled;
        long overdue = tasks.stream().filter(task -> isOverdue(task, currentDate)).count();
        BigDecimal workProgress = validTotal == 0
                ? ZERO
                : BigDecimal.valueOf(done)
                        .multiply(HUNDRED)
                        .divide(BigDecimal.valueOf(validTotal), 3, RoundingMode.HALF_UP);

        return DashboardTaskSummary.builder()
                .total(tasks.size())
                .validTotal(validTotal)
                .todo(todo)
                .inProgress(inProgress)
                .review(review)
                .done(done)
                .blocked(blocked)
                .cancelled(cancelled)
                .overdue(overdue)
                .workProgressPercent(workProgress)
                .build();
    }

    public boolean isOverdue(Task task, LocalDate currentDate) {
        return task.getDueDate().isBefore(currentDate)
                && task.getStatus() != TaskStatus.DONE
                && task.getStatus() != TaskStatus.CANCELLED;
    }

    public BigDecimal scale(BigDecimal value) {
        return value == null ? ZERO : value.setScale(3, RoundingMode.HALF_UP);
    }

    private KpiScore zeroScore(DashboardScoringDataStatus status) {
        return KpiScore.builder()
                .dataStatus(status)
                .completionRate(ZERO)
                .scoreCompletion(ZERO)
                .weightedScore(ZERO)
                .statusColor(KpiStatusColor.RED)
                .achievementStatus(KpiAchievementStatus.IN_PROGRESS)
                .build();
    }

    private KpiStatusColor colorForThresholds(KpiMeasurement measurement, BigDecimal completionRate) {
        if (completionRate.compareTo(measurement.getGreenThreshold()) >= 0) {
            return KpiStatusColor.GREEN;
        }
        if (completionRate.compareTo(measurement.getYellowThreshold()) >= 0) {
            return KpiStatusColor.YELLOW;
        }
        return KpiStatusColor.RED;
    }

    private long count(List<Task> tasks, TaskStatus status) {
        return tasks.stream().filter(task -> task.getStatus() == status).count();
    }

    private Comparator<KpiReport> reportComparator(boolean approved) {
        return Comparator
                .comparing((KpiReport report) -> lifecycleTimestamp(report, approved),
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(KpiReport::getUpdatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(KpiReport::getCreatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(KpiReport::getId, Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    private LocalDateTime lifecycleTimestamp(KpiReport report, boolean approved) {
        return approved ? report.getReviewedAt() : report.getSubmittedAt();
    }

    public record ScoringReportSelection(KpiReport report, DashboardScoringDataStatus dataStatus) {
    }

    @Getter
    @Builder
    public static class KpiScore {
        private DashboardScoringDataStatus dataStatus;
        private BigDecimal completionRate;
        private BigDecimal scoreCompletion;
        private BigDecimal weightedScore;
        private KpiStatusColor statusColor;
        private KpiAchievementStatus achievementStatus;
    }
}
