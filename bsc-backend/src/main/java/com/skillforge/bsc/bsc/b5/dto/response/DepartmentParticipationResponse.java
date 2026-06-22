package com.skillforge.bsc.bsc.b5.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.DepartmentParticipationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DepartmentParticipationResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID finalStrategicObjectiveId;
    private String finalStrategicObjectiveName;
    private BscPerspectiveCode perspectiveCode;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentColor;
    private UUID departmentHeadId;
    private String departmentHeadName;
    private DepartmentParticipationStatus status;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
