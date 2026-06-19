package com.skillforge.bsc.bsc.b1.entity;

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

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
        name = "assessment_financials",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_assessment_financials_strategy_year",
                columnNames = {"bsc_strategy_id", "year"}
        )
)
public class AssessmentFinancial extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal revenue;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal profit;
}
