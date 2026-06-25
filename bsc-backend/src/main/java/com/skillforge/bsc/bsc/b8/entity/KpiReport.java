package com.skillforge.bsc.bsc.b8.entity;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiReportStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.user.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "kpi_reports",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_kpi_reports_strategy_kpi_period",
                columnNames = {"bsc_strategy_id", "department_kpi_id", "reporting_period"}
        )
)
public class KpiReport extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_kpi_id", nullable = false)
    private DepartmentKpi departmentKpi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_strategic_objective_id", nullable = false)
    private FinalStrategicObjective finalStrategicObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "reporting_period", nullable = false, length = 50)
    private String reportingPeriod;

    @Column(name = "actual_value", nullable = false, precision = 18, scale = 3)
    private BigDecimal actualValue;

    @Column(name = "completion_rate", nullable = false, precision = 8, scale = 3)
    private BigDecimal completionRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_color", nullable = false, length = 50)
    private KpiStatusColor statusColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "achievement_status", nullable = false, length = 50)
    private KpiAchievementStatus achievementStatus;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "evidence_url", columnDefinition = "TEXT")
    private String evidenceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Employee reporter;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false, length = 50)
    private KpiReportStatus reviewStatus;

    @Column(name = "review_note", columnDefinition = "TEXT")
    private String reviewNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private Employee reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
