package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.TaskDependencyType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GanttDependencyResponse {

    private UUID id;
    private UUID sourceTaskId;
    private UUID targetTaskId;
    private TaskDependencyType dependencyType;
}
