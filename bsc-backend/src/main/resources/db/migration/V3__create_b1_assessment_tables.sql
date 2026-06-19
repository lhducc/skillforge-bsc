CREATE TABLE IF NOT EXISTS assessment_financials (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    year INTEGER NOT NULL,
    revenue DECIMAL(18, 2) NOT NULL,
    profit DECIMAL(18, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assessment_financials_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT uk_assessment_financials_strategy_year
        UNIQUE (bsc_strategy_id, year),
    CONSTRAINT chk_assessment_financials_revenue_non_negative
        CHECK (revenue >= 0)
);

CREATE TABLE IF NOT EXISTS assessment_market_shares (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    period_type VARCHAR(50) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    market_share_percent DECIMAL(6, 3) NOT NULL,
    is_own_company BOOLEAN NOT NULL,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assessment_market_shares_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT chk_assessment_market_share_percent_range
        CHECK (market_share_percent >= 0 AND market_share_percent <= 100)
);

CREATE TABLE IF NOT EXISTS assessment_text_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    category VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assessment_text_items_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT chk_assessment_text_items_content_not_blank
        CHECK (length(trim(content)) > 0)
);
