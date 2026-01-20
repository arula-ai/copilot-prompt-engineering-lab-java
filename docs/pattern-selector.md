# Pattern Selector Guide

Use this guide to quickly find the right prompt pattern for your task.

## Quick Decision Tree

```
What do you need to do?
│
├─► Create new code
│   │
│   ├─► API/HTTP service? ──────────► api-service-method.prompt.md
│   │                                  (or api-service-method-java.prompt.md)
│   │
│   ├─► Transform data? ────────────► data-transformer.prompt.md
│   │
│   ├─► Input validation? ──────────► input-validation.prompt.md
│   │                                  (or input-validation-java.prompt.md)
│   │
│   └─► Route protection? ──────────► auth-guard.prompt.md
│
├─► Write tests
│   │
│   └─► Unit tests? ────────────────► unit-test-suite.prompt.md
│                                      (or unit-test-suite-java.prompt.md)
│
├─► Improve existing code
│   │
│   ├─► Code review needed? ────────► review-and-refactor.prompt.md
│   │
│   ├─► Method too long? ───────────► extract-method.prompt.md
│   │
│   ├─► Too many parameters? ───────► introduce-parameter-object.prompt.md
│   │
│   ├─► Complex if/switch? ─────────► replace-conditional-with-polymorphism.prompt.md
│   │
│   └─► Complex boolean logic? ─────► simplify-complex-expression.prompt.md
│
├─► Add documentation
│   │
│   ├─► Code comments/docs? ────────► jsdoc-generation.prompt.md
│   │
│   └─► README file? ───────────────► readme-generator.prompt.md
│
└─► Plan/organize work
    │
    ├─► Commit message? ────────────► conventional-commit.prompt.md
    │
    ├─► Implementation plan? ───────► implementation-plan.prompt.md
    │
    └─► Break down feature? ────────► feature-breakdown.prompt.md
```

## Pattern Selection Matrix

Find patterns by matching your **task type** with **required features**.

### Code Generation Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [api-service-method](../prompt-library/code-generation/api-service-method.prompt.md) | Building HTTP services | Caching, retry, error handling, cancellation | 94% |
| [api-service-method-java](../prompt-library/code-generation/api-service-method-java.prompt.md) | Spring Boot services | @Cacheable, Resilience4j, sealed interfaces | 93% |
| [data-transformer](../prompt-library/code-generation/data-transformer.prompt.md) | Converting between types | Type safety, null handling, validation | 91% |

### Testing Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [unit-test-suite](../prompt-library/testing/unit-test-suite.prompt.md) | Jest/Vitest tests | Edge cases, mocking, parameterized tests | 89% |
| [unit-test-suite-java](../prompt-library/testing/unit-test-suite-java.prompt.md) | JUnit 5 tests | @Nested, @ParameterizedTest, AssertJ, Mockito | 91% |

### Security Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [input-validation](../prompt-library/security/input-validation.prompt.md) | Validating user input | OWASP alignment, sanitization, typed errors | 88% |
| [input-validation-java](../prompt-library/security/input-validation-java.prompt.md) | Bean Validation | @NotNull, custom validators, sealed results | 90% |
| [auth-guard](../prompt-library/security/auth-guard.prompt.md) | Protecting routes | Role-based, token validation, redirects | 92% |

### Refactoring Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [review-and-refactor](../prompt-library/refactoring/review-and-refactor.prompt.md) | Code review with fixes | Priority issues, before/after, explanations | 87% |
| [extract-method](../prompt-library/refactoring/extract-method.prompt.md) | Long methods (>20 lines) | Single responsibility, naming, parameters | 90% |
| [introduce-parameter-object](../prompt-library/refactoring/introduce-parameter-object.prompt.md) | Too many params (>3) | DTO creation, validation, immutability | 88% |
| [replace-conditional-with-polymorphism](../prompt-library/refactoring/replace-conditional-with-polymorphism.prompt.md) | Complex switch/if chains | Strategy pattern, type hierarchy | 85% |
| [simplify-complex-expression](../prompt-library/refactoring/simplify-complex-expression.prompt.md) | Nested boolean logic | Extract variables, early returns | 91% |

### Documentation Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [jsdoc-generation](../prompt-library/documentation/jsdoc-generation.prompt.md) | Code documentation | @param, @returns, @example, @throws | 92% |
| [readme-generator](../prompt-library/documentation/readme-generator.prompt.md) | Project READMEs | Quick start, installation, API reference | 91% |

### Workflow Patterns

| Pattern | Use When | Key Features | Success Rate |
|---------|----------|--------------|--------------|
| [conventional-commit](../prompt-library/workflow/conventional-commit.prompt.md) | Writing commits | feat/fix/docs types, scope, breaking changes | 95% |
| [implementation-plan](../prompt-library/workflow/implementation-plan.prompt.md) | Planning before coding | Requirements, phases, risks, testing strategy | 88% |
| [feature-breakdown](../prompt-library/workflow/feature-breakdown.prompt.md) | Breaking down epics | Task sizing, dependencies, acceptance criteria | 90% |

## Common Pattern Combinations

These patterns work well together in sequence:

### Building a New Feature
```
1. implementation-plan.prompt.md    → Plan the approach
2. feature-breakdown.prompt.md      → Break into tasks
3. api-service-method.prompt.md     → Build the service
4. input-validation.prompt.md       → Add validation
5. unit-test-suite.prompt.md        → Write tests
6. jsdoc-generation.prompt.md       → Document code
7. conventional-commit.prompt.md    → Commit changes
```

### Code Review and Improvement
```
1. review-and-refactor.prompt.md    → Identify issues
2. extract-method.prompt.md         → Fix long methods
3. simplify-complex-expression.prompt.md → Clean up logic
4. unit-test-suite.prompt.md        → Add missing tests
```

### API Development
```
1. input-validation.prompt.md       → Validate inputs first
2. api-service-method.prompt.md     → Build the endpoint
3. auth-guard.prompt.md             → Protect the route
4. unit-test-suite.prompt.md        → Test everything
```

## Choosing Between Similar Patterns

### api-service-method vs api-service-method-java
| Choose | When |
|--------|------|
| `api-service-method` | TypeScript/Angular with RxJS Observables |
| `api-service-method-java` | Spring Boot with RestClient/WebClient |

### input-validation vs input-validation-java
| Choose | When |
|--------|------|
| `input-validation` | TypeScript with Zod/class-validator |
| `input-validation-java` | Java with Bean Validation annotations |

### unit-test-suite vs unit-test-suite-java
| Choose | When |
|--------|------|
| `unit-test-suite` | Jest, Vitest, or Jasmine |
| `unit-test-suite-java` | JUnit 5 with Mockito and AssertJ |

### extract-method vs simplify-complex-expression
| Choose | When |
|--------|------|
| `extract-method` | Method is too long, does multiple things |
| `simplify-complex-expression` | Logic is correct but hard to read |

### implementation-plan vs feature-breakdown
| Choose | When |
|--------|------|
| `implementation-plan` | Need technical approach and architecture decisions |
| `feature-breakdown` | Need task list with sizes and dependencies |

## Pattern Complexity Levels

### Simple (Start Here)
- conventional-commit (95%)
- jsdoc-generation (92%)
- simplify-complex-expression (91%)

### Medium
- api-service-method (94%)
- unit-test-suite (89%)
- extract-method (90%)
- input-validation (88%)

### Advanced
- replace-conditional-with-polymorphism (85%)
- review-and-refactor (87%)
- implementation-plan (88%)

## When NOT to Use a Pattern

| Situation | Why | Alternative |
|-----------|-----|-------------|
| Simple one-liner fix | Overhead not worth it | Direct prompt |
| Highly custom logic | Pattern too generic | Write custom CRAFT prompt |
| Exploratory coding | Need flexibility | Use Agent mode freely |
| Learning new concept | Pattern assumes knowledge | Ask Copilot to explain first |

## Need a Pattern That Doesn't Exist?

1. Check if an existing pattern can be adapted
2. Write a custom CRAFT prompt for your task
3. If you use it 3+ times, consider contributing it to the library (see Lab 3)

---

*Can't decide? Start with `review-and-refactor` - it will identify what patterns you need.*
