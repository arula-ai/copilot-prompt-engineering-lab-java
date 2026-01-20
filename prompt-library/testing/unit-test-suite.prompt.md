---
agent: 'agent'
description: 'Generates a comprehensive unit test suite with mocking, edge cases, and proper coverage for TypeScript/Jest'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: function/class name to test, or paste the code'
---

# Unit Test Suite Pattern

**Category:** Testing | **Success Rate:** 89%

Generate a comprehensive test suite with the following specifications:

## Required Information

Please provide:
- **Target**: Function, class, or method name to test
- **Code to test**: Paste the code OR describe the signature
- **Framework**: Jest, Vitest, or Angular TestBed with Jest
- **Code type**: Pure function, Service with HttpClient, Angular component, Class with dependencies

## Mocking Strategy

Specify what to mock:
- **Dependencies to mock**: e.g., HttpClient, AuthService, Database
- **Mock library**: jest.fn(), jest.mock(), Angular testing utilities
- **Spy vs Mock**: When to use spyOn vs full mocks

## Required Test Categories

1. **Happy path**: Normal operation tests (specify count)
2. **Edge cases**: null, undefined, empty arrays, special characters
3. **Error scenarios**: 401, 404, 500, network timeout, validation errors
4. **Boundary values**: 0, -1, MAX_SAFE_INTEGER, empty string
5. **Async behavior**: Timeouts, retries, cancellation (if applicable)

## Assertions Should Verify

- Return values match expected
- Correct methods called with correct arguments
- Errors properly thrown or transformed
- Side effects (state changes, calls to dependencies)
- Async behavior completes correctly

## Coverage Targets

- Line coverage: 90%+
- Branch coverage: 85%+
- Critical paths: 100%

## Format Requirements

- Use describe/it blocks with behavior-focused descriptions
- Group by scenario type (happy path, edge cases, errors)
- Include beforeEach setup with mock reset
- Use test.each for data-driven tests
- AAA pattern: Arrange-Act-Assert with comments

## Constraints

- Tests must be independent (no shared mutable state)
- Reset mocks in beforeEach
- Single responsibility per test
- Use toBeCloseTo for floating point comparisons
- No real HTTP calls (mock all external dependencies)

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Shared mutable state | Tests affect each other | Reset mocks in beforeEach |
| Testing implementation | Tests break on refactor | Test behavior, not implementation |
| Flaky async tests | Random failures | Use fakeAsync, tick(), waitFor() |
| Floating point equality | `0.1 + 0.2 !== 0.3` | Use toBeCloseTo with tolerance |
| Missing error paths | 100% happy path, 0% errors | Require error scenario tests |

## Expected Output Quality: 9/10
