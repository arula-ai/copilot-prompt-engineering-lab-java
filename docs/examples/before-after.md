# Before & After: Prompt Transformations

Real examples showing the impact of applying CRAFT.

## Example 1: Login Function

### Before (Bad Prompt)
```
Create a login function
```

**Copilot Output:** Generic function, no security, no types, no error handling.

### After (CRAFT Prompt)
```
Context: Express.js API for financial services using JWT with refresh tokens.
Role: Senior security-focused backend developer.
Action: Create login handler that:
1. Validates email/password inputs
2. Verifies credentials with bcrypt
3. Generates JWT (15min) and refresh token (7 days)
4. Sets httpOnly cookie for refresh
5. Returns access token in body
6. Logs attempts for audit

Format: TypeScript async function with types and JSDoc
Constraints: Rate limit 5/min/IP, don't leak email existence, sanitize inputs
```

**Result:** Production-ready, secure, well-documented code.

---

## Example 2: Test Generation

### Before
```
Write tests for this function
```

**Result:** 2-3 basic tests, no edge cases.

### After
```
Context: Jest test suite for calculatePortfolioReturn (pure function).
Role: QA engineer focused on edge cases.
Action: Generate tests covering:
1. Happy path: gains, losses, break-even
2. Edge cases: zero initial, negatives
3. Boundaries: tiny decimals, huge numbers
4. Invalid: NaN, undefined, null

Format: describe/it blocks with behavior-describing names
Constraints: Use toBeCloseTo, 12+ tests, independent tests
```

**Result:** 15 comprehensive tests with full coverage.

---

## Example 3: Bug Fix

### Before
```
Fix the bug
```

**Result:** Fixes one obvious issue, misses others.

### After
```
Context: formatCurrency function in financial app displaying portfolio values.
Role: Senior developer with i18n experience.
Action: Fix these issues:
1. Negative numbers show "$-50" instead of "-$50"
2. No thousand separators
3. Floating point errors (0.1+0.2)
4. Only handles USD/EUR

Format: TypeScript using Intl.NumberFormat
Constraints: Support 10+ currencies, handle null, round to cents
```

**Result:** All bugs fixed, internationalization added, properly typed.

---

## Key Takeaways

| Aspect | Bad Prompt | CRAFT Prompt |
|--------|-----------|--------------|
| Context | None | Full tech stack |
| Specificity | Vague verb | Numbered requirements |
| Output | Guess-based | Defined format |
| Quality | 3/10 | 9/10 |
| Iterations | 5+ | 1-2 |
