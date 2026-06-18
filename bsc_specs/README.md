# bsc-spec README

## Purpose

This folder contains the technical specification for **BSC SkillForge**.

The purpose of `bsc-spec/` is to convert the detailed business requirements in `bsc-requirement/` into documents that developers and coding agents can use to implement the backend safely and consistently.

The files in this folder are the main reference for:

```text
Database design
Business validation rules
API design
MVP boundary
Development order
Codex / AI agent instructions
```

## Recommended Reading Order

For humans and coding agents, read the files in this order:

```text
1. README.md
2. MVP_SCOPE.md
3. DEVELOPMENT_PLAN.md
4. DATABASE_SCHEMA.md
5. BUSINESS_RULES.md
6. API_CONTRACT.md
7. CODEX_GUIDE.md
```

For coding agents, always read:

```text
CODEX_GUIDE.md
```

before implementing or modifying code.

## File Descriptions

### `MVP_SCOPE.md`

Defines what is included and excluded in the MVP.

Use this file to avoid overbuilding.

It should answer:

```text
What must be built now?
What can be delayed?
What is intentionally not included in MVP?
```

### `DEVELOPMENT_PLAN.md`

Defines the implementation roadmap.

Use this file to decide the order of development.

Expected phases:

```text
Phase 0. Project setup
Phase 1. Core company, department, user, role
Phase 2. BSC strategy workflow
Phase 3. B1 to B4
Phase 4. B5 to B7
Phase 5. B8 operation
Phase 6. Basic dashboard / reporting
```

### `DATABASE_SCHEMA.md`

Defines database tables, relationships, enums, constraints, and migration notes.

Use this file when creating:

```text
JPA entities
Flyway migrations
Repository relationships
Database constraints
Seed data
```

### `BUSINESS_RULES.md`

Defines business rules and validation rules.

Use this file when implementing:

```text
Service-layer validation
Workflow guards
Step completion rules
Permission boundaries
Invalidation rules
```

### `API_CONTRACT.md`

Defines backend REST API endpoints.

Use this file when implementing:

```text
Controllers
Request DTOs
Response DTOs
Swagger documentation
HTTP status behavior
```

### `CODEX_GUIDE.md`

Defines coding-agent instructions.

Use this file when working with Codex or any AI coding agent.

It contains:

```text
Project conventions
Package structure
Coding rules
Validation strategy
Database rules
Business safety rules
Task execution workflow
```

## Relationship With `bsc-requirement/`

`bsc-requirement/` contains detailed business documents for B1 to B8.

`bsc-spec/` is the developer-facing summary and implementation guide.

The recommended usage is:

```text
Use bsc-spec/ for coding.
Use bsc-requirement/ for deeper business clarification.
```

Do not feed all documents to Codex at once unless necessary.

Use focused context.

Example:

```text
To implement B6 Weight Allocation:
- Read DATABASE_SCHEMA.md section B6
- Read BUSINESS_RULES.md section B6
- Read API_CONTRACT.md section B6
- Read CODEX_GUIDE.md
```

## MVP Design Principle

The MVP should be:

```text
Simple enough to finish
Strict enough to preserve BSC logic
Clean enough to extend later
```

Avoid advanced features unless explicitly requested.

Do not overbuild:

```text
Complex RBAC
Advanced dashboard
Notification system
AI auto-merge
File storage
Multi-tenant SaaS infrastructure
```

Focus on:

```text
Correct workflow
Correct data relationship
Correct validation
Clean Spring Boot REST API
PostgreSQL persistence
Swagger-testable endpoints
```

## BSC Workflow Summary

The core workflow is:

```text
B1. CEO enters current business assessment.
B2. CEO builds SWOT and candidate strategies.
B3. CEO selects 1 to 2 strategies.
B4. CEO creates final strategy map.
B5. Department heads create department KPIs from final objectives.
B6. CEO assigns absolute weights from total 100%.
B7. CEO defines measurement, target, direction, and reporting frequency.
B8. Department heads create action plans and tasks linked to KPIs.
```

## Critical System Rule

The system must preserve this traceability chain:

```text
Company
→ BSC Strategy
→ Final Strategic Objective
→ Department KPI
→ KPI Measurement
→ Action Plan
→ Task
→ KPI Report
→ Dashboard
```

A task must always be traceable back to a KPI and strategic objective.
