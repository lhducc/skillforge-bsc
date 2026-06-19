CREATE TABLE IF NOT EXISTS analysis_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    model_type VARCHAR(50) NOT NULL,
    factor_code VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_analysis_items_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT chk_analysis_items_content_not_blank
        CHECK (length(trim(content)) > 0)
);

CREATE TABLE IF NOT EXISTS swot_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    swot_type VARCHAR(10) NOT NULL,
    source_analysis_item_id UUID NOT NULL,
    content_snapshot TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_swot_items_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_swot_items_source_analysis_item
        FOREIGN KEY (source_analysis_item_id) REFERENCES analysis_items (id),
    CONSTRAINT uk_swot_items_strategy_source
        UNIQUE (bsc_strategy_id, source_analysis_item_id),
    CONSTRAINT chk_swot_items_content_snapshot_not_blank
        CHECK (length(trim(content_snapshot)) > 0)
);

CREATE TABLE IF NOT EXISTS candidate_strategies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    strategy_group VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    display_order INTEGER,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_candidate_strategies_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT chk_candidate_strategies_name_not_blank
        CHECK (length(trim(name)) > 0)
);

CREATE TABLE IF NOT EXISTS strategy_swot_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    candidate_strategy_id UUID NOT NULL,
    swot_item_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_strategy_swot_items_candidate_strategy
        FOREIGN KEY (candidate_strategy_id) REFERENCES candidate_strategies (id),
    CONSTRAINT fk_strategy_swot_items_swot_item
        FOREIGN KEY (swot_item_id) REFERENCES swot_items (id),
    CONSTRAINT uk_strategy_swot_items_swot_item
        UNIQUE (swot_item_id)
);

CREATE INDEX IF NOT EXISTS idx_analysis_items_strategy
    ON analysis_items (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_swot_items_strategy
    ON swot_items (bsc_strategy_id);

CREATE INDEX IF NOT EXISTS idx_candidate_strategies_strategy_status
    ON candidate_strategies (bsc_strategy_id, status);
