package com.fidelity.promptlab.challenges.lab1;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * LAB 1 - CHALLENGE 2: Test Generation
 *
 * BAD PROMPT: "Write tests for this"
 *
 * Target code to test:
 */
public class Challenge2Tests {

    /**
     * Calculate portfolio return including dividends.
     */
    public static PortfolioReturn calculatePortfolioReturn(
            BigDecimal initialValue,
            BigDecimal finalValue,
            BigDecimal dividends) {

        if (dividends == null) {
            dividends = BigDecimal.ZERO;
        }

        BigDecimal absoluteReturn = finalValue.subtract(initialValue).add(dividends);
        BigDecimal percentReturn = absoluteReturn
                .divide(initialValue, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        return new PortfolioReturn(
                absoluteReturn.setScale(2, RoundingMode.HALF_UP),
                percentReturn
        );
    }

    public record PortfolioReturn(BigDecimal absoluteReturn, BigDecimal percentReturn) {}

    /**
     * Your task: Create a specific prompt that generates comprehensive JUnit 5 tests
     *
     * Consider:
     * - What test framework? (JUnit 5, AssertJ)
     * - What edge cases exist? (BigDecimal precision, null, zero)
     * - What assertions are meaningful?
     * - What's the coverage goal?
     */

    // YOUR IMPROVED PROMPT:
    // ____________________________________________
    // ____________________________________________
    // ____________________________________________

    // QUALITY RATING (1-10): ___
}
