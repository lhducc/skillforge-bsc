package com.skillforge.bsc.bsc.b5.mapper;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentKpiResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentParticipationResponse;
import com.skillforge.bsc.bsc.b5.dto.response.FishboneObjectiveResponse;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.entity.DepartmentParticipation;
import com.skillforge.bsc.user.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FishboneMapper {

    public DepartmentParticipationResponse toParticipationResponse(DepartmentParticipation participation) {
        FinalStrategicObjective finalObjective = participation.getFinalStrategicObjective();
        Employee departmentHead = participation.getDepartmentHead();
        return DepartmentParticipationResponse.builder()
                .id(participation.getId())
                .bscStrategyId(participation.getBscStrategy().getId())
                .finalStrategicObjectiveId(finalObjective.getId())
                .finalStrategicObjectiveName(finalObjective.getName())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .departmentId(participation.getDepartment().getId())
                .departmentName(participation.getDepartment().getName())
                .departmentCode(participation.getDepartment().getCode())
                .departmentColor(participation.getDepartment().getColor())
                .departmentHeadId(departmentHead == null ? null : departmentHead.getId())
                .departmentHeadName(departmentHead == null ? null : departmentHead.getFullName())
                .status(participation.getStatus())
                .createdBy(participation.getCreatedBy())
                .createdAt(participation.getCreatedAt())
                .updatedAt(participation.getUpdatedAt())
                .build();
    }

    public DepartmentKpiResponse toKpiResponse(DepartmentKpi kpi) {
        FinalStrategicObjective finalObjective = kpi.getFinalStrategicObjective();
        return DepartmentKpiResponse.builder()
                .id(kpi.getId())
                .bscStrategyId(kpi.getBscStrategy().getId())
                .finalStrategicObjectiveId(finalObjective.getId())
                .finalStrategicObjectiveName(finalObjective.getName())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .departmentId(kpi.getDepartment().getId())
                .departmentName(kpi.getDepartment().getName())
                .departmentCode(kpi.getDepartment().getCode())
                .departmentColor(kpi.getDepartment().getColor())
                .departmentParticipationId(kpi.getDepartmentParticipation().getId())
                .name(kpi.getName())
                .description(kpi.getDescription())
                .displayOrder(kpi.getDisplayOrder())
                .status(kpi.getStatus())
                .createdBy(kpi.getCreatedBy())
                .createdAt(kpi.getCreatedAt())
                .updatedAt(kpi.getUpdatedAt())
                .build();
    }

    public FishboneObjectiveResponse toObjectiveResponse(
            FinalStrategicObjective finalObjective,
            List<DepartmentParticipation> participations,
            List<DepartmentKpi> kpis
    ) {
        return FishboneObjectiveResponse.builder()
                .finalStrategicObjectiveId(finalObjective.getId())
                .name(finalObjective.getName())
                .description(finalObjective.getDescription())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .displayOrder(finalObjective.getDisplayOrder())
                .participations(participations.stream().map(this::toParticipationResponse).toList())
                .departmentKpis(kpis.stream().map(this::toKpiResponse).toList())
                .build();
    }
}
