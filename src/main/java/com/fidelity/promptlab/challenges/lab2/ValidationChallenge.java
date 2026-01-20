package com.fidelity.promptlab.challenges.lab2;

import com.fidelity.promptlab.models.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * LAB 2 - LIBRARY PATTERNS: Input Validation
 *
 * Use prompt library patterns to create secure validation.
 *
 * Reference: prompt-library/security/input-validation.md
 *
 * Your task:
 * 1. Open the input-validation.md pattern
 * 2. Apply it to validate transaction requests
 * 3. Ensure security considerations are addressed
 */
public class ValidationChallenge {

    // Current validation - minimal
    public static List<String> validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();
        if (transaction.getSymbol() == null) errors.add("Symbol required");
        if (transaction.getQuantity() <= 0) errors.add("Quantity required");
        return errors;
    }

    // YOUR LIBRARY PATTERN PROMPT:
    // ____________________________________________
    // ____________________________________________
    // ____________________________________________

    // PASTE IMPROVED VALIDATION HERE:

    // SECURITY IMPROVEMENTS:
    // ____________________________________________
}
