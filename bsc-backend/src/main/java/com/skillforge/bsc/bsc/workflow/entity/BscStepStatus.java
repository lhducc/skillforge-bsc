package com.skillforge.bsc.bsc.workflow.entity;

import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "bsc_step_statuses",
        uniqueConstraints = @UniqueConstraint(name = "uk_bsc_step_statuses_strategy_step", columnNames = {"bsc_strategy_id", "step_code"})
)
public class BscStepStatus extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_code", nullable = false, length = 100)
    private BscStepCode stepCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BscStepStatusValue status;

    @Column(name = "completed_by")
    private UUID completedBy;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "invalidated_reason", columnDefinition = "TEXT")
    private String invalidatedReason;
}
