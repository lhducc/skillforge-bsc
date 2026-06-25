package com.skillforge.bsc.bsc.b8.dto.request;

import com.skillforge.bsc.common.enums.KpiReportStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewKpiReportRequest {

    @NotNull
    private KpiReportStatus reviewStatus;

    private String note;

    private UUID reviewerId;
}
