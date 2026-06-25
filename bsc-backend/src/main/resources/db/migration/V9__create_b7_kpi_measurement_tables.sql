CREATE TABLE IF NOT EXISTS kpi_measurements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    department_kpi_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    perspective_code VARCHAR(100) NOT NULL,
    unit VARCHAR(100) NOT NULL,
    baseline_value DECIMAL(18,3),
    target_value DECIMAL(18,3) NOT NULL,
    direction VARCHAR(50) NOT NULL,
    reporting_frequency VARCHAR(50) NOT NULL,
    formula_description TEXT,
    green_threshold DECIMAL(6,3) NOT NULL DEFAULT 90.000,
    yellow_threshold DECIMAL(6,3) NOT NULL DEFAULT 70.000,
    red_threshold DECIMAL(6,3) NOT NULL DEFAULT 0.000,
    report_owner_id UUID,
    status VARCHAR(50) NOT NULL,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_kpi_measurements_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_kpi_measurements_department_kpi
        FOREIGN KEY (department_kpi_id) REFERENCES department_kpis (id),
    CONSTRAINT fk_kpi_measurements_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_kpi_measurements_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_kpi_measurements_report_owner
        FOREIGN KEY (report_owner_id) REFERENCES employees (id),
    CONSTRAINT uk_kpi_measurements_strategy_kpi
        UNIQUE (bsc_strategy_id, department_kpi_id),
    CONSTRAINT chk_kpi_measurements_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_kpi_measurements_direction
        CHECK (direction IN ('HIGHER_IS_BETTER', 'LOWER_IS_BETTER')),
    CONSTRAINT chk_kpi_measurements_reporting_frequency
        CHECK (reporting_frequency IN ('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'CUSTOM')),
    CONSTRAINT chk_kpi_measurements_status
        CHECK (status IN ('ACTIVE', 'INACTIVE')),
    CONSTRAINT chk_kpi_measurements_baseline
        CHECK (baseline_value IS NULL OR baseline_value >= 0),
    CONSTRAINT chk_kpi_measurements_target
        CHECK (target_value >= 0),
    CONSTRAINT chk_kpi_measurements_thresholds
        CHECK (green_threshold > yellow_threshold AND yellow_threshold > red_threshold)
);

CREATE INDEX IF NOT EXISTS idx_kpi_measurements_strategy
    ON kpi_measurements (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_kpi_measurements_department_kpi
    ON kpi_measurements (department_kpi_id);

CREATE INDEX IF NOT EXISTS idx_kpi_measurements_final_objective
    ON kpi_measurements (final_strategic_objective_id);

CREATE INDEX IF NOT EXISTS idx_kpi_measurements_department
    ON kpi_measurements (department_id);

CREATE INDEX IF NOT EXISTS idx_kpi_measurements_report_owner
    ON kpi_measurements (report_owner_id);
