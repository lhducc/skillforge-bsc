package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.auth.security.CurrentUser;
import com.skillforge.bsc.auth.security.CurrentUserProvider;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.dto.request.CreateTaskRequest;
import com.skillforge.bsc.bsc.b8.dto.request.UpdateTaskStatusRequest;
import com.skillforge.bsc.bsc.b8.dto.response.GanttActionPlanResponse;
import com.skillforge.bsc.bsc.b8.dto.response.GanttDependencyResponse;
import com.skillforge.bsc.bsc.b8.dto.response.GanttResponse;
import com.skillforge.bsc.bsc.b8.dto.response.GanttTaskResponse;
import com.skillforge.bsc.bsc.b8.dto.response.KanbanColumnResponse;
import com.skillforge.bsc.bsc.b8.dto.response.KanbanResponse;
import com.skillforge.bsc.bsc.b8.dto.response.TaskResponse;
import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.entity.TaskComment;
import com.skillforge.bsc.bsc.b8.entity.TaskDependency;
import com.skillforge.bsc.bsc.b8.mapper.TaskMapper;
import com.skillforge.bsc.bsc.b8.repository.ActionPlanRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskCommentRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskDependencyRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskRepository;
import com.skillforge.bsc.common.enums.TaskDependencyType;
import com.skillforge.bsc.common.enums.TaskStatus;
import com.skillforge.bsc.common.enums.UserRole;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private static final BigDecimal ZERO = new BigDecimal("0.000");
    private static final BigDecimal FIFTY = new BigDecimal("50.000");
    private static final BigDecimal EIGHTY = new BigDecimal("80.000");
    private static final BigDecimal HUNDRED = new BigDecimal("100.000");

    private final B8ValidationService validationService;
    private final ActionPlanService actionPlanService;
    private final ActionPlanRepository actionPlanRepository;
    private final TaskRepository taskRepository;
    private final TaskDependencyRepository taskDependencyRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final TaskMapper taskMapper;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        ActionPlan actionPlan = actionPlanService.getActionPlan(request.getActionPlanId());
        validationService.ensureB8Accessible(actionPlan.getBscStrategy().getId());
        KpiMeasurement measurement = validationService.getMeasuredKpi(
                actionPlan.getBscStrategy().getId(),
                actionPlan.getDepartmentKpi().getId()
        );
        validateActionPlanTrace(actionPlan, measurement);

        String name = validationService.normalizeRequired(
                request.getName(),
                ErrorCode.B8_TASK_NAME_REQUIRED,
                "Task name must not be blank"
        );
        validateDateRange(request.getStartDate(), request.getDueDate());
        Employee assignee = validationService.getValidEmployeeInDepartment(
                actionPlan.getBscStrategy(),
                actionPlan.getDepartment(),
                request.getAssigneeId(),
                ErrorCode.B8_TASK_ASSIGNEE_INVALID
        );

        TaskStatus status = request.getStatus() == null ? TaskStatus.TODO : request.getStatus();
        String blockReason = validateBlockReason(status, request.getBlockReason());

        Task task = new Task();
        task.setBscStrategy(actionPlan.getBscStrategy());
        task.setActionPlan(actionPlan);
        task.setDepartmentKpi(actionPlan.getDepartmentKpi());
        task.setFinalStrategicObjective(actionPlan.getFinalStrategicObjective());
        task.setDepartment(actionPlan.getDepartment());
        task.setAssignee(assignee);
        task.setName(name);
        task.setDescription(validationService.normalize(request.getDescription()));
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setStatus(status);
        task.setProgressPercent(resolveProgress(status, request.getProgressPercent(), null));
        task.setPriority(request.getPriority());
        task.setBlockReason(blockReason);
        task.setEvidenceUrl(validationService.normalize(request.getEvidenceUrl()));
        task.setCreatedBy(null);
        task.setUpdatedBy(null);

        Task savedTask = taskRepository.save(task);
        createDependencies(savedTask, request.getDependencyTaskIds());
        return taskMapper.toResponse(savedTask, isOverdue(savedTask));
    }

    @Transactional
    public TaskResponse updateStatus(UUID taskId, UpdateTaskStatusRequest request) {
        Task task = getTask(taskId);
        validationService.ensureB8Accessible(task.getBscStrategy().getId());
        CurrentUser currentUser = currentUserProvider.getCurrentUser().orElse(null);
        enforceEmployeeAssignment(currentUser, task);
        TaskStatus oldStatus = task.getStatus();
        TaskStatus newStatus = request.getNewStatus();
        validateTransition(oldStatus, newStatus);

        String blockReason = validateBlockReason(newStatus, request.getBlockReason());
        UUID actorEmployeeId = currentUser != null && currentUser.role() == UserRole.EMPLOYEE
                ? currentUser.employeeId()
                : request.getActorEmployeeId();
        Employee actor = actorEmployeeId == null
                ? null
                : validationService.getValidEmployeeInDepartment(
                        task.getBscStrategy(),
                        task.getDepartment(),
                        actorEmployeeId,
                        ErrorCode.B8_TASK_ASSIGNEE_INVALID
                );

        BigDecimal progress = resolveProgress(newStatus, request.getProgressPercent(), task.getProgressPercent());
        task.setStatus(newStatus);
        task.setProgressPercent(progress);
        task.setBlockReason(newStatus == TaskStatus.BLOCKED ? blockReason : null);
        String evidenceUrl = validationService.normalize(request.getEvidenceUrl());
        if (evidenceUrl != null) {
            task.setEvidenceUrl(evidenceUrl);
        }
        task.setUpdatedBy(actor == null ? null : actor.getId());

        Task savedTask = taskRepository.save(task);
        saveStatusComment(savedTask, actor, oldStatus, newStatus, progress, request.getComment(), blockReason);
        return taskMapper.toResponse(savedTask, isOverdue(savedTask));
    }

    @Transactional(readOnly = true)
    public KanbanResponse getKanban(
            UUID strategyId,
            UUID departmentId,
            UUID assigneeId,
            UUID actionPlanId,
            UUID departmentKpiId
    ) {
        validationService.getStrategy(strategyId);
        UUID effectiveAssigneeId = resolveEmployeeAssigneeFilter(assigneeId);
        Map<TaskStatus, List<TaskResponse>> tasksByStatus = filteredTasks(
                strategyId,
                departmentId,
                effectiveAssigneeId,
                actionPlanId,
                departmentKpiId
        ).stream()
                .map(task -> taskMapper.toResponse(task, isOverdue(task)))
                .collect(Collectors.groupingBy(TaskResponse::getStatus));

        List<KanbanColumnResponse> columns = Arrays.stream(TaskStatus.values())
                .map(status -> {
                    List<TaskResponse> tasks = tasksByStatus.getOrDefault(status, List.of());
                    return KanbanColumnResponse.builder()
                            .status(status)
                            .total(tasks.size())
                            .tasks(tasks)
                            .build();
                })
                .toList();

        return KanbanResponse.builder()
                .bscStrategyId(strategyId)
                .departmentId(departmentId)
                .assigneeId(effectiveAssigneeId)
                .actionPlanId(actionPlanId)
                .departmentKpiId(departmentKpiId)
                .columns(columns)
                .build();
    }

    @Transactional(readOnly = true)
    public GanttResponse getGantt(UUID strategyId, UUID departmentId, UUID actionPlanId, UUID departmentKpiId) {
        validationService.getStrategy(strategyId);
        List<Task> tasks = filteredTasks(strategyId, departmentId, null, actionPlanId, departmentKpiId);
        Set<UUID> actionPlanIds = tasks.stream()
                .map(task -> task.getActionPlan().getId())
                .collect(Collectors.toSet());
        if (actionPlanId != null) {
            actionPlanIds.add(actionPlanId);
        }

        List<ActionPlan> actionPlans = actionPlanRepository.findByBscStrategy_IdOrderByStartDateAscCreatedAtAsc(strategyId)
                .stream()
                .filter(actionPlan -> actionPlanIds.contains(actionPlan.getId()))
                .filter(actionPlan -> departmentId == null || actionPlan.getDepartment().getId().equals(departmentId))
                .filter(actionPlan -> departmentKpiId == null || actionPlan.getDepartmentKpi().getId().equals(departmentKpiId))
                .toList();

        Map<UUID, List<Task>> tasksByActionPlan = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getActionPlan().getId()));

        List<GanttActionPlanResponse> actionPlanResponses = actionPlans.stream()
                .map(actionPlan -> toGanttActionPlanResponse(
                        actionPlan,
                        tasksByActionPlan.getOrDefault(actionPlan.getId(), List.of())
                ))
                .toList();

        Set<UUID> taskIds = tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toSet());
        List<GanttDependencyResponse> dependencies = taskIds.isEmpty()
                ? List.of()
                : taskDependencyRepository.findByTargetTask_IdIn(taskIds)
                        .stream()
                        .filter(dependency -> taskIds.contains(dependency.getSourceTask().getId()))
                        .map(taskMapper::toDependencyResponse)
                        .toList();

        return GanttResponse.builder()
                .bscStrategyId(strategyId)
                .departmentId(departmentId)
                .actionPlanId(actionPlanId)
                .departmentKpiId(departmentKpiId)
                .actionPlans(actionPlanResponses)
                .dependencies(dependencies)
                .build();
    }

    @Transactional(readOnly = true)
    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_TASK_NOT_FOUND));
    }

    private List<Task> filteredTasks(
            UUID strategyId,
            UUID departmentId,
            UUID assigneeId,
            UUID actionPlanId,
            UUID departmentKpiId
    ) {
        return taskRepository.findByBscStrategy_IdOrderByDueDateAscCreatedAtAsc(strategyId)
                .stream()
                .filter(task -> departmentId == null || task.getDepartment().getId().equals(departmentId))
                .filter(task -> assigneeId == null || task.getAssignee().getId().equals(assigneeId))
                .filter(task -> actionPlanId == null || task.getActionPlan().getId().equals(actionPlanId))
                .filter(task -> departmentKpiId == null || task.getDepartmentKpi().getId().equals(departmentKpiId))
                .toList();
    }

    private void enforceEmployeeAssignment(CurrentUser currentUser, Task task) {
        if (currentUser != null
                && currentUser.role() == UserRole.EMPLOYEE
                && !task.getAssignee().getId().equals(currentUser.employeeId())) {
            throw new BusinessException(ErrorCode.AUTH_ACCESS_DENIED);
        }
    }

    private UUID resolveEmployeeAssigneeFilter(UUID requestedAssigneeId) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser().orElse(null);
        if (currentUser == null || currentUser.role() != UserRole.EMPLOYEE) {
            return requestedAssigneeId;
        }
        if (requestedAssigneeId != null && !requestedAssigneeId.equals(currentUser.employeeId())) {
            throw new BusinessException(ErrorCode.AUTH_ACCESS_DENIED);
        }
        return currentUser.employeeId();
    }

    private void createDependencies(Task targetTask, List<UUID> dependencyTaskIds) {
        if (dependencyTaskIds == null || dependencyTaskIds.isEmpty()) {
            return;
        }
        Set<UUID> uniqueDependencyIds = new HashSet<>(dependencyTaskIds);
        if (uniqueDependencyIds.size() != dependencyTaskIds.size()) {
            throw new BusinessException(ErrorCode.DUPLICATED_RESOURCE, "Dependency task IDs must not be duplicated");
        }

        for (UUID dependencyTaskId : uniqueDependencyIds) {
            if (targetTask.getId().equals(dependencyTaskId)) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Task cannot depend on itself");
            }
            Task sourceTask = getTask(dependencyTaskId);
            if (!sourceTask.getActionPlan().getId().equals(targetTask.getActionPlan().getId())) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Task dependency must stay within the same action plan in MVP");
            }
            if (taskDependencyRepository.existsBySourceTask_IdAndTargetTask_Id(sourceTask.getId(), targetTask.getId())) {
                throw new BusinessException(ErrorCode.DUPLICATED_RESOURCE, "Task dependency is duplicated");
            }
            TaskDependency dependency = new TaskDependency();
            dependency.setSourceTask(sourceTask);
            dependency.setTargetTask(targetTask);
            dependency.setDependencyType(TaskDependencyType.FINISH_TO_START);
            taskDependencyRepository.save(dependency);
        }
    }

    private GanttActionPlanResponse toGanttActionPlanResponse(ActionPlan actionPlan, List<Task> tasks) {
        List<GanttTaskResponse> taskResponses = tasks.stream()
                .map(task -> taskMapper.toGanttTaskResponse(task, isOverdue(task)))
                .toList();
        return GanttActionPlanResponse.builder()
                .id(actionPlan.getId())
                .departmentKpiId(actionPlan.getDepartmentKpi().getId())
                .departmentKpiName(actionPlan.getDepartmentKpi().getName())
                .departmentId(actionPlan.getDepartment().getId())
                .departmentName(actionPlan.getDepartment().getName())
                .name(actionPlan.getName())
                .startDate(actionPlan.getStartDate())
                .endDate(actionPlan.getEndDate())
                .ownerId(actionPlan.getOwner().getId())
                .ownerName(actionPlan.getOwner().getFullName())
                .priority(actionPlan.getPriority())
                .status(actionPlan.getStatus())
                .progressPercent(calculateActionPlanProgress(tasks))
                .tasks(taskResponses)
                .build();
    }

    private BigDecimal calculateActionPlanProgress(List<Task> tasks) {
        List<Task> validTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED)
                .toList();
        if (validTasks.isEmpty()) {
            return ZERO;
        }
        long doneCount = validTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .count();
        return BigDecimal.valueOf(doneCount)
                .multiply(HUNDRED)
                .divide(BigDecimal.valueOf(validTasks.size()), 3, RoundingMode.HALF_UP);
    }

    private void validateActionPlanTrace(ActionPlan actionPlan, KpiMeasurement measurement) {
        if (!actionPlan.getBscStrategy().getId().equals(measurement.getBscStrategy().getId())
                || !actionPlan.getDepartmentKpi().getId().equals(measurement.getDepartmentKpi().getId())
                || !actionPlan.getFinalStrategicObjective().getId().equals(measurement.getFinalStrategicObjective().getId())
                || !actionPlan.getDepartment().getId().equals(measurement.getDepartment().getId())) {
            throw new BusinessException(ErrorCode.B8_TRACEABILITY_INVALID);
        }
    }

    private void validateDateRange(LocalDate startDate, LocalDate dueDate) {
        if (startDate == null || dueDate == null || startDate.isAfter(dueDate)) {
            throw new BusinessException(ErrorCode.B8_TASK_DATE_INVALID);
        }
    }

    private String validateBlockReason(TaskStatus status, String value) {
        String normalized = validationService.normalize(value);
        if (status == TaskStatus.BLOCKED && normalized == null) {
            throw new BusinessException(ErrorCode.B8_BLOCK_REASON_REQUIRED);
        }
        return normalized;
    }

    private void validateTransition(TaskStatus oldStatus, TaskStatus newStatus) {
        if (oldStatus == TaskStatus.CANCELLED || !isAllowedTransition(oldStatus, newStatus)) {
            throw new BusinessException(ErrorCode.B8_TASK_INVALID_STATUS_TRANSITION);
        }
    }

    private boolean isAllowedTransition(TaskStatus oldStatus, TaskStatus newStatus) {
        return (oldStatus == TaskStatus.TODO && (newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.CANCELLED))
                || (oldStatus == TaskStatus.IN_PROGRESS && (newStatus == TaskStatus.REVIEW
                || newStatus == TaskStatus.BLOCKED
                || newStatus == TaskStatus.CANCELLED))
                || (oldStatus == TaskStatus.REVIEW && (newStatus == TaskStatus.DONE
                || newStatus == TaskStatus.IN_PROGRESS
                || newStatus == TaskStatus.CANCELLED))
                || (oldStatus == TaskStatus.BLOCKED && newStatus == TaskStatus.IN_PROGRESS);
    }

    private BigDecimal resolveProgress(TaskStatus status, BigDecimal requestedProgress, BigDecimal currentProgress) {
        if (requestedProgress != null) {
            return requestedProgress.setScale(3, RoundingMode.HALF_UP);
        }
        if (status == TaskStatus.IN_PROGRESS) {
            return FIFTY;
        }
        if (status == TaskStatus.REVIEW) {
            return EIGHTY;
        }
        if (status == TaskStatus.DONE) {
            return HUNDRED;
        }
        if (status == TaskStatus.BLOCKED || status == TaskStatus.CANCELLED) {
            return currentProgress == null ? ZERO : currentProgress;
        }
        return ZERO;
    }

    private void saveStatusComment(
            Task task,
            Employee actor,
            TaskStatus oldStatus,
            TaskStatus newStatus,
            BigDecimal progress,
            String comment,
            String blockReason
    ) {
        TaskComment taskComment = new TaskComment();
        taskComment.setTask(task);
        taskComment.setActorEmployee(actor);
        taskComment.setContent(validationService.normalize(comment) == null
                ? blockReason
                : validationService.normalize(comment));
        taskComment.setOldStatus(oldStatus);
        taskComment.setNewStatus(newStatus);
        taskComment.setProgressPercent(progress);
        taskCommentRepository.save(taskComment);
    }

    private boolean isOverdue(Task task) {
        return task.getDueDate().isBefore(LocalDate.now())
                && task.getStatus() != TaskStatus.DONE
                && task.getStatus() != TaskStatus.CANCELLED;
    }
}
