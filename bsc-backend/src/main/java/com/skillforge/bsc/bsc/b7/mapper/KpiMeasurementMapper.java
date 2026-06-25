package com.skillforge.bsc.bsc.b7.mapper;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import com.skillforge.bsc.bsc.b7.dto.response.KpiMeasurementDetailResponse;
import com.skillforge.bsc.bsc.b7.dto.response.KpiMeasurementResponse;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementObjectiveResponse;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementPerspectiveResponse;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KpiMeasurementMapper {

    public KpiMeasurementResponse toKpiResponse(KpiWeight kpiWeight, KpiMeasurement measurement, boolean valid) {
        DepartmentKpi kpi = kpiWeight.getDepartmentKpi();
        FinalStrategicObjective finalObjective = kpiWeight.getFinalStrategicObjective();
        return KpiMeasurementResponse.builder()
                .departmentKpiId(kpi.getId())
                .name(kpi.getName())
                .description(kpi.getDescription())
                .departmentId(kpiWeight.getDepartment().getId())
                .departmentName(kpiWeight.getDepartment().getName())
                .departmentCode(kpiWeight.getDepartment().getCode())
                .departmentColor(kpiWeight.getDepartment().getColor())
                .finalStrategicObjectiveId(finalObjective.getId())
                .perspectiveCode(kpiWeight.getPerspectiveCode())
                .weightPercent(kpiWeight.getWeightPercent())
                .measurementConfigured(measurement != null)
                .valid(valid)
                .measurement(toMeasurementDetailResponse(measurement))
                .build();
    }

    public MeasurementObjectiveResponse toObjectiveResponse(
            FinalStrategicObjective finalObjective,
            boolean valid,
            List<KpiMeasurementResponse> departmentKpis
    ) {
        return MeasurementObjectiveResponse.builder()
                .finalStrategicObjectiveId(finalObjective.getId())
                .name(finalObjective.getName())
                .description(finalObjective.getDescription())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .displayOrder(finalObjective.getDisplayOrder())
                .valid(valid)
                .departmentKpis(departmentKpis)
                .build();
    }

    public MeasurementPerspectiveResponse toPerspectiveResponse(
            BscPerspectiveCode perspectiveCode,
            boolean valid,
            List<MeasurementObjectiveResponse> objectives
    ) {
        return MeasurementPerspectiveResponse.builder()
                .perspectiveCode(perspectiveCode)
                .valid(valid)
                .objectives(objectives)
                .build();
    }

    public KpiMeasurementDetailResponse toMeasurementDetailResponse(KpiMeasurement measurement) {
        if (measurement == null) {
            return null;
        }
        return KpiMeasurementDetailResponse.builder()
                .id(measurement.getId())
                .unit(measurement.getUnit())
                .baselineValue(measurement.getBaselineValue())
                .targetValue(measurement.getTargetValue())
                .direction(measurement.getDirection())
                .reportingFrequency(measurement.getReportingFrequency())
                .formulaDescription(measurement.getFormulaDescription())
                .greenThreshold(measurement.getGreenThreshold())
                .yellowThreshold(measurement.getYellowThreshold())
                .redThreshold(measurement.getRedThreshold())
                .reportOwnerId(measurement.getReportOwner() == null ? null : measurement.getReportOwner().getId())
                .reportOwnerName(measurement.getReportOwner() == null ? null : measurement.getReportOwner().getFullName())
                .status(measurement.getStatus())
                .build();
    }
}
