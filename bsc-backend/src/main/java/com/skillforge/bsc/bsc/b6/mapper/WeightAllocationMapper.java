package com.skillforge.bsc.bsc.b6.mapper;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b6.dto.response.KpiWeightResponse;
import com.skillforge.bsc.bsc.b6.dto.response.ObjectiveWeightResponse;
import com.skillforge.bsc.bsc.b6.dto.response.PerspectiveWeightResponse;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class WeightAllocationMapper {

    public KpiWeightResponse toKpiResponse(DepartmentKpi kpi, BigDecimal weightPercent) {
        FinalStrategicObjective finalObjective = kpi.getFinalStrategicObjective();
        return KpiWeightResponse.builder()
                .departmentKpiId(kpi.getId())
                .name(kpi.getName())
                .description(kpi.getDescription())
                .departmentId(kpi.getDepartment().getId())
                .departmentName(kpi.getDepartment().getName())
                .departmentCode(kpi.getDepartment().getCode())
                .departmentColor(kpi.getDepartment().getColor())
                .finalStrategicObjectiveId(finalObjective.getId())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .weightPercent(weightPercent)
                .build();
    }

    public ObjectiveWeightResponse toObjectiveResponse(
            FinalStrategicObjective finalObjective,
            BigDecimal weightPercent,
            BigDecimal allocatedKpiWeightPercent,
            BigDecimal remainingKpiWeightPercent,
            boolean valid,
            List<KpiWeightResponse> departmentKpis
    ) {
        return ObjectiveWeightResponse.builder()
                .finalStrategicObjectiveId(finalObjective.getId())
                .name(finalObjective.getName())
                .description(finalObjective.getDescription())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .displayOrder(finalObjective.getDisplayOrder())
                .weightPercent(weightPercent)
                .allocatedKpiWeightPercent(allocatedKpiWeightPercent)
                .remainingKpiWeightPercent(remainingKpiWeightPercent)
                .valid(valid)
                .departmentKpis(departmentKpis)
                .build();
    }

    public PerspectiveWeightResponse toPerspectiveResponse(
            BscPerspectiveCode perspectiveCode,
            BigDecimal weightPercent,
            BigDecimal allocatedObjectiveWeightPercent,
            BigDecimal remainingObjectiveWeightPercent,
            boolean valid,
            List<ObjectiveWeightResponse> objectives
    ) {
        return PerspectiveWeightResponse.builder()
                .perspectiveCode(perspectiveCode)
                .weightPercent(weightPercent)
                .allocatedObjectiveWeightPercent(allocatedObjectiveWeightPercent)
                .remainingObjectiveWeightPercent(remainingObjectiveWeightPercent)
                .valid(valid)
                .objectives(objectives)
                .build();
    }
}
