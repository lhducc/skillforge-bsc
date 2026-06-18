# CODEX_GUIDE.md

## Purpose

This file defines how Codex or any coding agent should work on the **BSC SkillForge** backend.

Follow this file when creating or modifying code.

The goal is to produce clean, consistent, MVP-focused Spring Boot backend code without changing the agreed business rules.

## Project Tech Stack

Use the following stack unless the user explicitly changes it:

```text
Java 21
Spring Boot 3.5.x
Maven
PostgreSQL
Spring Web
Spring Data JPA
Spring Validation
Flyway
Springdoc OpenAPI / Swagger
Lombok
MapStruct
```

Security can be added incrementally.

For MVP, it is acceptable to start with a simple authentication flow and add full JWT/RBAC later if requested.

## Source Of Truth

When implementing code, follow this priority:

```text
1. User's latest direct instruction
2. bsc-spec/
3. bsc-requirement/
4. Existing code
5. General Spring Boot knowledge
```

If something is unclear, choose the smallest safe MVP implementation and explain the assumption.

Do not invent new business logic if the existing spec already defines it.

## Required Documents To Read

Before making code changes, read:

```text
bsc-spec/MVP_SCOPE.md
bsc-spec/DEVELOPMENT_PLAN.md
bsc-spec/CODEX_GUIDE.md
```

For module-specific work, also read the relevant sections in:

```text
bsc-spec/DATABASE_SCHEMA.md
bsc-spec/BUSINESS_RULES.md
bsc-spec/API_CONTRACT.md
```

Examples:

```text
Task: Implement B1
Read: DATABASE_SCHEMA B1, BUSINESS_RULES B1, API_CONTRACT B1

Task: Implement B6
Read: DATABASE_SCHEMA B6, BUSINESS_RULES B6, API_CONTRACT B6

Task: Implement B8 task status update
Read: DATABASE_SCHEMA B8, BUSINESS_RULES B8, API_CONTRACT B8
```

## General Coding Rules

1. Do not modify unrelated modules.
2. Do not rename existing entities, tables, packages, or APIs unless required.
3. Do not change business rules without explicit instruction.
4. Do not hardcode values that should be enums or seed data.
5. Do not use `float` or `double` for business values.
6. Do not create database schema manually outside Flyway.
7. Do not bypass service-layer validation.
8. Do not put business logic in controllers.
9. Do not return JPA entities directly from controllers.
10. Do not create circular dependencies between services.

## Package Structure

Use this structure inside `bsc-backend`:

```text
src/main/java/.../
├── BscSkillForgeApplication.java
│
├── common/
│   ├── response/
│   ├── exception/
│   ├── pagination/
│   └── constant/
│
├── config/
│   ├── OpenApiConfig.java
│   ├── JpaAuditingConfig.java
│   └── SecurityConfig.java
│
├── auth/
│   ├── controller/
│   ├── service/
│   ├── dto/
│   └── entity/
│
├── company/
├── department/
├── user/
├── bsc/
│   ├── strategy/
│   ├── workflow/
│   ├── b1/
│   ├── b2/
│   ├── b3/
│   ├── b4/
│   ├── b5/
│   ├── b6/
│   ├── b7/
│   └── b8/
└── dashboard/
```

Each module should generally contain:

```text
controller/
service/
repository/
entity/
dto/request/
dto/response/
mapper/
```

For small MVP modules, a flatter package is acceptable, but keep it readable.

## Entity Rules

Use UUID identifiers by default:

```java
@Id
@GeneratedValue
private UUID id;
```

Use a shared base entity when possible:

```text
id
createdAt
updatedAt
createdBy
updatedBy
```

Use JPA auditing for timestamps.

Do not expose entities directly through APIs.

Use DTOs.

## Data Type Rules

Use `BigDecimal` for:

```text
Money
Revenue
Profit
Market share percent
Weight percent
Baseline value
Target value
Actual value
Completion rate
Weighted score
```

Do not use:

```text
float
double
```

for business calculations.

Use `LocalDate` for dates without time.

Use `LocalDateTime` or `OffsetDateTime` for timestamps depending on project convention.

For MVP, `LocalDateTime` is acceptable if the application uses a consistent timezone.

## Enum Rules

Use Java enums for fixed business concepts.

Examples:

```text
BscStepStatus
BscStrategyStatus
BscPerspectiveCode
SwotType
StrategyGroupType
KpiDirection
ReportingFrequency
TaskStatus
ActionPlanStatus
KpiReportStatus
Priority
```

Stable framework concepts can be enums.

Examples:

```text
SWOT: S, W, O, T
BSC perspectives: FINANCIAL, CUSTOMER, INTERNAL_PROCESS, LEARNING_AND_GROWTH
7S categories
PESTEL categories
5 Forces categories
```

Do not create lookup tables for stable framework concepts unless the spec requests it.

## API Response Rule

All API responses should use a common response wrapper.

Example:

```json
{
  "success": true,
  "code": "SUCCESS",
  "message": "Success",
  "data": {}
}
```

For errors:

```json
{
  "success": false,
  "code": "B6_WEIGHT_TOTAL_INVALID",
  "message": "Total weight must equal 100%",
  "data": null
}
```

Use consistent error codes.

## Exception Handling Rule

Use centralized exception handling.

Expected classes:

```text
BusinessException
ErrorCode
GlobalExceptionHandler
ValidationErrorResponse
```

Do not throw raw `RuntimeException` for business validation.

Do not return validation errors manually in controllers if they can be handled globally.

## Validation Strategy

Use annotation-based validation for simple request checks:

```text
@NotNull
@NotBlank
@Size
@DecimalMin
@DecimalMax
```

Use service-layer validation for business rules.

Examples:

```text
B3 must select 1 to 2 strategies.
B6 perspective weights must sum to 100%.
B7 target must be present for every KPI.
B8 task must belong to an action plan.
```

## Database And Flyway Rules

All database schema changes must be done through Flyway migrations.

Migration location:

```text
src/main/resources/db/migration/
```

Migration naming:

```text
V1__init_core_tables.sql
V2__init_bsc_strategy_tables.sql
V3__init_b1_to_b4_tables.sql
V4__init_b5_to_b7_tables.sql
V5__init_b8_operation_tables.sql
V6__seed_master_data.sql
```

Do not edit old migrations after they have been applied unless the user explicitly asks and the project is still local-only.

For MVP local development, editing early migrations is acceptable only before the database is shared.

## Business Workflow Rules

The BSC workflow is sequential:

```text
B1 → B2 → B3 → B4 → B5 → B6 → B7 → B8
```

General rule:

```text
A step cannot be completed if required previous steps are not completed.
```

Each BSC Strategy should track step status.

Recommended step statuses:

```text
NOT_STARTED
IN_PROGRESS
COMPLETED
INVALIDATED
LOCKED
```

Some steps may have additional states if defined in the spec.

## B1 Rules

B1 stores business assessment data.

Core requirements:

```text
Financial data: max 3 years
Revenue must not be negative
Profit may be negative
Current market share total must equal 100%
Future market share total must equal 100%
Dynamic text list items must not be blank
```

B1 does not generate strategies.

B1 unlocks B2 after completion.

## B2 Rules

B2 builds strategic analysis and candidate strategies.

Core flow:

```text
7S → source for Strengths and Weaknesses
5 Forces + PESTEL → source for Opportunities and Threats
SWOT → source for SO/ST/WO/WT strategies
```

Important rules:

```text
An internal analysis item can be selected as either S or W, not both.
An external analysis item can be selected as either O or T, not both.
Each SWOT item can be used in at most one candidate strategy.
Total candidate strategies must not exceed 12.
At least 1 candidate strategy is required to complete B2.
```

## B3 Rules

B3 selects official strategies from B2.

Rules:

```text
CEO must select at least 1 strategy.
CEO can select at most 2 strategies.
Selected strategies must belong to the current BSC Strategy.
Selected strategies must still exist and be valid.
No duplicate selected strategies.
```

B3 unlocks B4 after completion.

## B4 Rules

B4 creates strategy maps.

Rules for 1 selected strategy:

```text
The strategy map must include all 4 BSC perspectives.
Each perspective must have at least 1 strategic objective.
The map must not exceed 12 objectives.
No self-link in causal links.
No duplicate causal links.
```

Rules for 2 selected strategies:

```text
Each individual map must include all 4 BSC perspectives.
Each individual map must not exceed 12 objectives.
The final merged map must include all 4 BSC perspectives.
The final merged map must not exceed 24 objectives.
Manual merge only.
Merge is allowed only for objectives in the same BSC perspective.
```

B4 produces final strategic objectives for B5.

## B5 Rules

B5 creates department KPIs from final strategic objectives.

Rules:

```text
CEO can view B5 but should not create department KPIs.
Department heads select which final objectives their department participates in.
Department heads create KPIs only for their own department.
Each department KPI must belong to a final strategic objective.
Each final strategic objective should have at least 1 department KPI before completing B5.
```

B5 does not assign weights or targets.

B5 unlocks B6.

## B6 Rules

B6 assigns weights.

Most important rule:

```text
All weights are absolute percentages based on the total BSC 100%.
They are not relative percentages inside each child group.
```

Validation:

```text
Total of 4 perspective weights = 100%.
Total objective weights in each perspective = that perspective weight.
Total KPI weights under each objective = that objective weight.
Each active item must have weight > 0.
Use BigDecimal for validation.
```

Do not use float or double.

B6 unlocks B7.

## B7 Rules

B7 defines KPI measurement and targets.

Rules:

```text
B7 does not create KPIs.
Every KPI from B5/B6 must have exactly one active measurement config.
Target is required.
Unit is required.
KPI direction is required.
Reporting frequency is required.
Thresholds must be valid.
```

KPI direction:

```text
HIGHER_IS_BETTER
LOWER_IS_BETTER
```

Completion rate formula:

```text
HIGHER_IS_BETTER: actual / target * 100
LOWER_IS_BETTER: target / actual * 100
```

Dashboard score may cap completion rate at 100% for aggregation if the spec requires it.

## B8 Rules

B8 creates action plans, tasks, and KPI reports.

Critical rule:

```text
Task must not be created freely.
Task must belong to an Action Plan.
Action Plan must belong to a Department KPI.
Department KPI must belong to a Final Strategic Objective.
```

Action plan rules:

```text
Department heads create action plans for KPIs of their own department.
Action plan must have name, start date, end date, owner, and status.
Start date must not be after end date.
```

Task rules:

```text
Task must belong to an action plan.
Task assignee must be valid.
Task should stay within the department in MVP.
Start date must not be after due date.
If status is BLOCKED, block reason is required.
```

Recommended task statuses:

```text
TODO
IN_PROGRESS
REVIEW
DONE
BLOCKED
CANCELLED
```

Recommended flow:

```text
TODO → IN_PROGRESS
IN_PROGRESS → REVIEW
REVIEW → DONE
REVIEW → IN_PROGRESS
IN_PROGRESS → BLOCKED
BLOCKED → IN_PROGRESS
TODO/IN_PROGRESS/REVIEW → CANCELLED
```

KPI report rules:

```text
KPI report must belong to a measured KPI.
Actual value is required.
Actual value must be numeric.
Reporting period must match reporting frequency.
Avoid duplicate report for the same KPI and period if MVP requires one report per period.
```

## Dashboard Rules

Dashboard should derive from:

```text
B6: weights
B7: target, direction, threshold
B8: actual value, task progress, KPI reports
```

Traceability:

```text
Company Score
→ Perspective Score
→ Strategic Objective Score
→ Department KPI
→ Action Plan
→ Task
→ Employee
```

Do not implement advanced dashboard unless explicitly requested.

Start with basic calculation and drill-down data.

## Controller Rules

Controllers should:

```text
Validate request DTOs
Call services
Return ApiResponse
Not contain business logic
Not directly use EntityManager
Not return JPA entities
```

## Service Rules

Services should:

```text
Implement business validation
Check workflow status
Check ownership or role boundaries
Call repositories
Map entities to DTOs
Throw BusinessException when rules fail
```

## Repository Rules

Repositories should be Spring Data JPA interfaces.

Use query methods for simple queries.

Use `@Query` only when necessary.

Avoid complex native SQL unless required.

## Mapper Rules

Use MapStruct for entity-to-DTO mapping if available.

Manual mapping is acceptable for small MVP modules, but keep it consistent.

Do not place mapping logic in controllers.

## Security Rules

For MVP, security may be incremental.

Minimum expectation:

```text
Password must be hashed with BCrypt.
Do not store plain text password.
Prepare User / Role structure for later JWT and RBAC.
```

If JWT is not implemented yet, keep security config simple and document the assumption.

Do not implement complicated permission logic unless requested.

## Testing Rules

When tests are requested, prioritize:

```text
Service unit tests for business rules
Controller integration tests for API behavior
Repository tests only when custom queries are complex
```

Important business tests:

```text
B3 cannot select 0 or more than 2 strategies.
B6 weight totals must match parent weights.
B7 cannot complete when a KPI has no target.
B8 cannot create task without action plan.
```

## What Not To Do

Do not:

```text
Rewrite the whole project without being asked.
Create a new architecture style without being asked.
Add microservices.
Add Kafka.
Add Redis.
Add Docker complexity beyond PostgreSQL unless requested.
Add AI features.
Add frontend code.
Add advanced RBAC before the MVP backend works.
```

## Completion Response Format

After completing a coding task, summarize:

```text
Files changed
What was implemented
Business rules covered
How to test
Assumptions made
```

If something could not be completed, say clearly what is missing and why.
