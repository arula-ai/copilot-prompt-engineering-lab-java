---
agent: 'agent'
description: 'Creates a detailed implementation plan before coding - supports CRAFT framework planning phase'
tools: ['search/codebase', 'search/changes', 'read/problems']
argument-hint: 'Provide: feature description or user story to plan'
---

# Implementation Plan Generator

**Category:** Workflow | **Success Rate:** 88%

Create a structured implementation plan before writing code. This supports the **CRAFT framework** by ensuring thorough planning.

## Required Information

Please provide:
- **Feature/Task description**: What needs to be built or changed
- **User story** (if available): "As a [user], I want [goal] so that [benefit]"
- **Acceptance criteria**: How we know it's done
- **Constraints**: Time, technology, or business constraints

## Plan Structure

### 1. Goal Statement
- 3-5 sentence overview of what will be implemented
- Why this feature/change is needed
- Expected outcome

### 2. Requirements Analysis
- Functional requirements (what it must do)
- Non-functional requirements (performance, security, scalability)
- Out of scope (explicit boundaries)

### 3. Technical Approach
- Architecture decisions and rationale
- Technology choices (if any)
- Integration points with existing code
- Data model changes (if any)

### 4. Implementation Phases
Break down into atomic, testable phases:

| Phase | Tasks | Files Affected | Tests Required |
|-------|-------|----------------|----------------|
| 1. Setup | Create interfaces, types | `types/*.ts` | Type tests |
| 2. Core | Implement business logic | `services/*.ts` | Unit tests |
| 3. API | Add endpoints/routes | `api/*.ts` | Integration tests |
| 4. UI | Build components | `components/*.tsx` | Component tests |

### 5. Testing Strategy
- Unit test coverage targets
- Integration test scenarios
- Edge cases to cover
- Manual testing checklist

### 6. Risks and Mitigations
- Technical risks identified
- Dependencies on other teams/systems
- Mitigation strategies

### 7. Definition of Done
- [ ] All acceptance criteria met
- [ ] Tests passing (specify coverage)
- [ ] Code reviewed
- [ ] Documentation updated
- [ ] No new security vulnerabilities

## Output Format

Generate a markdown document with:
- Clear section headers
- Mermaid diagrams for architecture (if helpful)
- Task tables with specific file paths
- Checklist for tracking progress

---

## Planning Best Practices

| Practice | Why |
|----------|-----|
| Start with "why" | Ensures alignment on purpose |
| Define scope boundaries | Prevents scope creep |
| Identify unknowns early | Allows time for research |
| Break into small phases | Enables incremental progress |
| Include rollback plan | Reduces deployment risk |

## Example Output Structure

```markdown
# Implementation Plan: [Feature Name]

## Goal
[3-5 sentences describing the feature and its value]

## Requirements
### Functional
- [ ] REQ-001: User can...
- [ ] REQ-002: System must...

### Non-Functional
- [ ] NFR-001: Response time < 200ms
- [ ] NFR-002: Support 1000 concurrent users

## Technical Approach
[Architecture decisions with rationale]

## Implementation Phases
| Phase | Description | Estimate |
|-------|-------------|----------|
| 1 | Setup types and interfaces | 2h |
| 2 | Implement core service | 4h |
| 3 | Add API endpoint | 2h |
| 4 | Write tests | 3h |

## Risks
| Risk | Impact | Mitigation |
|------|--------|------------|
| External API changes | High | Version pin, add adapter layer |
```

## Expected Output Quality: 9/10
