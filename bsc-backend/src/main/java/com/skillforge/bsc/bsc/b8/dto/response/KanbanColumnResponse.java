package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KanbanColumnResponse {

    private TaskStatus status;
    private int total;
    private List<TaskResponse> tasks;
}
