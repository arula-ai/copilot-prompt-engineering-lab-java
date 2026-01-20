---
agent: 'agent'
description: 'Simplifies complex conditional expressions by extracting variables and predicate methods'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: function name and paste the complex expression to simplify'
---

# Simplify Complex Expression Pattern

**Category:** Refactoring | **Success Rate:** 91%

Simplify a complex expression with the following specifications:

## Required Information

Please provide:
- **Function name**: The function containing complexity
- **Current code**: Paste the complex conditional or expression
- **Language**: TypeScript or Java 17

## Analysis

Identify:
- **Expression purpose**: What it determines (e.g., eligibility, validity)
- **Business rules encoded**: List the rules (e.g., user must be active, amount < limit)

## Simplification Strategy

1. **Extract explaining variables** for sub-expressions
2. **Replace magic values** with named constants
3. **Extract predicate methods** for complex conditions
4. **Apply De Morgan's law** if nested negations exist

## Requirements

1. Maintain **exact same behavior** (write test first if needed)
2. Each extracted variable/method has descriptive name
3. Add comments only where business logic is non-obvious
4. Keep cyclomatic complexity under 5
5. Method names should read like business rules

## Format

- Pure functions, no side effects
- No new dependencies
- Tree-shakable (for TypeScript)

---

## Simplification Techniques

| Technique | When to Apply | Example |
|-----------|--------------|---------|
| Explaining variable | Sub-expression used twice or unclear | `const isAdult = age >= 18` |
| Predicate method | Condition embodies business rule | `isAccountInGoodStanding()` |
| Named constant | Magic number with meaning | `const VOLATILITY_THRESHOLD = 0.3` |
| De Morgan's law | Nested negations | `!(a && b)` → `!a \|\| !b` |
| Early return | Deeply nested conditionals | Guard clauses at top |

## Before/After Example

```typescript
// BEFORE: Complex, hard to understand
function canExecuteTrade(user, trade, market) {
  return user.status === 'ACTIVE' && user.kycStatus === 'VERIFIED' &&
    !user.suspended && user.accountAge > 30 &&
    (user.tier === 'PREMIUM' || trade.amount < 100000) &&
    market.isOpen && !(market.volatilityIndex > 0.3 && trade.type === 'MARGIN');
}

// AFTER: Self-documenting
const ACCOUNT_MIN_AGE_DAYS = 30;
const STANDARD_TRADE_LIMIT = 100_000;
const VOLATILITY_THRESHOLD = 0.3;

function canExecuteTrade(user, trade, market) {
  const isUserEligible = isActiveVerifiedUser(user) && hasMinimumAccountAge(user);
  const isTradeAllowed = isPremiumUser(user) || isWithinStandardLimit(trade);
  const isMarketSuitable = market.isOpen && !isHighVolatilityMarginTrade(market, trade);

  return isUserEligible && isTradeAllowed && isMarketSuitable;
}
```

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Over-extraction | Too many tiny methods | Group related checks |
| Behavior change | Accidentally altered logic | Test before and after |
| Unclear names | `isValid1`, `checkStuff` | Name describes business rule |

## Expected Output Quality: 9/10
