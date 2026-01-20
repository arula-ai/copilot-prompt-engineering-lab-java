package com.fidelity.promptlab.challenges.lab2;

import com.fidelity.promptlab.models.Portfolio;
import com.fidelity.promptlab.models.Holding;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * LAB 2 - LIBRARY PATTERNS: Portfolio Service
 *
 * Use prompt library patterns to generate improved service methods.
 *
 * Reference: prompt-library/code-generation/api-service-method.md
 *
 * CHALLENGE: The existing PortfolioService.getPortfolio() method has issues:
 * - No @Cacheable annotation (repeated calls hit database)
 * - No @Retryable for transient failures
 * - No proper exception handling
 * - Missing @Transactional for database operations
 *
 * YOUR TASK:
 * 1. Open prompt-library/code-generation/api-service-method.md
 * 2. Adapt the template for Java/Spring:
 *    - [service-name]: PortfolioService
 *    - [method-name]: findPortfolioById
 *    - [endpoint]: Repository call to portfolioRepository.findById(id)
 *    - [parameters]: portfolioId: String
 *    - [return-type]: Optional<Portfolio>
 *    - [caching]: @Cacheable("portfolios")
 *    - [retry]: @Retryable with 3 attempts, exponential backoff
 *    - [errors]: PortfolioNotFoundException, DataAccessException handling
 * 3. Use your crafted prompt in Copilot
 * 4. Paste the result below and compare quality
 */
@Service
public class PortfolioServiceChallenge {

    // ============================================================
    // EXISTING IMPLEMENTATION (for comparison)
    // ============================================================

    /**
     * Issues: No caching, no retry, no proper error handling
     */
    public Optional<Portfolio> getPortfolioOriginal(String portfolioId) {
        // return portfolioRepository.findById(portfolioId);
        return Optional.empty(); // Placeholder
    }

    // ============================================================
    // YOUR IMPROVED IMPLEMENTATION
    // ============================================================

    /**
     * YOUR LIBRARY PATTERN PROMPT (paste what you used):
     * ─────────────────────────────────────────────────
     *
     *
     *
     * ─────────────────────────────────────────────────
     */

    // PASTE YOUR IMPROVED findPortfolioById METHOD HERE:


    /**
     * IMPROVEMENTS CHECKLIST - mark what your generated code includes:
     * [ ] Returns Optional<Portfolio>
     * [ ] Has @Cacheable annotation with proper key
     * [ ] Has @Retryable with exponential backoff
     * [ ] Only retries on transient exceptions
     * [ ] Has @Transactional(readOnly = true)
     * [ ] Throws domain-specific exceptions
     * [ ] Has proper Javadoc documentation
     * [ ] Logs appropriately (method entry/exit, errors)
     */

    // ============================================================
    // BONUS CHALLENGE: Generate tests using testing pattern
    // ============================================================
    // Reference: prompt-library/testing/unit-test-service.md
}
