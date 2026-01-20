package com.fidelity.promptlab.services;

import com.fidelity.promptlab.models.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service with various issues for prompt engineering challenges.
 *
 * INTENTIONAL ISSUES:
 * 1. Minimal validation
 * 2. Overly simple fee calculation (no tiers)
 * 3. No state machine for transaction status
 * 4. Basic report generation
 * 5. Placeholder fraud detection
 * 6. Mutation of input objects
 */
@Service
public class TransactionService {

    // Challenge: Complex validation needed
    // - Only checks 2 fields
    // - No Bean Validation annotations
    // - No cross-field validation
    // - No async validation (symbol lookup)
    public List<String> validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();

        // Minimal validation - participants should improve with prompts
        if (transaction.getSymbol() == null || transaction.getSymbol().isEmpty()) {
            errors.add("Symbol required");
        }
        if (transaction.getQuantity() <= 0) {
            errors.add("Invalid quantity");
        }
        // Missing: price > 0, valid type, portfolio exists, sufficient funds,
        // symbol exists in market, market is open, etc.

        return errors;
    }

    // Challenge: Calculate fees - needs multiple fee tiers
    // - Flat 1% regardless of amount
    // - No volume discounts
    // - No fee caps
    // - No special handling for different transaction types
    public BigDecimal calculateFees(BigDecimal amount) {
        // Overly simple - should have tiered fees
        return amount.multiply(BigDecimal.valueOf(0.01));
        // Real implementation should consider:
        // - Volume tiers (>$10k = 0.5%, >$100k = 0.25%)
        // - Transaction type (dividends = 0%)
        // - Account type (premium = reduced fees)
        // - Minimum fee ($1)
        // - Maximum fee ($50)
    }

    // Challenge: Process transaction - needs proper state machine
    // - Direct status mutation (should be immutable)
    // - No status transition validation
    // - No idempotency
    // - No event emission
    public Transaction processTransaction(Transaction transaction) {
        // Bug: Mutates input object!
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        return transaction;
        // Should:
        // - Validate current status allows transition
        // - Create new transaction with updated status (immutable)
        // - Publish TransactionCompletedEvent
        // - Handle failures with compensation
    }

    // Challenge: Generate transaction report - needs formatting
    // - Very basic string output
    // - No currency formatting
    // - No totals
    // - No grouping by type
    // - No localization
    public String generateReport(List<Transaction> transactions) {
        // Very basic - participants should improve
        return transactions.stream()
                .map(t -> t.getType() + ": " + t.getSymbol() + " x" + t.getQuantity())
                .collect(Collectors.joining("\n"));
        // Should:
        // - Format currency with locale
        // - Calculate totals per type
        // - Group by date
        // - Support multiple formats (CSV, JSON, PDF)
        // - Include gain/loss calculations
    }

    // Challenge: Detect suspicious activity - needs pattern detection
    // - Always returns false (placeholder)
    // - No actual detection logic
    // - No configurable thresholds
    // - No ML model integration
    public boolean detectSuspiciousActivity(List<Transaction> transactions) {
        // Placeholder - needs real implementation
        return false;
        // Should detect:
        // - Rapid trading (>10 transactions per minute)
        // - Unusual amounts (>3 std dev from mean)
        // - Off-hours trading
        // - Circular transactions (A->B->C->A)
        // - Velocity anomalies
        // - Pattern matching against known fraud patterns
    }
}
