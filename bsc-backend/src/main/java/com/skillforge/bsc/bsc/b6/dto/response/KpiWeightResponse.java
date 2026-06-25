package com.skillforge.bsc.bsc.b6.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class KpiWeightResponse {

    private UUID departmentKpiId;
    private String name;
    private String description;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentColor;
    private UUID finalStrategicObjectiveId;
    private BscPerspectiveCode perspectiveCode;
    private BigDecimal weightPercent;
}
