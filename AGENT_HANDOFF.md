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

Notes:
* `V1__init_core_tables.sql` currently contains the initial Flyway health/check setup.
* `V2__create_phase1_tables.sql` contains the actual Phase 1 database schema.
* `V3__create_b1_assessment_tables.sql` contains the Phase 2 B1 Assessment schema.
```

## Completed Phases

- Phase 0 Project Setup: DONE
- Phase 1 Company Setup + BSC Strategy Cycle: DONE
- Phase 2 B1 Assessment: DONE

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

- Phase 3: B2 Strategy Building
- Expected branch: `phase/3-b2-strategy-building`
- Expected scope:
  - `analysis_items`
  - `swot_items`
  - `candidate_strategies`
  - `strategy_swot_items`
  - B2 APIs
  - Complete B2 unlocks B3

## Expected Phase 3 Validation

- B2 data belongs to the current BSC Strategy.
- B2 completion requires `B1_ASSESSMENT = COMPLETED`.
- Analysis items support 7S, 5 Forces, and PESTEL.
- Internal analysis items can be selected as either S or W, not both.
- External analysis items can be selected as either O or T, not both.
- Each analysis item can be selected into at most one SWOT item.
- Total candidate strategies per BSC Strategy must not exceed 12.
- Each SWOT item can be used in at most one candidate strategy.
- Candidate strategy group must be valid: `SO`, `ST`, `WO`, or `WT`.
- Complete B2 updates `B2_STRATEGY_BUILDING` to `COMPLETED`.
- Complete B2 unlocks `B3_STRATEGY_RESULT` to `NOT_STARTED`.

## Required Reading Before Phase 3

Read these before implementing Phase 3:

- `./CODEX.md`
- `./AGENT_HANDOFF.md`
- `./bsc-spec/DEVELOPMENT_PLAN.md` Phase 3
- `./bsc-spec/DATABASE_SCHEMA.md` B2 Strategy Building
- `./bsc-spec/BUSINESS_RULES.md` B2 Strategy Building
- `./bsc-spec/API_CONTRACT.md` B2 Strategy Building
- `./bsc-spec/CODEX_GUIDE.md`
- `./bsc-requirement/B2_xay_dung_chien_luoc.md` if deeper business clarification is needed

## Working Rules For Future Codex Sessions

- Work only on the current requested branch.
- Do not commit, push, merge, or switch branches unless explicitly requested.
- Keep implementation scoped to the requested phase.
- If a schema change is needed, create a new Flyway migration in `./bsc-backend/src/main/resources/db/migration/`.
- Verify with compile and, when relevant, app startup plus Swagger/Postman flow.
