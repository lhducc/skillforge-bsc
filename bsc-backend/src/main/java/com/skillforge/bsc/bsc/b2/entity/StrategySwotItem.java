package com.skillforge.bsc.bsc.b2.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
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
        name = "strategy_swot_items",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_strategy_swot_items_swot_item",
                columnNames = "swot_item_id"
        )
)
public class StrategySwotItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_strategy_id", nullable = false)
    private CandidateStrategy candidateStrategy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "swot_item_id", nullable = false)
    private SwotItem swotItem;
}
