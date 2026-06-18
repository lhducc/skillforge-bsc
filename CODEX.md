# CODEX.md

## Project Context

This repository contains **BSC SkillForge**, a backend-focused project for building and operating a Balanced Scorecard (BSC) workflow for small and medium-sized enterprises.

The system is designed around the BSC implementation flow:

```text
B1. Business Assessment
B2. Strategy Building
B3. Strategy Selection
B4. Strategy Map
B5. Fishbone Model / Department KPIs
B6. Weight Allocation
B7. KPI Measurement & Targets
B8. Action Plan / Task Execution
```

The backend project is expected to be implemented with:

```text
Java 21
Spring Boot 3.5.x
PostgreSQL
Spring Data JPA
Flyway
Validation
Swagger / OpenAPI
Lombok
MapStruct
```

## Repository Structure

Expected structure:

```text
BSCSkillForge/
├── CODEX.md
├── docker-compose.yml
├── .gitignore
│
├── bsc-requirement/
│   ├── B1_danh_gia_hien_trang_doanh_nghiep.md
│   ├── B2_xay_dung_chien_luoc.md
│   ├── B3_ket_qua_chien_luoc.md
│   ├── B4_ban_do_chien_luoc.md
│   ├── B5_mo_hinh_xuong_ca.md
│   ├── B6_phan_bo_ti_trong.md
│   ├── B7_do_luong_va_chi_tieu.md
│   └── B8_action_plan.md
│
├── bsc-spec/
│   ├── README.md
│   ├── MVP_SCOPE.md
│   ├── DEVELOPMENT_PLAN.md
│   ├── DATABASE_SCHEMA.md
│   ├── BUSINESS_RULES.md
│   ├── API_CONTRACT.md
│   └── CODEX_GUIDE.md
│
└── bsc-backend/
    ├── pom.xml
    └── src/
```

## Required Reading Before Coding

Before making changes, read these files first:

```text
bsc-spec/README.md
bsc-spec/MVP_SCOPE.md
bsc-spec/DEVELOPMENT_PLAN.md
bsc-spec/CODEX_GUIDE.md
```

When implementing a specific module, also read the related sections in:

```text
bsc-spec/DATABASE_SCHEMA.md
bsc-spec/BUSINESS_RULES.md
bsc-spec/API_CONTRACT.md
```

Use `bsc-requirement/` only as the detailed business reference source. Do not use it to override the technical decisions already summarized in `bsc-spec/` unless the user explicitly requests it.

## Main Agent Rules

1. Do not change business rules unless explicitly requested.
2. Do not implement modules outside the requested scope.
3. Do not rewrite unrelated files.
4. Do not introduce unnecessary architecture complexity for MVP.
5. Follow the package structure and coding conventions defined in `bsc-spec/CODEX_GUIDE.md`.
6. Use Flyway migrations for database schema changes.
7. Use `BigDecimal` for money, percentage, weight, target, baseline, actual value, and completion rate.
8. Do not use `float` or `double` for business values.
9. Use UUID identifiers unless an existing spec says otherwise.
10. Keep BSC workflow dependencies strict: a later step must not be completed before required previous steps are completed.

## MVP Priority

The MVP goal is to deliver a working backend for the BSC workflow, not a perfect enterprise system.

Prioritize:

```text
Correct business flow
Clean database schema
Clear API contracts
Basic validation
Readable Spring Boot code
Swagger-testable endpoints
```

Deprioritize unless explicitly requested:

```text
Full RBAC complexity
Advanced dashboard analytics
Notification system
File upload storage
AI-assisted strategy generation
Complex multi-tenant SaaS architecture
```

## How To Work On Tasks

For each coding task:

1. Identify the requested module.
2. Read only the related spec sections.
3. Implement the minimum necessary backend changes.
4. Add or update Flyway migration if schema changes are needed.
5. Add DTOs, validation, service logic, repository, controller, and exception handling consistently.
6. Keep APIs aligned with `API_CONTRACT.md`.
7. Do not modify unrelated modules.
8. Run build/tests if available.
9. Summarize what changed and mention any assumptions.

## Important Business Principles

The BSC workflow is sequential:

```text
B1 → B2 → B3 → B4 → B5 → B6 → B7 → B8
```

Key rules:

```text
B3 selects 1 to 2 strategies from B2.
B4 creates the final strategy map from selected strategies.
B5 creates department KPIs from final strategic objectives.
B6 assigns absolute weights from the total 100%.
B7 defines measurement and targets for weighted KPIs.
B8 creates action plans and tasks linked to KPIs.
```

Task creation rule:

```text
Task must not be created freely.
Task must belong to an Action Plan.
Action Plan must belong to a Department KPI.
Department KPI must belong to a Final Strategic Objective.
```

## Source Of Truth Priority

When sources conflict, follow this order:

```text
1. User's latest direct instruction
2. bsc-spec/
3. bsc-requirement/
4. Existing codebase
5. General framework knowledge
```

If a requirement is unclear, make the smallest safe MVP assumption and document it in the response.
