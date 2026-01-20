# Challenge 3: Bug Fix - SOLUTION

## Bad Prompt
```
Fix the bug
```

## Expert CRAFT Prompt

```
Context:
- Java financial application displaying portfolio values
- formatCurrency method used throughout for money display
- Must support multiple currencies and locales
- Current output examples showing bugs:
  - formatCurrency(1234.5, "USD")  -> "$1234.5" (should be "$1,234.50")
  - formatCurrency(-50, "USD")     -> "$-50.0" (should be "-$50.00")
  - formatCurrency(0.1 + 0.2, "USD") -> "$0.30000000000000004" (precision error)

Current implementation:
```java
public static String formatCurrency(double amount, String currency) {
    String symbol;
    if (currency.equals("USD")) {
        symbol = "$";
    } else if (currency.equals("EUR")) {
        symbol = "€";
    } else {
        symbol = currency;
    }
    return symbol + amount;
}
```

Role: Senior Java developer with internationalization (i18n) experience in financial applications.

Action: Fix these specific bugs:
1. BUG: No thousand separators
   FIX: Use NumberFormat.getCurrencyInstance() for locale-aware grouping
2. BUG: Negative sign in wrong position ("$-50" vs "-$50")
   FIX: NumberFormat handles this automatically per locale
3. BUG: Floating point precision errors (using double)
   FIX: Accept BigDecimal, or round double to 2 decimal places
4. BUG: Only handles USD/EUR symbols
   FIX: Use Currency.getInstance() for all ISO 4217 codes

Additionally:
5. Add input validation (null, invalid currency codes)
6. Support locale parameter (default to Locale.US)
7. Handle edge cases: zero, very large numbers, very small numbers

Format: Java method with:
- Overloaded methods: one accepting double, one accepting BigDecimal
- Full Javadoc with @param, @return, @throws, @example
- Pure function (no side effects)

Constraints:
- Must handle null amount gracefully (return formatted zero)
- Must validate currency code against Currency.getAvailableCurrencies()
- Must work with all locales (use Locale parameter)
- Must round to exactly 2 decimal places for most currencies
- Handle currencies with 0 decimals (JPY) and 3 decimals (BHD) correctly
```

## Why This Works

| CRAFT Element | What It Provides | Impact |
|---------------|------------------|--------|
| **Context** | Current bugs with examples | AI sees exactly what's wrong |
| **Role** | i18n + financial expertise | Gets proper NumberFormat usage |
| **Action** | Each bug listed with expected fix | All issues addressed |
| **Format** | Specific API, documentation needs | Clean, documented output |
| **Constraints** | Edge cases, currency validation | Production-ready code |

## Expected Output Quality: 9/10

```java
/**
 * Formats a monetary amount as a currency string with proper locale support.
 *
 * <p>Uses {@link NumberFormat#getCurrencyInstance(Locale)} for proper formatting
 * including thousand separators, negative sign placement, and currency symbol.
 *
 * @param amount the monetary amount to format (can be null, treated as zero)
 * @param currencyCode ISO 4217 currency code (e.g., "USD", "EUR", "JPY")
 * @param locale the locale for formatting (determines separators, symbol placement)
 * @return formatted currency string
 * @throws IllegalArgumentException if currencyCode is invalid
 *
 * @example formatCurrency(BigDecimal.valueOf(1234.50), "USD", Locale.US) -> "$1,234.50"
 * @example formatCurrency(BigDecimal.valueOf(-50), "USD", Locale.US) -> "-$50.00"
 * @example formatCurrency(BigDecimal.valueOf(1000), "EUR", Locale.GERMANY) -> "1.000,00 €"
 */
public static String formatCurrency(BigDecimal amount, String currencyCode, Locale locale) {
    // Handle null amount
    if (amount == null) {
        amount = BigDecimal.ZERO;
    }

    // Validate and get currency
    Currency currency;
    try {
        currency = Currency.getInstance(currencyCode);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid currency code: " + currencyCode, e);
    }

    // Get locale-specific currency formatter
    NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
    formatter.setCurrency(currency);

    // Currency determines decimal places (JPY=0, USD=2, BHD=3)
    formatter.setMinimumFractionDigits(currency.getDefaultFractionDigits());
    formatter.setMaximumFractionDigits(currency.getDefaultFractionDigits());

    return formatter.format(amount);
}

/**
 * Formats a double amount as currency, rounding to avoid floating-point errors.
 *
 * <p><b>Note:</b> Prefer the BigDecimal overload for financial calculations
 * to avoid precision loss.
 *
 * @param amount the amount to format
 * @param currencyCode ISO 4217 currency code
 * @param locale the locale for formatting
 * @return formatted currency string
 */
public static String formatCurrency(double amount, String currencyCode, Locale locale) {
    // Convert to BigDecimal with rounding to avoid floating-point precision issues
    // Round to 4 decimal places first, then let the formatter handle final rounding
    BigDecimal rounded = BigDecimal.valueOf(amount)
        .setScale(4, RoundingMode.HALF_UP);
    return formatCurrency(rounded, currencyCode, locale);
}

/**
 * Convenience method using US locale.
 */
public static String formatCurrency(BigDecimal amount, String currencyCode) {
    return formatCurrency(amount, currencyCode, Locale.US);
}
```

## Bug Analysis

### Why `double` Causes Precision Errors
```java
// The problem:
double result = 0.1 + 0.2;  // = 0.30000000000000004

// The fix:
BigDecimal result = BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.2));
// = 0.3 (exact)
```

### Why NumberFormat is Better Than Manual Formatting

| Feature | Manual String | NumberFormat |
|---------|---------------|--------------|
| Thousand separators | Must implement | Automatic |
| Negative sign placement | "-$50" (wrong) | "-$50.00" or "($50.00)" per locale |
| Currency symbols | Hard-coded | All ISO 4217 |
| Decimal places | Fixed | Per-currency (JPY=0, USD=2, BHD=3) |
| Locale support | None | Full i18n |

## Edge Case Tests

| Input | Expected Output (US) |
|-------|---------------------|
| `(0, "USD")` | `"$0.00"` |
| `(-0.001, "USD")` | `"$0.00"` (rounds to zero) |
| `(null, "USD")` | `"$0.00"` (safe default) |
| `(1000000, "JPY")` | `"¥1,000,000"` (no decimals) |
| `(1234.5, "EUR", GERMANY)` | `"1.234,50 €"` |
| `(1234.567, "BHD")` | `"BHD 1,234.567"` (3 decimals) |

## Currency-Specific Decimal Places

```java
Currency.getInstance("USD").getDefaultFractionDigits() // 2
Currency.getInstance("EUR").getDefaultFractionDigits() // 2
Currency.getInstance("JPY").getDefaultFractionDigits() // 0
Currency.getInstance("BHD").getDefaultFractionDigits() // 3
Currency.getInstance("CLF").getDefaultFractionDigits() // 4
```
