package com.skillforge.bsc.bsc.b8.controller;

import com.skillforge.bsc.bsc.b8.dto.request.CreateTaskRequest;
import com.skillforge.bsc.bsc.b8.dto.request.UpdateTaskStatusRequest;
import com.skillforge.bsc.bsc.b8.dto.response.GanttResponse;
import com.skillforge.bsc.bsc.b8.dto.response.KanbanResponse;
import com.skillforge.bsc.bsc.b8.dto.response.TaskResponse;
import com.skillforge.bsc.bsc.b8.service.TaskService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B8 Tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    @Operation(summary = "Create B8 task")
    public ApiResponse<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        return ApiResponse.success(taskService.create(request));
    }

    @PatchMapping("/tasks/{taskId}/status")
    @Operation(summary = "Update B8 task status")
    public ApiResponse<TaskResponse> updateStatus(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        return ApiResponse.success(taskService.updateStatus(taskId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/tasks/kanban")
    @Operation(summary = "Get B8 Kanban data")
    public ApiResponse<KanbanResponse> getKanban(
            @PathVariable UUID strategyId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID assigneeId,
            @RequestParam(required = false) UUID actionPlanId,
            @RequestParam(required = false) UUID departmentKpiId
    ) {
        return ApiResponse.success(taskService.getKanban(strategyId, departmentId, assigneeId, actionPlanId, departmentKpiId));
    }

    @GetMapping("/bsc-strategies/{strategyId}/tasks/gantt")
    @Operation(summary = "Get B8 Gantt data")
    public ApiResponse<GanttResponse> getGantt(
            @PathVariable UUID strategyId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID actionPlanId,
            @RequestParam(required = false) UUID departmentKpiId
    ) {
        return ApiResponse.success(taskService.getGantt(strategyId, departmentId, actionPlanId, departmentKpiId));
    }
}
