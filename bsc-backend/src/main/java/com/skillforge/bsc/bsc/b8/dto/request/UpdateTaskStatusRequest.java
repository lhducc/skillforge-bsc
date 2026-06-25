package com.skillforge.bsc.bsc.b8.dto.request;

import com.skillforge.bsc.common.enums.TaskStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateTaskStatusRequest {

    @NotNull
    private TaskStatus newStatus;

    @DecimalMin("0.000")
    @DecimalMax("100.000")
    private BigDecimal progressPercent;

    private String comment;

    private String blockReason;

    private String evidenceUrl;

    private UUID actorEmployeeId;
}
