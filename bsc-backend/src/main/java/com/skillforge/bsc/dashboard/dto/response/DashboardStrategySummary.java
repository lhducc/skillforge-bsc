package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.BscStrategyStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DashboardStrategySummary {
    private UUID strategyId;
    private String strategyName;
    private Integer year;
    private BscStrategyStatus strategyStatus;
    private UUID companyId;
    private String companyName;
}
