CREATE TABLE IF NOT EXISTS perspective_weights (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    perspective_code VARCHAR(100) NOT NULL,
    weight_percent DECIMAL(6,3) NOT NULL,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_perspective_weights_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT uk_perspective_weights_strategy_perspective
        UNIQUE (bsc_strategy_id, perspective_code),
    CONSTRAINT chk_perspective_weights_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_perspective_weights_percent
        CHECK (weight_percent > 0 AND weight_percent <= 100)
);

CREATE INDEX IF NOT EXISTS idx_perspective_weights_strategy
    ON perspective_weights (bsc_strategy_id);

CREATE TABLE IF NOT EXISTS objective_weights (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    perspective_code VARCHAR(100) NOT NULL,
    weight_percent DECIMAL(6,3) NOT NULL,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_objective_weights_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_objective_weights_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT uk_objective_weights_strategy_objective
        UNIQUE (bsc_strategy_id, final_strategic_objective_id),
    CONSTRAINT chk_objective_weights_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_objective_weights_percent
        CHECK (weight_percent > 0 AND weight_percent <= 100)
);

CREATE INDEX IF NOT EXISTS idx_objective_weights_strategy
    ON objective_weights (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_objective_weights_final_objective
    ON objective_weights (final_strategic_objective_id);

CREATE INDEX IF NOT EXISTS idx_objective_weights_perspective
    ON objective_weights (bsc_strategy_id, perspective_code);

CREATE TABLE IF NOT EXISTS kpi_weights (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    department_kpi_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    perspective_code VARCHAR(100) NOT NULL,
    weight_percent DECIMAL(6,3) NOT NULL,
    created_by UUID,
    updated_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_kpi_weights_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_kpi_weights_department_kpi
        FOREIGN KEY (department_kpi_id) REFERENCES department_kpis (id),
    CONSTRAINT fk_kpi_weights_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_kpi_weights_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT uk_kpi_weights_strategy_kpi
        UNIQUE (bsc_strategy_id, department_kpi_id),
    CONSTRAINT chk_kpi_weights_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_kpi_weights_percent
        CHECK (weight_percent > 0 AND weight_percent <= 100)
);

CREATE INDEX IF NOT EXISTS idx_kpi_weights_strategy
    ON kpi_weights (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_kpi_weights_department_kpi
    ON kpi_weights (department_kpi_id);

CREATE INDEX IF NOT EXISTS idx_kpi_weights_final_objective
    ON kpi_weights (final_strategic_objective_id);

CREATE INDEX IF NOT EXISTS idx_kpi_weights_department
    ON kpi_weights (department_id);
