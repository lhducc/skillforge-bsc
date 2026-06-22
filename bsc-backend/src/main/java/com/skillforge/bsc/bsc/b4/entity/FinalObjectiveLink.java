package com.skillforge.bsc.bsc.b4.entity;

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

@Getter
@Setter
@Entity
@Table(
        name = "final_objective_links",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_final_objective_links_strategy_source_target",
                columnNames = {"bsc_strategy_id", "source_final_objective_id", "target_final_objective_id"}
        )
)
public class FinalObjectiveLink extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_final_objective_id", nullable = false)
    private FinalStrategicObjective sourceFinalObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_final_objective_id", nullable = false)
    private FinalStrategicObjective targetFinalObjective;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "display_order")
    private Integer displayOrder;
}
