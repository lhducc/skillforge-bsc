package com.skillforge.bsc.bsc.strategy.entity;

import com.skillforge.bsc.common.entity.BaseEntity;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.company.entity.Company;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "bsc_strategies")
public class BscStrategy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BscStrategyStatus status;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;
}
