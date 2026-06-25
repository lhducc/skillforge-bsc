CREATE TABLE IF NOT EXISTS action_plans (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    department_kpi_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    owner_id UUID NOT NULL,
    priority VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_action_plans_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_action_plans_department_kpi
        FOREIGN KEY (department_kpi_id) REFERENCES department_kpis (id),
    CONSTRAINT fk_action_plans_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_action_plans_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_action_plans_owner
        FOREIGN KEY (owner_id) REFERENCES employees (id),
    CONSTRAINT chk_action_plans_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    CONSTRAINT chk_action_plans_status
        CHECK (status IN ('DRAFT', 'ACTIVE', 'COMPLETED', 'CANCELLED', 'ON_HOLD')),
    CONSTRAINT chk_action_plans_date_range
        CHECK (start_date <= end_date)
);

CREATE INDEX IF NOT EXISTS idx_action_plans_strategy
    ON action_plans (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_action_plans_department_kpi
    ON action_plans (department_kpi_id);

CREATE INDEX IF NOT EXISTS idx_action_plans_department
    ON action_plans (department_id);

CREATE INDEX IF NOT EXISTS idx_action_plans_owner
    ON action_plans (owner_id);

CREATE INDEX IF NOT EXISTS idx_action_plans_status
    ON action_plans (status);

CREATE TABLE IF NOT EXISTS tasks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    action_plan_id UUID NOT NULL,
    department_kpi_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    assignee_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    progress_percent DECIMAL(6,3),
    priority VARCHAR(50) NOT NULL,
    block_reason TEXT,
    evidence_url TEXT,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tasks_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_tasks_action_plan
        FOREIGN KEY (action_plan_id) REFERENCES action_plans (id),
    CONSTRAINT fk_tasks_department_kpi
        FOREIGN KEY (department_kpi_id) REFERENCES department_kpis (id),
    CONSTRAINT fk_tasks_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_tasks_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_tasks_assignee
        FOREIGN KEY (assignee_id) REFERENCES employees (id),
    CONSTRAINT chk_tasks_status
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'REVIEW', 'DONE', 'BLOCKED', 'CANCELLED')),
    CONSTRAINT chk_tasks_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    CONSTRAINT chk_tasks_date_range
        CHECK (start_date <= due_date),
    CONSTRAINT chk_tasks_progress
        CHECK (progress_percent IS NULL OR (progress_percent >= 0 AND progress_percent <= 100)),
    CONSTRAINT chk_tasks_block_reason
        CHECK (status <> 'BLOCKED' OR (block_reason IS NOT NULL AND length(trim(block_reason)) > 0))
);

CREATE INDEX IF NOT EXISTS idx_tasks_strategy
    ON tasks (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_tasks_action_plan
    ON tasks (action_plan_id);

CREATE INDEX IF NOT EXISTS idx_tasks_department_kpi
    ON tasks (department_kpi_id);

CREATE INDEX IF NOT EXISTS idx_tasks_department
    ON tasks (department_id);

CREATE INDEX IF NOT EXISTS idx_tasks_assignee
    ON tasks (assignee_id);

CREATE INDEX IF NOT EXISTS idx_tasks_status
    ON tasks (status);

CREATE INDEX IF NOT EXISTS idx_tasks_due_date
    ON tasks (due_date);

CREATE TABLE IF NOT EXISTS task_dependencies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    source_task_id UUID NOT NULL,
    target_task_id UUID NOT NULL,
    dependency_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_dependencies_source_task
        FOREIGN KEY (source_task_id) REFERENCES tasks (id),
    CONSTRAINT fk_task_dependencies_target_task
        FOREIGN KEY (target_task_id) REFERENCES tasks (id),
    CONSTRAINT uk_task_dependencies_source_target
        UNIQUE (source_task_id, target_task_id),
    CONSTRAINT chk_task_dependencies_type
        CHECK (dependency_type IN ('FINISH_TO_START')),
    CONSTRAINT chk_task_dependencies_not_self
        CHECK (source_task_id <> target_task_id)
);

CREATE INDEX IF NOT EXISTS idx_task_dependencies_source
    ON task_dependencies (source_task_id);

CREATE INDEX IF NOT EXISTS idx_task_dependencies_target
    ON task_dependencies (target_task_id);

CREATE TABLE IF NOT EXISTS task_comments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    task_id UUID NOT NULL,
    actor_employee_id UUID,
    content TEXT,
    old_status VARCHAR(50),
    new_status VARCHAR(50),
    progress_percent DECIMAL(6,3),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_comments_task
        FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT fk_task_comments_actor_employee
        FOREIGN KEY (actor_employee_id) REFERENCES employees (id),
    CONSTRAINT chk_task_comments_old_status
        CHECK (old_status IS NULL OR old_status IN ('TODO', 'IN_PROGRESS', 'REVIEW', 'DONE', 'BLOCKED', 'CANCELLED')),
    CONSTRAINT chk_task_comments_new_status
        CHECK (new_status IS NULL OR new_status IN ('TODO', 'IN_PROGRESS', 'REVIEW', 'DONE', 'BLOCKED', 'CANCELLED')),
    CONSTRAINT chk_task_comments_progress
        CHECK (progress_percent IS NULL OR (progress_percent >= 0 AND progress_percent <= 100))
);

CREATE INDEX IF NOT EXISTS idx_task_comments_task
    ON task_comments (task_id);

CREATE INDEX IF NOT EXISTS idx_task_comments_actor_employee
    ON task_comments (actor_employee_id);

CREATE TABLE IF NOT EXISTS kpi_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    department_kpi_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    reporting_period VARCHAR(50) NOT NULL,
    actual_value DECIMAL(18,3) NOT NULL,
    completion_rate DECIMAL(8,3) NOT NULL,
    status_color VARCHAR(50) NOT NULL,
    achievement_status VARCHAR(50) NOT NULL,
    note TEXT,
    evidence_url TEXT,
    reporter_id UUID,
    review_status VARCHAR(50) NOT NULL,
    review_note TEXT,
    reviewed_by UUID,
    reviewed_at TIMESTAMP,
    submitted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_kpi_reports_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_kpi_reports_department_kpi
        FOREIGN KEY (department_kpi_id) REFERENCES department_kpis (id),
    CONSTRAINT fk_kpi_reports_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_kpi_reports_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_kpi_reports_reporter
        FOREIGN KEY (reporter_id) REFERENCES employees (id),
    CONSTRAINT fk_kpi_reports_reviewer
        FOREIGN KEY (reviewed_by) REFERENCES employees (id),
    CONSTRAINT uk_kpi_reports_strategy_kpi_period
        UNIQUE (bsc_strategy_id, department_kpi_id, reporting_period),
    CONSTRAINT chk_kpi_reports_actual_value
        CHECK (actual_value >= 0),
    CONSTRAINT chk_kpi_reports_status_color
        CHECK (status_color IN ('GREEN', 'YELLOW', 'RED')),
    CONSTRAINT chk_kpi_reports_achievement_status
        CHECK (achievement_status IN ('IN_PROGRESS', 'ACHIEVED', 'EXCEEDED')),
    CONSTRAINT chk_kpi_reports_review_status
        CHECK (review_status IN ('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED'))
);

CREATE INDEX IF NOT EXISTS idx_kpi_reports_strategy
    ON kpi_reports (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_kpi_reports_department_kpi
    ON kpi_reports (department_kpi_id);

CREATE INDEX IF NOT EXISTS idx_kpi_reports_department
    ON kpi_reports (department_id);

CREATE INDEX IF NOT EXISTS idx_kpi_reports_review_status
    ON kpi_reports (review_status);

CREATE INDEX IF NOT EXISTS idx_kpi_reports_reporting_period
    ON kpi_reports (reporting_period);
