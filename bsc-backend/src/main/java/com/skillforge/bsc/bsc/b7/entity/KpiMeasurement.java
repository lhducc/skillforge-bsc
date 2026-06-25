package com.skillforge.bsc.bsc.b7.entity;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.KpiDirection;
import com.skillforge.bsc.common.enums.MeasurementStatus;
import com.skillforge.bsc.common.enums.ReportingFrequency;
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
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "kpi_measurements",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_kpi_measurements_strategy_kpi",
                columnNames = {"bsc_strategy_id", "department_kpi_id"}
        )
)
public class KpiMeasurement extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "perspective_code", nullable = false, length = 100)
    private BscPerspectiveCode perspectiveCode;

    @Column(nullable = false, length = 100)
    private String unit;

    @Column(name = "baseline_value", precision = 18, scale = 3)
    private BigDecimal baselineValue;

    @Column(name = "target_value", nullable = false, precision = 18, scale = 3)
    private BigDecimal targetValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private KpiDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "reporting_frequency", nullable = false, length = 50)
    private ReportingFrequency reportingFrequency;

    @Column(name = "formula_description", columnDefinition = "TEXT")
    private String formulaDescription;

    @Column(name = "green_threshold", nullable = false, precision = 6, scale = 3)
    private BigDecimal greenThreshold;

    @Column(name = "yellow_threshold", nullable = false, precision = 6, scale = 3)
    private BigDecimal yellowThreshold;

    @Column(name = "red_threshold", nullable = false, precision = 6, scale = 3)
    private BigDecimal redThreshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_owner_id")
    private Employee reportOwner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MeasurementStatus status;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;
}
