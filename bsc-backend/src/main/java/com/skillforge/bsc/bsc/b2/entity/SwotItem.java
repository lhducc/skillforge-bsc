package com.skillforge.bsc.bsc.b2.entity;

import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.SwotType;
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

@Getter
@Setter
@Entity
@Table(
        name = "swot_items",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_swot_items_strategy_source",
                columnNames = {"bsc_strategy_id", "source_analysis_item_id"}
        )
)
public class SwotItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "swot_type", nullable = false, length = 10)
    private SwotType swotType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_analysis_item_id", nullable = false)
    private AnalysisItem sourceAnalysisItem;

    @Column(name = "content_snapshot", nullable = false, columnDefinition = "TEXT")
    private String contentSnapshot;
}
