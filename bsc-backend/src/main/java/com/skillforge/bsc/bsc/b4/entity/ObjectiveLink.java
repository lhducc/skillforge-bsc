package com.skillforge.bsc.bsc.b4.entity;

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
        name = "objective_links",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_objective_links_map_source_target",
                columnNames = {"strategy_map_id", "source_objective_id", "target_objective_id"}
        )
)
public class ObjectiveLink extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "strategy_map_id", nullable = false)
    private StrategyMap strategyMap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_objective_id", nullable = false)
    private StrategicObjective sourceObjective;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_objective_id", nullable = false)
    private StrategicObjective targetObjective;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "display_order")
    private Integer displayOrder;
}
