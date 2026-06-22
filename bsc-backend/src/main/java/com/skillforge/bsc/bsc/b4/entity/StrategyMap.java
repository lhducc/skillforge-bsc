package com.skillforge.bsc.bsc.b4.entity;

import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.StrategyMapStatus;
import com.skillforge.bsc.common.enums.StrategyMapType;
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

@Getter
@Setter
@Entity
@Table(name = "strategy_maps")
public class StrategyMap extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_strategy_id")
    private SelectedStrategy selectedStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "map_type", nullable = false, length = 50)
    private StrategyMapType mapType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StrategyMapStatus status;
}
