CREATE TABLE IF NOT EXISTS selected_strategies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    candidate_strategy_id UUID NOT NULL,
    priority_order INTEGER NOT NULL,
    selection_reason TEXT,
    selected_by UUID,
    selected_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_selected_strategies_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_selected_strategies_candidate_strategy
        FOREIGN KEY (candidate_strategy_id) REFERENCES candidate_strategies (id),
    CONSTRAINT uk_selected_strategies_strategy_candidate
        UNIQUE (bsc_strategy_id, candidate_strategy_id),
    CONSTRAINT uk_selected_strategies_strategy_priority
        UNIQUE (bsc_strategy_id, priority_order),
    CONSTRAINT chk_selected_strategies_priority_order
        CHECK (priority_order IN (1, 2))
);

CREATE INDEX IF NOT EXISTS idx_selected_strategies_strategy
    ON selected_strategies (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_selected_strategies_candidate_strategy
    ON selected_strategies (candidate_strategy_id);
