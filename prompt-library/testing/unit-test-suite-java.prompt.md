---
agent: 'agent'
description: 'Generates a comprehensive JUnit 5 test suite with Mockito, nested classes, and parameterized tests'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: class/method name to test, or paste the code'
---

# Unit Test Suite Pattern (Java)

**Category:** Testing | **Language:** Java 17 / JUnit 5 | **Success Rate:** 91%

Generate a comprehensive JUnit 5 test class with the following specifications:

## Required Information

Please provide:
- **Target**: Class or method name to test
- **Code to test**: Paste the code OR describe the signature
- **Code type**: Pure function, Spring @Service, @RestController, Class with dependencies

## Mocking Strategy

- **Dependencies to mock**: List repositories, clients, services to mock
- **Mock library**: Mockito with @Mock and @InjectMocks
- **Spy vs Mock**: Mock for full replacement, Spy for partial mocking

## Required Test Categories

1. **Happy path**: found, created, calculated correctly
2. **Edge cases**: empty list, null fields, very long strings
3. **Error scenarios**: EntityNotFoundException, ValidationException, ServiceUnavailable
4. **Boundary values**: BigDecimal.ZERO, empty collections, max values

## Assertions Should Verify

- Repository/service called with correct parameters
- Return values match expected
- Exceptions thrown for error conditions
- BigDecimal calculations use proper scale/rounding
- State changes persisted correctly

## Coverage Targets

- Line coverage: 90%+
- Branch coverage: 85%+
- Critical paths: 100%

## Format Requirements

- @Nested classes to group tests by method
- @DisplayName for readable test descriptions
- @ParameterizedTest with @CsvSource for data-driven tests
- @BeforeEach for mock setup
- AssertJ fluent assertions (assertThat)
- AAA pattern (Arrange-Act-Assert)

## Constraints

- Use @ExtendWith(MockitoExtension.class)
- Tests must be independent (no shared mutable state)
- Single responsibility per test
- Use isEqualByComparingTo() for BigDecimal, NOT equals()
- Verify mock interactions where important

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| BigDecimal.equals() | `new BigDecimal("1.0").equals(new BigDecimal("1.00"))` is false | Use isEqualByComparingTo() |
| Missing @ExtendWith | Mocks not initialized | Add @ExtendWith(MockitoExtension.class) |
| Shared mutable state | Tests affect each other | Reset in @BeforeEach |
| Over-mocking | Tests pass but code fails | Use integration tests for critical paths |

## Example Structure

```java
@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {
    @Mock private PortfolioRepository repository;
    @InjectMocks private PortfolioService service;

    @Nested
    @DisplayName("findById")
    class FindById {
        @Test
        @DisplayName("returns portfolio when found")
        void returnsPortfolioWhenFound() {
            // Arrange
            when(repository.findById("1")).thenReturn(Optional.of(expected));
            // Act
            var result = service.findById("1");
            // Assert
            assertThat(result).isPresent().contains(expected);
        }
    }
}
```

## Expected Output Quality: 9/10
