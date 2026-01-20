# Pattern: Unit Test Suite (Java)

**Category:** Testing
**Language:** Java 17 / JUnit 5
**Success Rate:** 91%
**Last Verified:** December 2025

## Prompt Template

```
Generate a comprehensive JUnit 5 test class for [target-class/method]:

Code to test:
[paste the code or describe it]

Test framework: JUnit 5 with AssertJ assertions
Mocking: [Mockito / none for pure functions]

Required test categories:
1. Happy path - [number] tests covering normal operation
2. Edge cases - [describe specific edges to test]
3. Error scenarios - [list exception conditions]
4. Boundary values - [list boundaries]

Assertions should verify:
- [what to assert-1]
- [what to assert-2]
- [what to assert-3]

Format:
- Use @Nested classes to group related tests
- Use @DisplayName for readable test descriptions
- Use @ParameterizedTest where appropriate
- Include @BeforeEach setup

Constraints:
- Tests must be independent (no shared mutable state)
- Each test should have single responsibility
- Use AssertJ fluent assertions
- Follow AAA pattern (Arrange-Act-Assert)
```

## Example Usage

```
Generate a comprehensive JUnit 5 test class for calculatePortfolioReturn:

Code to test:
public record PortfolioReturn(BigDecimal absoluteReturn, BigDecimal percentReturn) {}

public static PortfolioReturn calculatePortfolioReturn(
    BigDecimal initialValue,
    BigDecimal finalValue,
    BigDecimal dividends)

Test framework: JUnit 5 with AssertJ assertions
Mocking: None needed (pure function)

Required test categories:
1. Happy path - 3 tests covering normal gains, losses, break-even
2. Edge cases - zero initial value, null dividends, very large numbers
3. Error scenarios - null initialValue, null finalValue, ArithmeticException
4. Boundary values - BigDecimal.ZERO, small decimals, MAX_VALUE

Assertions should verify:
- Return record has correct absoluteReturn
- Return record has correct percentReturn
- Values are rounded to 2 decimal places
- Division by zero is handled appropriately

Format:
- Use @Nested classes: HappyPath, EdgeCases, ErrorScenarios, BoundaryValues
- Use @DisplayName for readable descriptions
- Use @ParameterizedTest with @CsvSource for multiple similar inputs

Constraints:
- Use AssertJ (assertThat().isEqualTo())
- Use BigDecimal.compareTo() for numeric equality
- No floating point comparisons
```

## Expected Output Quality: 9/10
