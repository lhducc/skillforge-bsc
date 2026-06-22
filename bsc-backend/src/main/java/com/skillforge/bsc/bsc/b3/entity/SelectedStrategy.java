package com.skillforge.bsc.bsc.b3.entity;

import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
        name = "selected_strategies",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_selected_strategies_strategy_candidate",
                        columnNames = {"bsc_strategy_id", "candidate_strategy_id"}
                ),
                @UniqueConstraint(
                        name = "uk_selected_strategies_strategy_priority",
                        columnNames = {"bsc_strategy_id", "priority_order"}
                )
        }
)
public class SelectedStrategy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_strategy_id", nullable = false)
    private CandidateStrategy candidateStrategy;

    @Column(name = "priority_order", nullable = false)
    private Integer priorityOrder;

    @Column(name = "selection_reason", columnDefinition = "TEXT")
    private String selectionReason;

    @Column(name = "selected_by")
    private UUID selectedBy;

    @Column(name = "selected_at", nullable = false)
    private LocalDateTime selectedAt;
}
