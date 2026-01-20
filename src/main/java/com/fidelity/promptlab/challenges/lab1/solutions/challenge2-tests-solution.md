# Challenge 2: Test Generation - SOLUTION

## Bad Prompt
```
Write tests for this
```

## Expert CRAFT Prompt

```
Context:
- JUnit 5 test suite for Spring Boot financial application
- Testing calculatePortfolioReturn method using BigDecimal
- Method calculates absolute and percent returns from initial/final values
- Financial precision required (uses BigDecimal with HALF_UP rounding)

Role: QA engineer with expertise in financial calculations and BigDecimal precision testing.

Action: Generate comprehensive test suite covering:
1. Happy path (4 tests):
   - Positive return (gain)
   - Negative return (loss)
   - Break-even (zero return)
   - With dividends included
2. Edge cases (5 tests):
   - Zero initial value (ArithmeticException handling)
   - Very small values (0.01 cents)
   - Very large values (billions)
   - Negative initial value
   - All zeros except initial
3. Boundary values (3 tests):
   - BigDecimal precision limits
   - Rounding behavior verification
   - Scale handling (2 vs 4 decimal places)
4. Invalid inputs (4 tests):
   - Null initialValue
   - Null finalValue
   - Null dividends (should default to zero)
   - All nulls

Format: JUnit 5 with:
- @Nested classes for test categories
- @DisplayName for readable test names
- @ParameterizedTest with @CsvSource for boundary tests
- AssertJ assertions (assertThat, isEqualTo, isCloseTo)
- AAA pattern (Arrange-Act-Assert) with comments

Constraints:
- Use BigDecimal.valueOf() for test values (not new BigDecimal(double))
- Compare BigDecimal with compareTo() or AssertJ isEqualByComparingTo()
- Each test must be independent (no shared mutable state)
- Test names should read like specifications
- Minimum 16 tests total
```

## Why This Works

| CRAFT Element | What It Provides | Impact |
|---------------|------------------|--------|
| **Context** | Framework, function purpose, domain | AI understands BigDecimal testing |
| **Role** | QA + financial expertise | Gets thorough precision coverage |
| **Action** | Explicit test categories with counts | Guarantees comprehensive coverage |
| **Format** | JUnit 5 features, AssertJ | Modern idiomatic Java tests |
| **Constraints** | BigDecimal best practices | Avoids common precision bugs |

## Expected Output Quality: 9/10

```java
@DisplayName("calculatePortfolioReturn")
class CalculatePortfolioReturnTest {

    @Nested
    @DisplayName("Happy Path")
    class HappyPath {

        @Test
        @DisplayName("calculates positive return for portfolio gain")
        void calculatesPositiveReturn() {
            // Arrange
            BigDecimal initial = BigDecimal.valueOf(10000);
            BigDecimal finalVal = BigDecimal.valueOf(12000);
            BigDecimal dividends = BigDecimal.ZERO;

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, dividends);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("2000.00");
            assertThat(result.percentReturn()).isEqualByComparingTo("20.00");
        }

        @Test
        @DisplayName("calculates negative return for portfolio loss")
        void calculatesNegativeReturn() {
            // Arrange
            BigDecimal initial = BigDecimal.valueOf(10000);
            BigDecimal finalVal = BigDecimal.valueOf(8000);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("-2000.00");
            assertThat(result.percentReturn()).isEqualByComparingTo("-20.00");
        }

        @Test
        @DisplayName("returns zero for break-even scenario")
        void returnsZeroForBreakEven() {
            // Arrange
            BigDecimal initial = BigDecimal.valueOf(10000);
            BigDecimal finalVal = BigDecimal.valueOf(10000);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("0.00");
            assertThat(result.percentReturn()).isEqualByComparingTo("0.00");
        }

        @Test
        @DisplayName("includes dividends in return calculation")
        void includesDividendsInReturn() {
            // Arrange - $10k initial, $10k final, $500 dividends = $500 gain (5%)
            BigDecimal initial = BigDecimal.valueOf(10000);
            BigDecimal finalVal = BigDecimal.valueOf(10000);
            BigDecimal dividends = BigDecimal.valueOf(500);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, dividends);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("500.00");
            assertThat(result.percentReturn()).isEqualByComparingTo("5.00");
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("throws ArithmeticException for zero initial value")
        void throwsForZeroInitialValue() {
            // Arrange
            BigDecimal initial = BigDecimal.ZERO;
            BigDecimal finalVal = BigDecimal.valueOf(1000);

            // Act & Assert
            assertThatThrownBy(() -> calculatePortfolioReturn(initial, finalVal, null))
                .isInstanceOf(ArithmeticException.class);
        }

        @Test
        @DisplayName("handles very small values with precision")
        void handlesVerySmallValues() {
            // Arrange - 1 cent to 2 cents = 100% gain
            BigDecimal initial = BigDecimal.valueOf(0.01);
            BigDecimal finalVal = BigDecimal.valueOf(0.02);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("0.01");
            assertThat(result.percentReturn()).isEqualByComparingTo("100.00");
        }

        @Test
        @DisplayName("handles very large values (billions)")
        void handlesVeryLargeValues() {
            // Arrange
            BigDecimal initial = new BigDecimal("1000000000.00"); // $1B
            BigDecimal finalVal = new BigDecimal("1100000000.00"); // $1.1B

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert
            assertThat(result.absoluteReturn()).isEqualByComparingTo("100000000.00");
            assertThat(result.percentReturn()).isEqualByComparingTo("10.00");
        }
    }

    @Nested
    @DisplayName("Boundary Values")
    class BoundaryValues {

        @ParameterizedTest(name = "initial={0}, final={1}, dividends={2} -> {3}%")
        @CsvSource({
            "100.00, 100.01, 0.00, 0.01",    // Minimal gain
            "100.00, 99.99, 0.00, -0.01",    // Minimal loss
            "0.01, 0.01, 0.00, 0.00",        // Smallest possible break-even
            "99999999.99, 100000000.00, 0.00, 0.00" // Large number rounding
        })
        @DisplayName("handles boundary values correctly")
        void handlesBoundaryValues(String initial, String finalVal, String divs, String expectedPct) {
            // Act
            PortfolioReturn result = calculatePortfolioReturn(
                new BigDecimal(initial),
                new BigDecimal(finalVal),
                new BigDecimal(divs)
            );

            // Assert
            assertThat(result.percentReturn()).isEqualByComparingTo(expectedPct);
        }

        @Test
        @DisplayName("rounds to 2 decimal places using HALF_UP")
        void roundsCorrectlyWithHalfUp() {
            // Arrange - 1/3 gain should round properly
            BigDecimal initial = BigDecimal.valueOf(300);
            BigDecimal finalVal = BigDecimal.valueOf(400);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert - 100/300 = 33.333...% should round to 33.33%
            assertThat(result.percentReturn()).isEqualByComparingTo("33.33");
        }
    }

    @Nested
    @DisplayName("Invalid Inputs")
    class InvalidInputs {

        @Test
        @DisplayName("throws NullPointerException for null initial value")
        void throwsForNullInitial() {
            assertThatThrownBy(() -> calculatePortfolioReturn(null, BigDecimal.TEN, null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("throws NullPointerException for null final value")
        void throwsForNullFinal() {
            assertThatThrownBy(() -> calculatePortfolioReturn(BigDecimal.TEN, null, null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("treats null dividends as zero")
        void treatsNullDividendsAsZero() {
            // Arrange
            BigDecimal initial = BigDecimal.valueOf(100);
            BigDecimal finalVal = BigDecimal.valueOf(110);

            // Act
            PortfolioReturn result = calculatePortfolioReturn(initial, finalVal, null);

            // Assert - same as with zero dividends
            assertThat(result.percentReturn()).isEqualByComparingTo("10.00");
        }
    }
}
```

## Key Testing Patterns

| Pattern | Purpose | Example |
|---------|---------|---------|
| `@Nested` | Group related tests | EdgeCases, BoundaryValues |
| `@DisplayName` | Readable test output | "calculates positive return for portfolio gain" |
| `@ParameterizedTest` | Data-driven tests | Boundary values with CSV data |
| `isEqualByComparingTo` | BigDecimal comparison | Ignores scale differences |
| `assertThatThrownBy` | Exception testing | Clean exception assertions |

## Common BigDecimal Testing Mistakes

| Mistake | Why It's Bad | Correct Approach |
|---------|--------------|------------------|
| `assertEquals(expected, actual)` | Scale mismatch fails | `isEqualByComparingTo()` |
| `new BigDecimal(0.1)` | Creates 0.1000000000000000055... | `BigDecimal.valueOf(0.1)` |
| `result.equals(expected)` | Different scales != equal | `compareTo() == 0` |
