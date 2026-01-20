package com.fidelity.promptlab.challenges.lab1;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * LAB 1 - CHALLENGE 5: Code Optimization
 *
 * BAD PROMPT: "Optimize this code"
 *
 * This method works but is inefficient.
 */
public class Challenge5Optimize {

    public record TransactionRecord(
            String id,
            String symbol,
            int quantity,
            BigDecimal price,
            LocalDateTime timestamp
    ) {}

    // O(n²) comparison
    public static List<String[]> findDuplicateTransactions(List<TransactionRecord> transactions) {
        List<String[]> duplicates = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            for (int j = i + 1; j < transactions.size(); j++) {
                TransactionRecord t1 = transactions.get(i);
                TransactionRecord t2 = transactions.get(j);

                // Check if potentially duplicate (same symbol, quantity, price within 1 minute)
                if (t1.symbol().equals(t2.symbol()) &&
                        t1.quantity() == t2.quantity() &&
                        t1.price().compareTo(t2.price()) == 0 &&
                        Duration.between(t1.timestamp(), t2.timestamp()).abs().toMinutes() < 1) {

                    duplicates.add(new String[]{t1.id(), t2.id()});
                }
            }
        }

        return duplicates;
    }

    /**
     * Your task: Create a prompt that optimizes for:
     * - Time complexity (use HashMap for grouping?)
     * - Memory efficiency (streaming?)
     * - Readability (Java 17 features?)
     * - Maintainability
     *
     * Be specific about what "optimize" means!
     */

    // YOUR IMPROVED PROMPT:
    // ____________________________________________
    // ____________________________________________
    // ____________________________________________

    // QUALITY RATING (1-10): ___
}
