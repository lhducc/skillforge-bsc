package com.skillforge.bsc.bsc.b8.dto.request;

import com.skillforge.bsc.common.enums.ActionPlanStatus;
import com.skillforge.bsc.common.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateActionPlanRequest {

    @NotNull
    private UUID bscStrategyId;

    @NotNull
    private UUID departmentKpiId;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private UUID ownerId;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private ActionPlanStatus status;
}
