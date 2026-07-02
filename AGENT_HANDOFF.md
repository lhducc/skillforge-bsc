# BSC SkillForge Agent Handoff

## Project Overview

BSC SkillForge is a backend-focused Spring Boot project for building and operating a Balanced Scorecard workflow for small and medium-sized enterprises. The MVP follows the sequence:

```text
B1 Assessment -> B2 Strategy Building -> B3 Strategy Selection -> B4 Strategy Map -> B5 Department KPI/Fishbone -> B6 Weight Allocation -> B7 KPI Measurement & Target -> B8 Action Plan/Task Execution
```

## Current Folder Structure

```text
backend:      ./bsc-backend
specs:        ./bsc-spec
requirements: ./bsc-requirement
```

Important backend areas:

```text
./bsc-backend/src/main/java/com/skillforge/bsc
  auth/
  bsc/
    strategy/
    workflow/
    b1/ ... b8/
  common/
  company/
  config/
  dashboard/
  department/
  user/

./bsc-backend/src/main/resources/db/migration
  V1__init_core_tables.sql
  V2__create_phase1_tables.sql
  V3__create_b1_assessment_tables.sql
  V4__create_b2_strategy_building_tables.sql
  V5__create_b3_strategy_selection_tables.sql
  V6__create_b4_strategy_map_tables.sql
  V7__create_b5_fishbone_tables.sql
  V8__create_b6_weight_allocation_tables.sql
  V9__create_b7_kpi_measurement_tables.sql
  V10__create_b8_execution_report_tables.sql

Notes:
* `V1__init_core_tables.sql` currently contains the initial Flyway health/check setup.
* `V2__create_phase1_tables.sql` contains the actual Phase 1 database schema.
* `V3__create_b1_assessment_tables.sql` contains the Phase 2 B1 Assessment schema.
* `V4__create_b2_strategy_building_tables.sql` contains the Phase 3 B2 Strategy Building schema.
* `V5__create_b3_strategy_selection_tables.sql` contains the Phase 4 B3 Strategy Selection schema.
* `V6__create_b4_strategy_map_tables.sql` contains the Phase 5 B4 Strategy Map schema.
* `V7__create_b5_fishbone_tables.sql` contains the Phase 6 B5 Fishbone / Department KPI schema.
* `V8__create_b6_weight_allocation_tables.sql` contains the Phase 7 B6 Weight Allocation schema.
* `V9__create_b7_kpi_measurement_tables.sql` contains the Phase 8 B7 KPI Measurement & Target schema.
* `V10__create_b8_execution_report_tables.sql` contains the Phase 9 B8 Execution / Action Plan / Task / KPI Report schema.
```

## Completed Phases

- Phase 0 Project Setup: DONE
- Phase 1 Company Setup + BSC Strategy Cycle: DONE
- Phase 2 B1 Assessment: DONE
- Phase 3 B2 Strategy Building: DONE
- Phase 4 B3 Strategy Selection: DONE
- Phase 5 B4 Strategy Map: DONE
- Phase 6 B5 Fishbone / Department KPI: DONE
- Phase 7 B6 Weight Allocation: DONE
- Phase 8 B7 KPI Measurement & Target: DONE
- Phase 9 B8 Execution / Action Plan / Task / KPI Report: DONE
- Phase 10 Dashboard Basic: tagged `phase-10-complete`
- Phase 11 Security Basic / JWT / RBAC / CORS: IMPLEMENTED LOCALLY, NOT COMMITTED

## Phase 1 Branch And Tag

- Branch: `phase/1-company-strategy-cycle`
- Tag: `phase-1-complete`
- Status: merged into `main` via GitHub UI

## Phase 1 Validation Summary

- Maven compile passed.
- App startup passed.
- Swagger full Phase 1 API flow passed.
- BSC Strategy creation creates exactly 8 step statuses.
- `B1_ASSESSMENT = NOT_STARTED`.
- `B2_STRATEGY_BUILDING` through `B8_ACTION_PLAN = LOCKED`.

## Implemented Phase 1 APIs

- `POST /api/v1/companies`
- `GET /api/v1/companies/{companyId}`
- `PUT /api/v1/companies/{companyId}`
- `POST /api/v1/companies/{companyId}/departments`
- `GET /api/v1/companies/{companyId}/departments`
- `PUT /api/v1/departments/{departmentId}`
- `POST /api/v1/companies/{companyId}/employees`
- `GET /api/v1/companies/{companyId}/employees`
- `GET /api/v1/companies/{companyId}/employees?departmentId={departmentId}`
- `POST /api/v1/employees/{employeeId}/account`
- `POST /api/v1/companies/{companyId}/bsc-strategies`
- `GET /api/v1/bsc-strategies/{strategyId}`
- `GET /api/v1/bsc-strategies/{strategyId}/steps`

## Phase 2 Branch And Tag

- Branch: `phase/2-b1-assessment`
- Tag: `phase-2-complete`
- Status: merged into `main`
- Local `main` has been pulled after the merge.

## Phase 2 Validation Summary

- Maven compile passed.
- App startup passed.
- Swagger B1 API flow passed.
- Financial upsert works.
- Market share upsert works.
- Text item upsert works.
- Get assessment works.
- Complete B1 updates `B1_ASSESSMENT` to `COMPLETED`.
- Complete B1 unlocks `B2_STRATEGY_BUILDING` to `NOT_STARTED`.
- Revenue must be greater than or equal to 0.
- Profit may be negative.
- Financial data has at least 1 row and at most 3 rows when completing B1.
- Current and future market share totals must equal 100.
- Each market share period must have at least one own company item.
- Text items must not be blank.

## Implemented Phase 2 APIs

- `PUT /api/v1/bsc-strategies/{strategyId}/assessment/financials`
- `PUT /api/v1/bsc-strategies/{strategyId}/assessment/market-shares`
- `PUT /api/v1/bsc-strategies/{strategyId}/assessment/text-items`
- `GET /api/v1/bsc-strategies/{strategyId}/assessment`
- `POST /api/v1/bsc-strategies/{strategyId}/assessment/complete`

## Phase 3 Branch

- Branch: `phase/3-b2-strategy-building`
- Status: implemented and tested locally.

## Phase 3 Validation Summary

- Maven compile passed.
- App startup passed.
- Swagger B2 API flow passed.
- B2 data belongs to current BSC Strategy.
- B2 completion requires B1 completed.
- 7S can only create S/W.
- 5 Forces + PESTEL can only create O/T.
- One analysis item can be selected into at most one SWOT item.
- Candidate strategies are limited to 12 per BSC Strategy.
- One SWOT item can be used in at most one candidate strategy.
- SO/ST/WO/WT rules are validated.
- Complete B2 updates `B2_STRATEGY_BUILDING = COMPLETED`.
- Complete B2 unlocks `B3_STRATEGY_RESULT = NOT_STARTED`.

## Implemented Phase 3 Tables

- `analysis_items`
- `swot_items`
- `candidate_strategies`
- `strategy_swot_items`

## Implemented Phase 3 APIs

- `PUT /api/v1/bsc-strategies/{strategyId}/analysis-items`
- `POST /api/v1/bsc-strategies/{strategyId}/swot-items`
- `DELETE /api/v1/swot-items/{swotItemId}`
- `POST /api/v1/bsc-strategies/{strategyId}/candidate-strategies`
- `PUT /api/v1/candidate-strategies/{candidateStrategyId}`
- `DELETE /api/v1/candidate-strategies/{candidateStrategyId}`
- `GET /api/v1/bsc-strategies/{strategyId}/candidate-strategies`
- `POST /api/v1/bsc-strategies/{strategyId}/strategy-building/complete`

## Phase 4 Branch

- Branch: `phase/4-b3-strategy-selection`
- Status: implemented and tested locally.

## Phase 4 Validation Summary

- Maven test passed.
- App startup passed.
- Swagger B3 API flow passed.
- Select strategies works.
- Get selected strategies works.
- Complete B3 updates `B3_STRATEGY_RESULT = COMPLETED`.
- Complete B3 unlocks `B4_STRATEGY_MAP = NOT_STARTED`.
- B3 enforces 1 to 2 selected strategies.
- B3 rejects duplicate candidate strategy selection.
- B3 rejects duplicate priority order.
- B3 rejects candidate strategies from another BSC Strategy.
- B3 rejects deleted/inactive candidate strategies.

## Implemented Phase 4 Tables

- `selected_strategies`

## Implemented Phase 4 APIs

- `PUT /api/v1/bsc-strategies/{strategyId}/selected-strategies`
- `GET /api/v1/bsc-strategies/{strategyId}/selected-strategies`
- `POST /api/v1/bsc-strategies/{strategyId}/strategy-result/complete`

## Phase 5 Branch

- Branch: `phase/5-b4-strategy-map`
- Status: implemented and tested locally.

## Phase 5 Validation Summary

- Maven test passed.
- App startup passed.
- Swagger B4 API flow passed.
- Create individual strategy map works.
- Create strategic objective works.
- Update strategic objective works.
- Delete strategic objective works.
- Create objective link works.
- Delete objective link works.
- Build final objectives works.
- Create final objective link works.
- Delete final objective link works.
- Get final strategy map works.
- Complete B4 updates `B4_STRATEGY_MAP = COMPLETED`.
- Complete B4 unlocks `B5_FISHBONE = NOT_STARTED`.
- B4 validates B3 must be completed before working with strategy map.
- B4 validates BSC Strategy must remain `DRAFT`.
- B4 validates each selected strategy must have an individual strategy map.
- B4 validates each selected strategy must have objectives covering all 4 BSC perspectives:
  - `FINANCIAL`
  - `CUSTOMER`
  - `INTERNAL_PROCESS`
  - `LEARNING_AND_GROWTH`
- B4 validates each selected strategy has at most 12 active objectives.
- B4 rejects objective links where source equals target.
- B4 rejects duplicate objective links.
- B4 validates source and target objectives must belong to the same strategy map.
- B4 supports building final objectives from source objectives.
- B4 supports `ORIGINAL`, `MERGED`, and `MANUAL_EDITED` final objective source types if implemented.
- B4 validates merged source objectives must belong to the same perspective.
- B4 validates a source objective cannot be used in more than one active final objective in the same build request.
- B4 validates final objectives cover all 4 BSC perspectives.
- B4 validates final objective links:
  - no self-link
  - no duplicate link
  - source and target final objectives belong to the current BSC Strategy
- Swagger happy case with 2 selected strategies was tested:
  - Created 2 individual strategy maps.
  - Created objectives for both selected strategies.
  - Created causal links for both individual maps.
  - Built final objectives from source objectives.
  - Created final objective links.
  - Completed B4 successfully.

## Implemented Phase 5 Tables

- `strategy_maps`
- `strategic_objectives`
- `objective_links`
- `final_strategic_objectives`
- `final_objective_sources`
- `final_objective_links`

## Implemented Phase 5 APIs

- `POST /api/v1/bsc-strategies/{strategyId}/strategy-maps`
- `POST /api/v1/strategy-maps/{strategyMapId}/objectives`
- `PUT /api/v1/strategic-objectives/{objectiveId}`
- `DELETE /api/v1/strategic-objectives/{objectiveId}`
- `POST /api/v1/strategy-maps/{strategyMapId}/objective-links`
- `DELETE /api/v1/objective-links/{objectiveLinkId}`
- `POST /api/v1/bsc-strategies/{strategyId}/final-objectives/build`
- `POST /api/v1/bsc-strategies/{strategyId}/final-objective-links`
- `DELETE /api/v1/final-objective-links/{finalObjectiveLinkId}`
- `GET /api/v1/bsc-strategies/{strategyId}/final-strategy-map`
- `POST /api/v1/bsc-strategies/{strategyId}/strategy-map/complete`

## Phase 6 Branch

- Branch: `phase/6-b5-fishbone-department-kpi`
- Status: implemented and tested locally.

## Phase 6 Validation Summary

- Maven test passed.
- App startup passed.
- Swagger B5 happy case passed.
- Department join final objective works.
- Create department KPI works.
- Get company fishbone works.
- Get department fishbone works.
- Complete B5 updates `B5_FISHBONE = COMPLETED`.
- Complete B5 unlocks `B6_WEIGHT_ALLOCATION = NOT_STARTED`.
- B5 validates B4 must be completed before B5.
- B5 validates BSC Strategy must remain `DRAFT`.
- B5 validates final objective must belong to current BSC Strategy.
- B5 validates department must exist and belong to same company as the BSC Strategy.
- B5 prevents duplicate active department participation for the same strategy + final objective + department.
- B5 validates department KPI must belong to a valid active department participation.
- B5 validates department KPI strategy/objective/department must match the participation.
- B5 validates KPI name must not be blank.
- Delete KPI follows project convention using soft delete/status.
- Remove department participation rejects removal if active KPIs still exist.
- Rejoining a removed participation reactivates it.
- Complete B5 uses strict MVP validation:
  - every active final objective has at least one active department participation
  - every active final objective has at least one active department KPI
  - every active participation has at least one active KPI
- For Swagger testing convenience, seed data was used to create missing B5 participation/KPI rows after one B5 happy case was tested through Swagger. Completion was still verified through API.

## Implemented Phase 6 Tables

- `department_participations`
- `department_kpis`

## Implemented Phase 6 APIs

- `POST /api/v1/bsc-strategies/{strategyId}/department-participations`
- `DELETE /api/v1/department-participations/{participationId}`
- `POST /api/v1/department-kpis`
- `PUT /api/v1/department-kpis/{departmentKpiId}`
- `DELETE /api/v1/department-kpis/{departmentKpiId}`
- `GET /api/v1/bsc-strategies/{strategyId}/fishbone/company`
- `GET /api/v1/bsc-strategies/{strategyId}/fishbone/departments/{departmentId}`
- `POST /api/v1/bsc-strategies/{strategyId}/fishbone/complete`

## Phase 7 Branch

- Branch: `phase/7-b6-weight-allocation`
- Status: implemented and tested locally.

## Phase 7 Validation Summary

- Maven compile/test passed.
- App startup passed.
- Swagger B6 happy case passed.
- Perspective/objective/KPI weight upsert works.
- Get weight tree works.
- Complete B6 updates `B6_WEIGHT_ALLOCATION = COMPLETED`.
- Complete B6 unlocks `B7_MEASUREMENT_TARGET = NOT_STARTED`.
- B6 validates B5 completed.
- B6 validates strategy `DRAFT`.
- B6 validates all weights are positive.
- B6 validates 4 perspective total = 100.
- B6 validates objective total per perspective = perspective weight.
- B6 validates KPI total per objective = objective weight.
- B6 uses `BigDecimal`/`DECIMAL`, not `float`/`double`.
- B6 does not create B7 measurements, B8 action plans/tasks/reports, dashboard data, or security logic.

## Implemented Phase 7 Tables

- `perspective_weights`
- `objective_weights`
- `kpi_weights`

## Implemented Phase 7 APIs

- `PUT /api/v1/bsc-strategies/{strategyId}/weights/perspectives`
- `PUT /api/v1/bsc-strategies/{strategyId}/weights/objectives`
- `PUT /api/v1/bsc-strategies/{strategyId}/weights/kpis`
- `GET /api/v1/bsc-strategies/{strategyId}/weights/tree`
- `POST /api/v1/bsc-strategies/{strategyId}/weights/complete`

## Phase 8 Branch

- Branch: `phase/8-b7-kpi-measurement`
- Status: implemented and tested locally.

## Phase 8 Validation Summary

- Maven clean compile passed.
- Maven test passed with UTC timezone command.
- App startup passed.
- Swagger B7 happy case passed.
- GET measurements works.
- Upsert KPI measurement works.
- Complete B7 works.
- Complete B7 updates `B7_MEASUREMENT_TARGET = COMPLETED`.
- Complete B7 unlocks `B8_ACTION_PLAN = NOT_STARTED`.
- B7 rejects measurement upsert after B7 is completed.
- B7 validates `B6_WEIGHT_ALLOCATION` must be `COMPLETED`.
- B7 validates BSC Strategy must be `DRAFT`.
- B7 validates department KPI exists and is `ACTIVE`.
- B7 validates department KPI has KPI weight from B6.
- B7 validates unit is required.
- B7 validates targetValue is required and >= 0.
- B7 validates baselineValue is optional but must be >= 0 if provided.
- B7 validates direction enum.
- B7 validates reportingFrequency enum.
- B7 validates threshold order greenThreshold > yellowThreshold > redThreshold.
- B7 defaults thresholds to green=90, yellow=70, red=0 if omitted.
- B7 validates reportOwnerId exists and belongs to the same company if provided.
- B7 validates every active weighted KPI has one active measurement before completion.
- B7 uses `BigDecimal`/`DECIMAL` for numeric business values.
- B7 does not use `float`/`double` for business values.
- B7 does not create B8 action plans, tasks, KPI reports, dashboard data, or security logic.

## Implemented Phase 8 Tables

- `kpi_measurements`

## Implemented Phase 8 APIs

- `PUT /api/v1/department-kpis/{departmentKpiId}/measurement`
- `GET /api/v1/bsc-strategies/{strategyId}/measurements`
- `POST /api/v1/bsc-strategies/{strategyId}/measurements/complete`

## Phase 8 Migration

- `V9__create_b7_kpi_measurement_tables.sql`

## Phase 9 Branch

- Branch: `phase/9-b8-execution-task-report`
- Status: implemented and tested locally.

## Phase 9 Validation Summary

- Maven clean compile passed with UTC timezone command using local Maven.
- Maven test passed with UTC timezone command using local Maven.
- App startup passed during Spring Boot test context.
- Flyway validated 10 migrations and schema version 10.
- B8 action plan create/update/list APIs implemented.
- B8 task create/status update APIs implemented.
- B8 Kanban and Gantt read APIs implemented from task data.
- B8 KPI report create/list/review APIs implemented.
- B8 strategy-level KPI report list supports optional filters.
- Complete B8 setup validates B7 completed.
- Complete B8 setup uses relaxed MVP validation: at least one valid action plan under a measured KPI, every existing action plan has at least one task, and B8 records trace to the current BSC Strategy.
- Action plans validate measured KPI traceability, nonblank name, date range, and valid same-company/same-department owner.
- Tasks validate action plan traceability, nonblank name, same-department assignee, date range, status flow, and block reason.
- Task comments use `actor_employee_id` instead of `user_id` because Phase 9 has no Security/JWT current-user context.
- KPI reports validate measured KPI traceability, `actualValue >= 0`, nonblank reporting period, and no duplicate KPI + period.
- KPI report creation defaults to `SUBMITTED` and only allows `DRAFT` or `SUBMITTED`; `APPROVED` and `REJECTED` are only set through review.
- Server calculates KPI report `completionRate`, `statusColor`, and `achievementStatus`.
- Divide-by-zero is handled deterministically without artificial values such as `100.001`.
- B8 uses `BigDecimal`/`DECIMAL`, not `float`/`double`, for business numeric values.
- B8 does not implement Phase 10 dashboard, Security/JWT/RBAC/CORS, notifications, file storage, or advanced formula engine.
- The Maven wrapper command failed in this environment before Maven startup; local `mvn` was used for successful compile/test validation.

## Implemented Phase 9 Tables

- `action_plans`
- `tasks`
- `task_dependencies`
- `task_comments`
- `kpi_reports`

## Implemented Phase 9 APIs

- `POST /api/v1/action-plans`
- `PUT /api/v1/action-plans/{actionPlanId}`
- `GET /api/v1/bsc-strategies/{strategyId}/action-plans`
- `POST /api/v1/tasks`
- `PATCH /api/v1/tasks/{taskId}/status`
- `GET /api/v1/bsc-strategies/{strategyId}/tasks/kanban`
- `GET /api/v1/bsc-strategies/{strategyId}/tasks/gantt`
- `POST /api/v1/kpi-reports`
- `GET /api/v1/bsc-strategies/{strategyId}/kpi-reports`
- `GET /api/v1/department-kpis/{departmentKpiId}/reports`
- `PATCH /api/v1/kpi-reports/{reportId}/review`
- `POST /api/v1/bsc-strategies/{strategyId}/action-plan/complete`

## Phase 9 Migration

- `V10__create_b8_execution_report_tables.sql`

## Phase 11 Branch

- Branch: `phase/11-security-jwt-rbac-cors`
- Base: `phase-10-complete`
- Status: implemented and tested locally; not committed, pushed, merged, or tagged.

## Phase 11 Validation Summary

- Added `POST /api/v1/auth/login` and `GET /api/v1/auth/me` using the shared `ApiResponse` envelope.
- Login authenticates `user_accounts.email` case-insensitively with BCrypt and rejects inactive/locked accounts and inactive employees.
- User account creation uses the shared BCrypt `PasswordEncoder`; passwords and password hashes are never returned.
- JWT access tokens are signed with HS256, default to 86,400 seconds, and are revalidated against the current account/employee status on every request.
- Added stateless Spring Security, JWT filter, JSON 401/403 handlers, coarse endpoint-level RBAC, and deny-by-default handling for unmatched APIs.
- Added configurable CORS through `CORS_ALLOWED_ORIGINS`; local defaults are `http://localhost:5173` and `http://localhost:3000`.
- Added OpenAPI Bearer JWT security; `/auth/login`, Swagger/OpenAPI, health, and CORS preflight are public.
- Added auth error codes: `AUTH_INVALID_CREDENTIALS`, `AUTH_ACCOUNT_DISABLED`, `AUTH_TOKEN_INVALID`, `AUTH_TOKEN_EXPIRED`, `AUTH_ACCESS_DENIED`, and `AUTH_UNAUTHORIZED`.
- No Flyway migration was needed because `user_accounts.password_hash`, role, status, and employee linkage already support Phase 11.
- `mvn "-Duser.timezone=UTC" test` passed: 14 tests, 0 failures/errors.
- Database-backed integration coverage creates an account through the real service, verifies BCrypt storage, logs in, calls `/auth/me`, accesses a protected API with the JWT, and verifies the OpenAPI Bearer scheme.
- Web security coverage verifies missing/invalid token 401 responses, invalid-role 403, valid-role access, and localhost CORS preflight.

## Implemented Phase 11 APIs

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`

## Phase 11 Security Boundaries

- `COMPANY_ADMIN`: company, department, employee, and user-account setup endpoints.
- `CEO`: BSC strategy creation, B1-B4, B6-B7, company-level B5 reads, B5/B8 reads, workflow summaries, and reserved dashboard reads.
- `DEPARTMENT_HEAD`: B5 operations, B8 action plan/task/report operations, department B5/B8 reads, workflow summaries, and reserved dashboard reads.
- `EMPLOYEE`: assigned-task Kanban reads and assigned-task status updates. Employee Kanban is forced to the authenticated employee ID and employee task updates use the authenticated employee as actor; Gantt remains CEO/department-head only.
- `SYSTEM_ADMIN`: no strategic-data access is granted because there are currently no monitoring/admin endpoints.
- Existing service-level same-company/same-department checks remain authoritative; Phase 11 does not add multi-tenant isolation or a permission table.
- A clean deployment must provision the first `COMPANY_ADMIN` account through controlled seed/DB setup; account creation is not exposed as a public bootstrap endpoint.
- No dashboard controller/source is present in the current checkout; `/bsc-strategies/{id}/dashboard/**` is reserved in RBAC for CEO/department-head reads when that API exists.

## Current Technical Notes

- Use Flyway for all schema changes.
- Do not edit old migrations after they are applied.
- Do not use Hibernate `ddl-auto=update` or `ddl-auto=create`; keep schema validation explicit.
- Use request/response DTOs and the shared `ApiResponse` wrapper.
- Do not return JPA entities directly from controllers.
- Keep business validation in services, not controllers.
- Do not implement outside the current phase scope.
- Keep controllers thin: validate request DTOs, call services, return `ApiResponse`.
- Use `BigDecimal` for business values such as money, percentages, weights, targets, actual values, completion rates, and scores.
- Do not use `float` or `double` for business values.
- Do not edit already-applied migrations unless explicitly instructed and safe. Prefer a new Flyway migration.
- B4 final strategic objectives are the source data for B5 Fishbone / Department KPI.
- After B4 is completed, B5 should read from `final_strategic_objectives`.
- B5 reads source data from `final_strategic_objectives` produced by B4.
- B5 does not create final objectives.
- B5 does not create B6 weights.
- B5 does not create B7 measurements.
- B5 does not create B8 action plans/tasks/reports.
- B5 does not implement Security/JWT/RBAC/CORS.
- B6 reads source data from `final_strategic_objectives` produced by B4 and `department_kpis` produced by B5.
- B6 writes only weight allocation data.
- B6 does not create B7 measurements.
- B6 does not create B8 action plans/tasks/reports.
- B6 does not create dashboard data.
- B6 does not implement Security/JWT/RBAC/CORS.
- B7 reads source data from `department_kpis`, `kpi_weights`, and `final_strategic_objectives`.
- B7 writes only KPI measurement and target data.
- `kpi_measurements` uses one row per `bsc_strategy_id` + `department_kpi_id` in MVP.
- B7 measurement status is kept for future extension, not version history.
- `targetValue = 0` is allowed, so Phase 9/10 KPI report/dashboard calculations must handle divide-by-zero safely.
- `reportOwnerId` same-department validation is not enforced in MVP; same-company validation is implemented.
- B7 does not create B8 action plans/tasks/reports.
- B7 does not create dashboard data.
- B7 does not implement Security/JWT/RBAC/CORS.
- B8 reads source data from `department_kpis`, `kpi_measurements`, and BSC workflow step statuses.
- B8 writes action plan, task, task dependency/comment, and KPI report data only.
- B8 data keeps denormalized traceability fields: `bsc_strategy_id`, `department_kpi_id`, `final_strategic_objective_id`, and `department_id`.
- B8 task comments use `actor_employee_id` for Swagger/MVP testing without Security/JWT current-user lookup.
- B8 action plans and tasks derive strategy/objective/department/KPI traceability from measured KPIs and action plans, not from free request fields.
- B8 KPI reports store calculated `completion_rate`, `status_color`, and `achievement_status`.
- B8 KPI report creation only allows `DRAFT` or `SUBMITTED`; report review owns `APPROVED` and `REJECTED`.
- B8 completion does not unlock or implement any Phase 10 dashboard behavior.
- Dashboard Basic should read from B6 weights, B7 measurements, and B8 KPI reports/tasks/action plans.
- Dashboard Basic should keep KPI performance separate from task/work progress.
- Dashboard Basic should cap score contribution at 100% when calculating weighted score, while still showing raw completion rate.
- Dashboard Basic must handle missing KPI reports and divide-by-zero cases safely.
- Phase 10 should not mutate B6/B7/B8 source data while calculating dashboard responses.
- Continue avoiding advanced permission matrices, refresh-token rotation, multi-tenant SaaS isolation, notifications, file upload/storage, and advanced formula engines unless explicitly requested.

## Next Phase

- Recommended next phase: Phase 12 - MVP end-to-end hardening and release readiness.
- Suggested branch: `phase/12-mvp-hardening-e2e`.
- Focus: verify authenticated B1-B8 flows by role, close only demonstrated ownership gaps, add deployment-safe secret/account bootstrap documentation, and reconcile the missing dashboard source with `phase-10-complete` before release.

## Working Rules For Future Codex Sessions

- Work only on the current requested branch.
- Do not commit, push, merge, or switch branches unless explicitly requested.
- Keep implementation scoped to the requested phase.
- If a schema change is needed, create a new Flyway migration in `./bsc-backend/src/main/resources/db/migration/`.
- Verify with compile and, when relevant, app startup plus Swagger/Postman flow.
