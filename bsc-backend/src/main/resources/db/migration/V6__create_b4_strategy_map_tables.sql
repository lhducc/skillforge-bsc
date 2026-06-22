CREATE TABLE IF NOT EXISTS strategy_maps (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    selected_strategy_id UUID,
    map_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_strategy_maps_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_strategy_maps_selected_strategy
        FOREIGN KEY (selected_strategy_id) REFERENCES selected_strategies (id),
    CONSTRAINT chk_strategy_maps_map_type
        CHECK (map_type IN ('INDIVIDUAL', 'FINAL')),
    CONSTRAINT chk_strategy_maps_status
        CHECK (status IN ('DRAFT', 'COMPLETED')),
    CONSTRAINT chk_strategy_maps_selected_strategy_required
        CHECK (map_type = 'FINAL' OR selected_strategy_id IS NOT NULL)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_strategy_maps_strategy_selected_type
    ON strategy_maps (bsc_strategy_id, selected_strategy_id, map_type)
    WHERE selected_strategy_id IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_strategy_maps_strategy
    ON strategy_maps (bsc_strategy_id);

CREATE TABLE IF NOT EXISTS strategic_objectives (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    strategy_map_id UUID NOT NULL,
    selected_strategy_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    perspective_code VARCHAR(100) NOT NULL,
    display_order INTEGER,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_strategic_objectives_strategy_map
        FOREIGN KEY (strategy_map_id) REFERENCES strategy_maps (id),
    CONSTRAINT fk_strategic_objectives_selected_strategy
        FOREIGN KEY (selected_strategy_id) REFERENCES selected_strategies (id),
    CONSTRAINT chk_strategic_objectives_name_not_blank
        CHECK (length(trim(name)) > 0),
    CONSTRAINT chk_strategic_objectives_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_strategic_objectives_status
        CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE INDEX IF NOT EXISTS idx_strategic_objectives_strategy_map_status
    ON strategic_objectives (strategy_map_id, status);

CREATE INDEX IF NOT EXISTS idx_strategic_objectives_selected_strategy_status
    ON strategic_objectives (selected_strategy_id, status);

CREATE TABLE IF NOT EXISTS objective_links (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    strategy_map_id UUID NOT NULL,
    source_objective_id UUID NOT NULL,
    target_objective_id UUID NOT NULL,
    note TEXT,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_objective_links_strategy_map
        FOREIGN KEY (strategy_map_id) REFERENCES strategy_maps (id),
    CONSTRAINT fk_objective_links_source_objective
        FOREIGN KEY (source_objective_id) REFERENCES strategic_objectives (id),
    CONSTRAINT fk_objective_links_target_objective
        FOREIGN KEY (target_objective_id) REFERENCES strategic_objectives (id),
    CONSTRAINT uk_objective_links_map_source_target
        UNIQUE (strategy_map_id, source_objective_id, target_objective_id),
    CONSTRAINT chk_objective_links_no_self_reference
        CHECK (source_objective_id <> target_objective_id)
);

CREATE INDEX IF NOT EXISTS idx_objective_links_strategy_map
    ON objective_links (strategy_map_id);

CREATE TABLE IF NOT EXISTS final_strategic_objectives (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    perspective_code VARCHAR(100) NOT NULL,
    source_type VARCHAR(50) NOT NULL,
    display_order INTEGER,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_final_strategic_objectives_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT chk_final_strategic_objectives_name_not_blank
        CHECK (length(trim(name)) > 0),
    CONSTRAINT chk_final_strategic_objectives_perspective
        CHECK (perspective_code IN ('FINANCIAL', 'CUSTOMER', 'INTERNAL_PROCESS', 'LEARNING_AND_GROWTH')),
    CONSTRAINT chk_final_strategic_objectives_source_type
        CHECK (source_type IN ('ORIGINAL', 'MERGED', 'MANUAL_EDITED')),
    CONSTRAINT chk_final_strategic_objectives_status
        CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE INDEX IF NOT EXISTS idx_final_strategic_objectives_strategy_status
    ON final_strategic_objectives (bsc_strategy_id, status);

CREATE TABLE IF NOT EXISTS final_objective_sources (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    final_objective_id UUID NOT NULL,
    source_objective_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_final_objective_sources_final_objective
        FOREIGN KEY (final_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_final_objective_sources_source_objective
        FOREIGN KEY (source_objective_id) REFERENCES strategic_objectives (id),
    CONSTRAINT uk_final_objective_sources_final_source
        UNIQUE (final_objective_id, source_objective_id)
);

CREATE INDEX IF NOT EXISTS idx_final_objective_sources_final_objective
    ON final_objective_sources (final_objective_id);

CREATE INDEX IF NOT EXISTS idx_final_objective_sources_source_objective
    ON final_objective_sources (source_objective_id);

CREATE TABLE IF NOT EXISTS final_objective_links (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    source_final_objective_id UUID NOT NULL,
    target_final_objective_id UUID NOT NULL,
    note TEXT,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_final_objective_links_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT fk_final_objective_links_source_final_objective
        FOREIGN KEY (source_final_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT fk_final_objective_links_target_final_objective
        FOREIGN KEY (target_final_objective_id) REFERENCES final_strategic_objectives (id),
    CONSTRAINT uk_final_objective_links_strategy_source_target
        UNIQUE (bsc_strategy_id, source_final_objective_id, target_final_objective_id),
    CONSTRAINT chk_final_objective_links_no_self_reference
        CHECK (source_final_objective_id <> target_final_objective_id)
);

CREATE INDEX IF NOT EXISTS idx_final_objective_links_strategy
    ON final_objective_links (bsc_strategy_id);
