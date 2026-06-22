package com.skillforge.bsc.bsc.b5.entity;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import com.skillforge.bsc.department.entity.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "department_kpis")
public class DepartmentKpi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_strategic_objective_id", nullable = false)
    private FinalStrategicObjective finalStrategicObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_participation_id", nullable = false)
    private DepartmentParticipation departmentParticipation;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DepartmentKpiStatus status;

    @Column(name = "created_by")
    private UUID createdBy;
}
