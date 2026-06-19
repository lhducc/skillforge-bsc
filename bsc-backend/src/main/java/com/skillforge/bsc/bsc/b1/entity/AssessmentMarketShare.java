package com.skillforge.bsc.bsc.b1.entity;

import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.AssessmentMarketSharePeriodType;
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

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "assessment_market_shares")
public class AssessmentMarketShare extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bsc_strategy_id", nullable = false)
    private BscStrategy bscStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false, length = 50)
    private AssessmentMarketSharePeriodType periodType;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "market_share_percent", nullable = false, precision = 6, scale = 3)
    private BigDecimal marketSharePercent;

    @Column(name = "is_own_company", nullable = false)
    private Boolean ownCompany;

    @Column(name = "display_order")
    private Integer displayOrder;
}
