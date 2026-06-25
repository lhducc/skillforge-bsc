package com.skillforge.bsc.bsc.b8.dto.request;

import com.skillforge.bsc.common.enums.TaskPriority;
import com.skillforge.bsc.common.enums.TaskStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateTaskRequest {

    @NotNull
    private UUID actionPlanId;

    @NotNull
    private UUID assigneeId;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private TaskPriority priority;

    private TaskStatus status;

    @DecimalMin("0.000")
    @DecimalMax("100.000")
    private BigDecimal progressPercent;

    private String blockReason;

    private String evidenceUrl;

    private List<UUID> dependencyTaskIds;
}
