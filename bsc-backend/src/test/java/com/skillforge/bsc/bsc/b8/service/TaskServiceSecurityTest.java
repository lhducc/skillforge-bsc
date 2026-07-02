package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.auth.security.CurrentUser;
import com.skillforge.bsc.auth.security.CurrentUserProvider;
import com.skillforge.bsc.bsc.b8.dto.request.UpdateTaskStatusRequest;
import com.skillforge.bsc.bsc.b8.dto.response.KanbanResponse;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.mapper.TaskMapper;
import com.skillforge.bsc.bsc.b8.repository.ActionPlanRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskCommentRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskDependencyRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.enums.TaskStatus;
import com.skillforge.bsc.common.enums.UserRole;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceSecurityTest {

    @Mock private B8ValidationService validationService;
    @Mock private ActionPlanService actionPlanService;
    @Mock private ActionPlanRepository actionPlanRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private TaskDependencyRepository taskDependencyRepository;
    @Mock private TaskCommentRepository taskCommentRepository;
    @Mock private TaskMapper taskMapper;
    @Mock private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private TaskService taskService;

    @Test
    void employeeCannotUpdateAnotherEmployeesTask() {
        UUID currentEmployeeId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = taskAssignedTo(UUID.randomUUID());
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(currentUserProvider.getCurrentUser()).thenReturn(Optional.of(employeeUser(currentEmployeeId)));

        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setNewStatus(TaskStatus.IN_PROGRESS);

        assertThatThrownBy(() -> taskService.updateStatus(taskId, request))
                .isInstanceOfSatisfying(BusinessException.class,
                        ex -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.AUTH_ACCESS_DENIED));
    }

    @Test
    void employeeKanbanIsForcedToCurrentEmployee() {
        UUID currentEmployeeId = UUID.randomUUID();
        UUID strategyId = UUID.randomUUID();
        when(currentUserProvider.getCurrentUser()).thenReturn(Optional.of(employeeUser(currentEmployeeId)));
        when(taskRepository.findByBscStrategy_IdOrderByDueDateAscCreatedAtAsc(strategyId)).thenReturn(List.of());

        KanbanResponse response = taskService.getKanban(strategyId, null, null, null, null);

        assertThat(response.getAssigneeId()).isEqualTo(currentEmployeeId);
    }

    private Task taskAssignedTo(UUID assigneeId) {
        BscStrategy strategy = new BscStrategy();
        strategy.setId(UUID.randomUUID());
        Employee assignee = new Employee();
        assignee.setId(assigneeId);
        Task task = new Task();
        task.setBscStrategy(strategy);
        task.setAssignee(assignee);
        task.setStatus(TaskStatus.TODO);
        return task;
    }

    private CurrentUser employeeUser(UUID employeeId) {
        return new CurrentUser(UUID.randomUUID(), employeeId, UUID.randomUUID(), UUID.randomUUID(),
                "employee@company.com", "Employee", UserRole.EMPLOYEE);
    }
}
