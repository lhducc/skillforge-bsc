package com.skillforge.bsc.bsc.b6.entity;

import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
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
        name = "perspective_weights",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_perspective_weights_strategy_perspective",
                columnNames = {"bsc_strategy_id", "perspective_code"}
        )
)
public class PerspectiveWeight extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

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
