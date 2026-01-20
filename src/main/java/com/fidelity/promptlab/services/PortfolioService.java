package com.fidelity.promptlab.services;

import com.fidelity.promptlab.models.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for Lab 2 exercises.
 * Participants will generate better versions of these methods using library patterns.
 *
 * INTENTIONAL ISSUES:
 * 1. No @Cacheable on frequently-called methods
 * 2. No @Retryable for transient failures
 * 3. No proper error handling/transformation
 * 4. No @Transactional for database operations
 * 5. Performance issues with large data sets
 * 6. No pagination support
 */
@Service
public class PortfolioService {

    // Challenge: In-memory storage - should use PortfolioRepository
    private final Map<String, Portfolio> portfolios = new HashMap<>();
    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    // Challenge: Should be @Cacheable("portfolios")
    // Should return Optional<Portfolio>
    public Optional<Portfolio> getPortfolio(String portfolioId) {
        return Optional.ofNullable(portfolios.get(portfolioId));
    }

    // Challenge: Fetches all then filters - should use query
    // Should be: portfolioRepository.findByUserId(userId)
    public List<Portfolio> getPortfoliosByUser(String userId) {
        return portfolios.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .collect(Collectors.toList());
        // Bug: Fetches all portfolios to find user's - O(n) instead of indexed query
    }

    // Challenge: Simplistic calculation
    // - No null safety
    // - No rounding for currency
    // - No memoization
    public BigDecimal calculateTotalValue(List<Holding> holdings) {
        BigDecimal total = BigDecimal.ZERO;
        for (Holding h : holdings) {
            // Bug: No null check on getCurrentPrice()
            total = total.add(h.getCurrentPrice().multiply(BigDecimal.valueOf(h.getQuantity())));
        }
        return total;
        // Bug: Should round to 2 decimal places for currency
    }

    // Challenge: No caching, no retry, no error handling
    // Should use: @Cacheable, @Retryable, proper exception handling
    // Should call: external market data API with circuit breaker
    public Map<String, BigDecimal> fetchMarketPrices(List<String> symbols) {
        // Simulated API call - in reality would use WebClient or RestTemplate
        Map<String, BigDecimal> prices = new HashMap<>();
        Random random = new Random();
        for (String symbol : symbols) {
            prices.put(symbol, BigDecimal.valueOf(random.nextDouble() * 1000));
        }
        return prices;
        // Bug: Random prices, no caching, no retry, no timeout
    }

    // Challenge: No transaction validation
    // Should: validate transaction, check sufficient funds, use @Transactional
    public Transaction recordTransaction(Transaction transaction) {
        String id = UUID.randomUUID().toString().substring(0, 7);
        transaction.setId(id);

        List<Transaction> existing = transactions.computeIfAbsent(
                transaction.getPortfolioId(), k -> new ArrayList<>());
        existing.add(transaction);

        return transaction;
        // Bug: No validation, no idempotency key, no event publishing
    }

    // Challenge: Performance issues with large portfolios
    // - Fetches ALL transactions then filters client-side
    // - No pagination
    // - No server-side date filtering
    public List<Transaction> getTransactionHistory(
            String portfolioId,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        List<Transaction> all = transactions.getOrDefault(portfolioId, new ArrayList<>());

        // Bug: Inefficient client-side filtering
        return all.stream()
                .filter(t -> {
                    if (startDate != null && t.getExecutedAt().isBefore(startDate)) return false;
                    if (endDate != null && t.getExecutedAt().isAfter(endDate)) return false;
                    return true;
                })
                .collect(Collectors.toList());
        // Should: use repository query with date parameters
    }
}
