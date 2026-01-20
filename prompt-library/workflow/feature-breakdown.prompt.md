---
agent: 'agent'
description: 'Breaks down a feature or epic into smaller, actionable implementation tasks'
tools: ['search/codebase', 'search/changes']
argument-hint: 'Provide: feature description or epic to break down'
---

# Feature Breakdown Generator

**Category:** Workflow | **Success Rate:** 90%

Break down a feature or epic into smaller, actionable implementation tasks suitable for individual work items or tickets.

## Required Information

Please provide:
- **Feature/Epic description**: High-level description of what to build
- **Target system**: Where this will be implemented (e.g., Angular frontend, Spring backend)
- **Complexity level**: Simple, Medium, or Complex
- **Team context**: Solo developer, pair, or team

## Breakdown Principles

### Task Sizing Guidelines

| Size | Duration | Scope |
|------|----------|-------|
| **XS** | < 2 hours | Single function, config change |
| **S** | 2-4 hours | Single component/service method |
| **M** | 4-8 hours | Feature slice (vertical) |
| **L** | 1-2 days | Multiple components, integration |
| **XL** | 2-5 days | Should be broken down further |

### INVEST Criteria for Tasks

Each task should be:
- **I**ndependent: Can be worked on separately
- **N**egotiable: Details can be discussed
- **V**aluable: Delivers user or technical value
- **E**stimable: Can be sized with confidence
- **S**mall: Fits in one sprint/iteration
- **T**estable: Has clear acceptance criteria

## Output Structure

### 1. Feature Overview
- Summary of the feature
- User value delivered
- Technical scope

### 2. Task Breakdown

```markdown
## Tasks

### Task 1: [Title] (Size: S)
**Description:** [What needs to be done]
**Acceptance Criteria:**
- [ ] Criterion 1
- [ ] Criterion 2
**Dependencies:** None | Task X
**Files likely affected:** `path/to/file.ts`

### Task 2: [Title] (Size: M)
...
```

### 3. Dependency Graph
Show which tasks depend on others:

```
Task 1 (Setup) ──► Task 2 (Core Logic)
                         │
                         ▼
Task 3 (API) ◄───► Task 4 (UI)
                         │
                         ▼
                  Task 5 (Integration)
```

### 4. Suggested Order
Recommended sequence considering dependencies and risk.

---

## Breakdown Strategies

### Vertical Slicing (Preferred)
Break by user-facing functionality:
- ✅ "Add login form with validation"
- ✅ "Implement password reset flow"
- ❌ "Build all form components" (horizontal)

### By Layer (When Necessary)
When vertical slicing isn't practical:
1. Data model / types
2. Service / business logic
3. API / controller
4. UI / presentation
5. Integration / wiring

### By Risk
Start with highest-risk items:
1. Unknown technology
2. External dependencies
3. Complex algorithms
4. Performance-critical paths

## Example Breakdown

**Feature:** User Profile Management

```markdown
### Task 1: Create User Profile types (XS)
- Define UserProfile interface
- Define UpdateProfileRequest DTO
- Add validation schemas
**Files:** `types/user.ts`, `schemas/user.schema.ts`

### Task 2: Implement ProfileService (S)
- Add getProfile(userId) method
- Add updateProfile(userId, data) method
- Include validation and error handling
**Depends on:** Task 1
**Files:** `services/profile.service.ts`

### Task 3: Add Profile API endpoints (S)
- GET /api/users/:id/profile
- PUT /api/users/:id/profile
- Add request validation middleware
**Depends on:** Task 2
**Files:** `api/profile.controller.ts`

### Task 4: Build ProfileForm component (M)
- Create form with validation
- Handle loading/error states
- Add avatar upload
**Depends on:** Task 1
**Files:** `components/profile/ProfileForm.tsx`

### Task 5: Integration & E2E tests (S)
- Test happy path
- Test validation errors
- Test unauthorized access
**Depends on:** Tasks 3, 4
```

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too large tasks | Can't track progress | Break into < 1 day tasks |
| Hidden dependencies | Blocked work | Map dependencies explicitly |
| Missing tests | Quality issues | Include test tasks |
| No acceptance criteria | Unclear "done" | Add specific criteria |

## Expected Output Quality: 9/10
