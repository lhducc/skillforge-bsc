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

Notes:
* `V1__init_core_tables.sql` currently contains the initial Flyway health/check setup.
* `V2__create_phase1_tables.sql` contains the actual Phase 1 database schema.
* `V3__create_b1_assessment_tables.sql` contains the Phase 2 B1 Assessment schema.
* `V4__create_b2_strategy_building_tables.sql` contains the Phase 3 B2 Strategy Building schema.
* `V5__create_b3_strategy_selection_tables.sql` contains the Phase 4 B3 Strategy Selection schema.
```

## Completed Phases

- Phase 0 Project Setup: DONE
- Phase 1 Company Setup + BSC Strategy Cycle: DONE
- Phase 2 B1 Assessment: DONE
- Phase 3 B2 Strategy Building: DONE
- Phase 4 B3 Strategy Selection: DONE

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

## Current Technical Notes

- Use Flyway for all schema changes.
- Do not use Hibernate `ddl-auto=update` or `ddl-auto=create`; keep schema validation explicit.
- Use request/response DTOs and the shared `ApiResponse` wrapper.
- Do not return JPA entities directly from controllers.
- Keep business validation in services, not controllers.
- Do not implement outside the current phase scope.
- Keep controllers thin: validate request DTOs, call services, return `ApiResponse`.
- Use `BigDecimal` for business values such as money, percentages, weights, targets, actual values, completion rates, and scores.
- Do not use `float` or `double` for business values.
- Do not edit already-applied migrations unless explicitly instructed and safe. Prefer a new Flyway migration.

## Next Phase

- Phase 5: B4 Strategy Map
- Expected branch: `phase/5-b4-strategy-map`
- Expected scope:
  - `strategy_maps`
  - `strategic_objectives`
  - `objective_links`
  - `final_strategic_objectives`
  - `final_objective_sources`
  - `final_objective_links`

## Working Rules For Future Codex Sessions

- Work only on the current requested branch.
- Do not commit, push, merge, or switch branches unless explicitly requested.
- Keep implementation scoped to the requested phase.
- If a schema change is needed, create a new Flyway migration in `./bsc-backend/src/main/resources/db/migration/`.
- Verify with compile and, when relevant, app startup plus Swagger/Postman flow.
