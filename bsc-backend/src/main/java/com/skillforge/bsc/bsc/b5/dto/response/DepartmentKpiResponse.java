package com.skillforge.bsc.bsc.b5.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DepartmentKpiResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID finalStrategicObjectiveId;
    private String finalStrategicObjectiveName;
    private BscPerspectiveCode perspectiveCode;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentColor;
    private UUID departmentParticipationId;
    private String name;
    private String description;
    private Integer displayOrder;
    private DepartmentKpiStatus status;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
