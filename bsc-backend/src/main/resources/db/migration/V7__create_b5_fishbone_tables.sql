CREATE TABLE IF NOT EXISTS department_participations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    department_head_id UUID,
    status VARCHAR(50) NOT NULL,
    created_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_department_participations_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_department_participations_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_department_participations_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_department_participations_department_head
        FOREIGN KEY (department_head_id) REFERENCES employees (id),
    CONSTRAINT uk_department_participations_strategy_objective_department
        UNIQUE (bsc_strategy_id, final_strategic_objective_id, department_id),
    CONSTRAINT chk_department_participations_status
        CHECK (status IN ('ACTIVE', 'REMOVED'))
);

CREATE INDEX IF NOT EXISTS idx_department_participations_strategy_status
    ON department_participations (bsc_strategy_id, status);

CREATE INDEX IF NOT EXISTS idx_department_participations_department_status
    ON department_participations (department_id, status);

CREATE INDEX IF NOT EXISTS idx_department_participations_final_objective_status
    ON department_participations (final_strategic_objective_id, status);

CREATE TABLE IF NOT EXISTS department_kpis (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    final_strategic_objective_id UUID NOT NULL,
    department_id UUID NOT NULL,
    department_participation_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    display_order INTEGER,
    status VARCHAR(50) NOT NULL,
    created_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_department_kpis_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_department_kpis_final_objective
        FOREIGN KEY (final_strategic_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_department_kpis_department
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_department_kpis_participation
        FOREIGN KEY (department_participation_id) REFERENCES department_participations (id),
    CONSTRAINT chk_department_kpis_name_not_blank
        CHECK (length(trim(name)) > 0),
    CONSTRAINT chk_department_kpis_status
        CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE INDEX IF NOT EXISTS idx_department_kpis_strategy_status
    ON department_kpis (bsc_strategy_id, status);

CREATE INDEX IF NOT EXISTS idx_department_kpis_department_status
    ON department_kpis (department_id, status);

CREATE INDEX IF NOT EXISTS idx_department_kpis_final_objective_status
    ON department_kpis (final_strategic_objective_id, status);

CREATE INDEX IF NOT EXISTS idx_department_kpis_participation_status
    ON department_kpis (department_participation_id, status);
