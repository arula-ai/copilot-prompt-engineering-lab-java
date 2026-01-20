# Pattern: Simplify Complex Expression

**Category:** Refactoring
**Language:** TypeScript, Java
**Success Rate:** 91%
**Last Verified:** December 2025

## Prompt Template

```
Simplify this complex expression in [function-name]:

Current code:
[paste complex conditional or expression]

The expression determines: [what it calculates/decides]
Business rules encoded: [list the rules]

Simplify by:
1. Extract explaining variables for: [sub-expressions]
2. Replace magic values with: [named constants]
3. Extract predicate methods for: [complex conditions]
4. Apply De Morgan's law if: [nested negations exist]

Requirements:
1. Maintain exact same behavior
2. Each extracted variable/method has descriptive name
3. Add comments only where business logic is non-obvious
4. Keep cyclomatic complexity under [target, e.g., 5]

Format: [TypeScript | Java] with extracted helpers
Constraints: [e.g., "No new dependencies", "Pure functions only"]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[function-name]` | Function containing complexity | isEligibleForTrade, validateOrder |
| `[what-it-decides]` | Purpose of expression | Eligibility check, Validation result |
| `[business-rules]` | Encoded domain rules | Age >= 18, Account active, KYC complete |
| `[sub-expressions]` | Parts to extract | Age check, Account status, Balance check |
| `[magic-values]` | Hardcoded numbers/strings | 18, 100000, 'ACTIVE' |

## Example Usage

### TypeScript

```
Simplify this complex expression in canExecuteTrade:

Current code:
function canExecuteTrade(user: User, trade: Trade, market: Market): boolean {
  return user.status === 'ACTIVE' &&
    user.kycStatus === 'VERIFIED' &&
    !user.suspended &&
    user.accountAge > 30 &&
    (user.tier === 'PREMIUM' || trade.amount < 100000) &&
    market.isOpen &&
    !(market.volatilityIndex > 0.3 && trade.type === 'MARGIN') &&
    (trade.symbol in market.tradableSymbols) &&
    (user.balance >= trade.amount * 1.1 || user.hasMarginAccount);
}

The expression determines: Whether a user can execute a specific trade
Business rules encoded:
- User must be active, verified, not suspended, account > 30 days
- Premium users can trade any amount, others limited to $100k
- Market must be open
- No margin trades during high volatility (>30%)
- Symbol must be tradable
- User needs 110% balance or margin account

Simplify by:
1. Extract explaining variables for: user eligibility, trade eligibility, market conditions
2. Replace magic values with: ACCOUNT_MIN_AGE_DAYS, STANDARD_TRADE_LIMIT, VOLATILITY_THRESHOLD, MARGIN_BUFFER
3. Extract predicate methods for: isUserEligible, isTradeAllowed, isMarketSuitable
4. Apply De Morgan's law if: the nested negation on margin/volatility

Requirements:
1. Maintain exact same behavior (write test first)
2. Each method name reads like a business rule
3. Constants grouped in TradeConstants object
4. Keep main function under 5 lines

Format: TypeScript with extracted helper functions
Constraints: Pure functions, no side effects, tree-shakable
```

### Java

```
Simplify this complex expression in isOrderValid:

Current code:
public boolean isOrderValid(Order order, Account account, LocalDateTime now) {
    return order != null &&
        order.getItems() != null &&
        !order.getItems().isEmpty() &&
        order.getItems().stream().allMatch(i -> i.getQuantity() > 0 && i.getPrice().compareTo(BigDecimal.ZERO) > 0) &&
        account != null &&
        account.getStatus().equals("ACTIVE") &&
        (account.getCreditLimit().subtract(account.getBalance()).compareTo(order.getTotal()) >= 0 ||
         account.getType().equals("PREPAID") && account.getPrepaidBalance().compareTo(order.getTotal()) >= 0) &&
        !order.getRequestedDelivery().isBefore(now.plusDays(1)) &&
        (order.getShippingAddress().getCountry().equals(account.getCountry()) || account.hasInternationalShipping());
}

The expression determines: Whether an order can be processed
Business rules encoded:
- Order and items must exist and be valid (positive qty/price)
- Account must be active
- Sufficient credit OR prepaid balance for prepaid accounts
- Delivery at least 1 day out
- Domestic shipping OR international shipping enabled

Simplify by:
1. Extract explaining variables for: order validity, account eligibility, payment coverage, delivery feasibility
2. Replace magic values with: MIN_DELIVERY_DAYS constant
3. Extract predicate methods for: hasValidItems, hasSufficientFunds, isDeliveryFeasible
4. Apply De Morgan's law if: any nested negations

Requirements:
1. Maintain exact same behavior with unit tests
2. Predicate methods as private helpers
3. Use Optional where null checks extracted
4. Aim for cyclomatic complexity < 4

Format: Java with private predicate methods
Constraints: No new dependencies, null-safe, thread-safe
```

## Simplification Techniques

| Technique | When to Apply | Example |
|-----------|--------------|---------|
| Explaining variable | Sub-expression used twice or unclear | `const isAdult = age >= 18` |
| Predicate method | Condition embodies business rule | `isAccountInGoodStanding()` |
| Named constant | Magic number with meaning | `const VOLATILITY_THRESHOLD = 0.3` |
| De Morgan's law | Nested negations | `!(a && b)` → `!a \|\| !b` |
| Early return | Deeply nested conditionals | Guard clauses at top |
| Decompose conditional | Large if/else blocks | Extract each branch |

## Expected Output Quality: 9/10

A well-crafted prompt produces:
- Readable, self-documenting code
- Business rules visible in method/variable names
- Testable helper functions
- Maintained original behavior
