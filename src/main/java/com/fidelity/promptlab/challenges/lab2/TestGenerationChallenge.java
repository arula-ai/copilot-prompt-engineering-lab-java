package com.fidelity.promptlab.challenges.lab2;

import com.fidelity.promptlab.models.Holding;

import java.math.BigDecimal;
import java.util.List;

/**
 * LAB 2 - LIBRARY PATTERNS: Test Generation
 *
 * Use prompt library patterns to generate comprehensive tests.
 *
 * Reference: prompt-library/testing/unit-test-suite.md
 *
 * CHALLENGE: Generate a comprehensive JUnit 5 test suite for calculateTotalValue.
 *
 * The method has potential issues to test:
 * - What happens with empty list?
 * - What about null list or null elements?
 * - BigDecimal precision and rounding?
 * - Negative quantities or prices?
 *
 * YOUR TASK:
 * 1. Open prompt-library/testing/unit-test-suite.md
 * 2. Adapt the template for Java/JUnit 5:
 *    - [function-name]: calculateTotalValue
 *    - [description]: Calculates total portfolio value from holdings
 *    - [inputs]: List<Holding> (symbol, quantity: int, currentPrice: BigDecimal)
 *    - [output]: BigDecimal (total value)
 *    - [edge-cases]: empty list, single item, zero values
 *    - [error-scenarios]: null list, null elements, null price
 *    - [framework]: JUnit 5 with AssertJ assertions
 * 3. Use your crafted prompt in Copilot
 * 4. Paste generated tests below and analyze coverage
 */
public class TestGenerationChallenge {

    // ============================================================
    // METHOD UNDER TEST
    // ============================================================

    /**
     * Calculates total portfolio value from holdings.
     * Known issues (intentional for testing challenge):
     * - No null checking on list
     * - No null checking on elements
     * - No null checking on price field
     * - No handling of negative values
     *
     * @param holdings list of portfolio holdings
     * @return total value as BigDecimal
     */
    public static BigDecimal calculateTotalValue(List<Holding> holdings) {
        return holdings.stream()
                .map(h -> h.getCurrentPrice().multiply(BigDecimal.valueOf(h.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ============================================================
    // YOUR GENERATED TESTS
    // ============================================================

    /**
     * YOUR LIBRARY PATTERN PROMPT (paste what you used):
     * ─────────────────────────────────────────────────
     *
     *
     *
     * ─────────────────────────────────────────────────
     */

    // PASTE GENERATED TEST CLASS HERE:
    // (Should be JUnit 5 tests with @Test, @DisplayName, @Nested)
    // Example structure:
    //
    // @Nested
    // @DisplayName("calculateTotalValue")
    // class CalculateTotalValueTest {
    //     @Test
    //     @DisplayName("returns zero for empty list")
    //     void emptyList() { ... }
    // }


    // ============================================================
    // COVERAGE ANALYSIS
    // ============================================================

    /**
     * COVERAGE CHECKLIST - mark what your generated tests include:
     *
     * HAPPY PATH:
     * [ ] Single holding with positive values
     * [ ] Multiple holdings summed correctly
     * [ ] Uses AssertJ's isEqualByComparingTo for BigDecimal
     *
     * EDGE CASES:
     * [ ] Empty list → returns BigDecimal.ZERO
     * [ ] Single holding → returns that holding's value
     * [ ] Holding with quantity = 0 → contributes ZERO
     * [ ] Holding with price = ZERO → contributes ZERO
     *
     * ERROR SCENARIOS:
     * [ ] Null list → throws NullPointerException
     * [ ] List with null element → throws NullPointerException
     * [ ] Holding with null price → throws NullPointerException
     * [ ] Negative quantity → documents behavior (allows or rejects)
     *
     * PRECISION:
     * [ ] Large values (millions) → no precision loss
     * [ ] Many decimal places → handled correctly
     * [ ] Uses proper BigDecimal comparison (not equals)
     *
     * TEST QUALITY:
     * [ ] Uses @DisplayName for readability
     * [ ] Uses @Nested for organization
     * [ ] Uses @ParameterizedTest where appropriate
     * [ ] Follows AAA pattern (Arrange-Act-Assert)
     *
     * TOTAL TESTS GENERATED: ___
     * QUALITY RATING: ___/10
     */
}
