CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS companies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    tax_code VARCHAR(100),
    industry VARCHAR(255),
    size VARCHAR(100),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS departments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    description TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_departments_company
        FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT uk_departments_company_code
        UNIQUE (company_id, code)
);

CREATE TABLE IF NOT EXISTS employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id UUID NOT NULL,
    department_id UUID NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    position_title VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_employees_company
        FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT fk_employees_department
        FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE IF NOT EXISTS user_accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_accounts_employee
        FOREIGN KEY (employee_id) REFERENCES employees (id),
    CONSTRAINT uk_user_accounts_email
        UNIQUE (email),
    CONSTRAINT uk_user_accounts_employee
        UNIQUE (employee_id)
);

CREATE TABLE IF NOT EXISTS bsc_strategies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    year INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_by UUID,
    activated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bsc_strategies_company
        FOREIGN KEY (company_id) REFERENCES companies (id)
);

CREATE TABLE IF NOT EXISTS bsc_step_statuses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bsc_strategy_id UUID NOT NULL,
    step_code VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    completed_by UUID,
    completed_at TIMESTAMP,
    invalidated_reason TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bsc_step_statuses_strategy
        FOREIGN KEY (bsc_strategy_id) REFERENCES bsc_strategies (id),
    CONSTRAINT uk_bsc_step_statuses_strategy_step
        UNIQUE (bsc_strategy_id, step_code)
);
