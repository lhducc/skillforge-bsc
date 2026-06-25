package com.skillforge.bsc.bsc.b6.entity;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.department.entity.Department;
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
        name = "kpi_weights",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_kpi_weights_strategy_kpi",
                columnNames = {"bsc_strategy_id", "department_kpi_id"}
        )
)
public class KpiWeight extends BaseEntity {

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

    @Column(name = "weight_percent", nullable = false, precision = 6, scale = 3)
    private BigDecimal weightPercent;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;
}
