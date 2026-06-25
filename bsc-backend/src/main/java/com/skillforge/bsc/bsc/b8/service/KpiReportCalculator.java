package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiDirection;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class KpiReportCalculator {

    private static final BigDecimal HUNDRED = new BigDecimal("100.000");
    private static final BigDecimal ZERO = new BigDecimal("0.000");

    public Result calculate(KpiMeasurement measurement, BigDecimal actualValue) {
        BigDecimal completionRate = calculateCompletionRate(measurement, actualValue);
        return Result.builder()
                .completionRate(completionRate)
                .statusColor(statusColor(measurement, completionRate))
                .achievementStatus(achievementStatus(completionRate))
                .build();
    }

    private BigDecimal calculateCompletionRate(KpiMeasurement measurement, BigDecimal actualValue) {
        BigDecimal targetValue = measurement.getTargetValue();
        if (measurement.getDirection() == KpiDirection.HIGHER_IS_BETTER) {
            if (targetValue.compareTo(BigDecimal.ZERO) == 0) {
                return actualValue.compareTo(BigDecimal.ZERO) >= 0 ? HUNDRED : ZERO;
            }
            return actualValue
                    .divide(targetValue, 6, RoundingMode.HALF_UP)
                    .multiply(HUNDRED)
                    .setScale(3, RoundingMode.HALF_UP);
        }

        if (actualValue.compareTo(BigDecimal.ZERO) == 0) {
            return targetValue.compareTo(BigDecimal.ZERO) >= 0 ? HUNDRED : ZERO;
        }
        return targetValue
                .divide(actualValue, 6, RoundingMode.HALF_UP)
                .multiply(HUNDRED)
                .setScale(3, RoundingMode.HALF_UP);
    }

    private KpiStatusColor statusColor(KpiMeasurement measurement, BigDecimal completionRate) {
        if (completionRate.compareTo(measurement.getGreenThreshold()) >= 0) {
            return KpiStatusColor.GREEN;
        }
        if (completionRate.compareTo(measurement.getYellowThreshold()) >= 0) {
            return KpiStatusColor.YELLOW;
        }
        return KpiStatusColor.RED;
    }

    private KpiAchievementStatus achievementStatus(BigDecimal completionRate) {
        if (completionRate.compareTo(HUNDRED) > 0) {
            return KpiAchievementStatus.EXCEEDED;
        }
        if (completionRate.compareTo(HUNDRED) >= 0) {
            return KpiAchievementStatus.ACHIEVED;
        }
        return KpiAchievementStatus.IN_PROGRESS;
    }

    @Getter
    @Builder
    public static class Result {

        private BigDecimal completionRate;
        private KpiStatusColor statusColor;
        private KpiAchievementStatus achievementStatus;
    }
}
