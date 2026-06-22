package com.skillforge.bsc.bsc.b5.entity;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.DepartmentParticipationStatus;
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

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "department_participations",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_department_participations_strategy_objective_department",
                columnNames = {"bsc_strategy_id", "final_strategic_objective_id", "department_id"}
        )
)
public class DepartmentParticipation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_strategic_objective_id", nullable = false)
    private FinalStrategicObjective finalStrategicObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_head_id")
    private Employee departmentHead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DepartmentParticipationStatus status;

    @Column(name = "created_by")
    private UUID createdBy;
}
