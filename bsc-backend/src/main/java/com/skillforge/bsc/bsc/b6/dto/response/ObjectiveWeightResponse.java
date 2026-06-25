package com.skillforge.bsc.bsc.b6.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ObjectiveWeightResponse {

    private UUID finalStrategicObjectiveId;
    private String name;
    private String description;
    private BscPerspectiveCode perspectiveCode;
    private Integer displayOrder;
    private BigDecimal weightPercent;
    private BigDecimal allocatedKpiWeightPercent;
    private BigDecimal remainingKpiWeightPercent;
    private boolean valid;
    private List<KpiWeightResponse> departmentKpis;
}
