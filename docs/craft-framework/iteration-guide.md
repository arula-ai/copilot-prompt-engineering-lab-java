# Prompt Iteration Guide

Expert prompts rarely work perfectly on the first try. This guide teaches you how to analyze Copilot's output and refine your prompts iteratively.

## The Iteration Loop

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│    ┌──────────┐     ┌──────────┐     ┌──────────┐          │
│    │  Write   │────►│  Review  │────►│ Identify │          │
│    │  Prompt  │     │  Output  │     │   Gap    │          │
│    └──────────┘     └──────────┘     └──────────┘          │
│         ▲                                  │                │
│         │                                  ▼                │
│    ┌──────────┐                      ┌──────────┐          │
│    │  Refine  │◄─────────────────────│ Diagnose │          │
│    │  CRAFT   │                      │  Cause   │          │
│    └──────────┘                      └──────────┘          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Goal:** Reach 9/10 quality in 1-3 iterations, not endless tweaking.

## Step 1: Review Output Against Requirements

Before refining, systematically check what's wrong:

| Check | Question | If Missing |
|-------|----------|------------|
| **Functionality** | Does it do what was asked? | Action gap |
| **Completeness** | Are all requirements addressed? | Action gap |
| **Format** | Is output in requested format? | Format gap |
| **Quality** | Is it production-ready? | Context/Constraint gap |
| **Edge Cases** | Are boundaries handled? | Action gap |
| **Security** | Are vulnerabilities avoided? | Constraint gap |

## Step 2: Diagnose the Cause

Match the problem to the CRAFT element that needs refinement:

### Context Gaps → Generic or Wrong Output

**Symptoms:**
- Output uses wrong framework/library
- Doesn't integrate with existing code
- Uses outdated patterns

**Example Problem:**
```
Prompt: "Create a login function"
Output: Uses localStorage directly (not your auth service)
```

**Fix:** Add more context
```
Context: Angular 17 app with existing AuthService at src/services/auth.service.ts
         that provides tokenStorage and httpClient injection
```

### Role Gaps → Missing Expertise

**Symptoms:**
- Misses security considerations
- Ignores performance implications
- Lacks domain-specific patterns

**Example Problem:**
```
Prompt: "Create a login function with validation"
Output: Missing rate limiting, account lockout, timing-safe comparison
```

**Fix:** Specify expertise
```
Role: Senior developer with security expertise in financial applications,
      familiar with OWASP authentication guidelines
```

### Action Gaps → Incomplete Implementation

**Symptoms:**
- Missing features
- Partial implementation
- Wrong interpretation of requirements

**Example Problem:**
```
Prompt: "Handle errors in this service"
Output: Only handles 500 errors, ignores 401/403/429
```

**Fix:** Be explicit and numbered
```
Action: Handle these specific error cases:
1. 401 Unauthorized → redirect to login, clear tokens
2. 403 Forbidden → show "Access Denied" message
3. 404 Not Found → return typed NotFoundError
4. 429 Rate Limited → queue retry with Retry-After header
5. 500+ Server Error → retry 3x with exponential backoff
6. Network Error → show offline indicator
```

### Format Gaps → Wrong Structure

**Symptoms:**
- Wrong return type
- Missing type annotations
- Incorrect file structure
- Missing documentation

**Example Problem:**
```
Prompt: "Create a user service"
Output: Returns Promise<any> instead of Observable<Result<User, UserError>>
```

**Fix:** Specify format precisely
```
Format: TypeScript with:
- Return type: Observable<Result<User, UserError>>
- Full interface definitions for User and UserError
- JSDoc with @param, @returns, @throws, @example
- inject() for dependencies (not constructor injection)
```

### Constraint Gaps → Unsafe or Non-compliant Code

**Symptoms:**
- Security vulnerabilities
- Performance issues
- Violates coding standards
- Missing error boundaries

**Example Problem:**
```
Prompt: "Log the login attempt"
Output: Logs password in error message: "Login failed for password: xyz"
```

**Fix:** Add explicit constraints
```
Constraints:
- NEVER log passwords, tokens, or sensitive data
- Use structured logging with correlation IDs
- Sanitize email before logging (mask domain)
- Rate limit log writes to prevent log flooding
```

## Step 3: Apply Targeted Refinements

### Refinement Templates

**When output is too generic:**
```
Add to Context:
"This is for [specific system] that already has [existing components].
The code must integrate with [specific services/patterns] located at [paths]."
```

**When missing security considerations:**
```
Add to Role:
"...with expertise in [OWASP/security domain] and awareness of
[specific threats like injection, enumeration, timing attacks]."
```

**When features are missing:**
```
Add to Action (numbered list):
"Additionally:
N. [Missing feature 1] with [specific behavior]
N+1. [Missing feature 2] with [specific behavior]"
```

**When format is wrong:**
```
Add to Format:
"Output MUST be:
- [Specific type/structure]
- With [specific annotations/decorators]
- Following [specific pattern like AAA, Result<T,E>]"
```

**When code is unsafe:**
```
Add to Constraints:
"Security requirements:
- MUST: [required security measure]
- MUST NOT: [prohibited practice]
- Handle [specific edge case] by [specific approach]"
```

## Step 4: Iteration Examples

### Example 1: Login Function (3 Iterations)

**Iteration 1 - Basic Prompt:**
```
Create a login function for Angular
```
**Output:** Generic function, no error handling, stores token in localStorage
**Quality:** 4/10

**Diagnosis:** Missing Context (framework details), Action (requirements), Constraints (security)

---

**Iteration 2 - Added Context and Action:**
```
Context: Angular 17 financial app with JWT auth

Action: Create login that:
1. POSTs to /api/auth/login
2. Stores tokens securely
3. Handles errors
```
**Output:** Better structure, but still uses localStorage, generic error handling
**Quality:** 6/10

**Diagnosis:** Missing Role (security expertise), Format (types), Constraints (security rules)

---

**Iteration 3 - Full CRAFT:**
```
Context: Angular 17 financial app with JWT auth, existing AuthService
         and TokenStorage service

Role: Senior Angular developer with security expertise

Action: Create login method that:
1. Accepts LoginRequest (email, password, rememberMe)
2. Validates inputs client-side
3. POSTs to /api/auth/login
4. On success: store access token in memory, refresh token per rememberMe
5. On 401: return typed AuthError
6. On 429: return rate limit error with retry time
7. Emit auth state via BehaviorSubject

Format: TypeScript with Observable<Result<AuthUser, AuthError>>, full types, JSDoc

Constraints:
- Never log passwords or tokens
- Use timing-safe comparison for sensitive operations
- Clear sensitive data from memory on failure
```
**Output:** Production-ready, secure, properly typed
**Quality:** 9/10

### Example 2: Test Generation (2 Iterations)

**Iteration 1:**
```
Write tests for calculatePortfolioReturn
```
**Output:** 3 basic tests, no edge cases, no mocking
**Quality:** 5/10

**Diagnosis:** Missing Action (test categories), Format (test structure)

---

**Iteration 2:**
```
Context: Jest tests for financial calculation function using BigDecimal-like precision

Role: QA engineer with financial domain expertise

Action: Generate tests covering:
1. Happy path (4 tests): positive gain, negative loss, break-even, with dividends
2. Edge cases (4 tests): zero initial, very small, very large, negative values
3. Boundaries (3 tests): precision limits, rounding behavior
4. Invalid inputs (4 tests): null, undefined, NaN handling

Format: Jest with describe blocks, test.each for parameterized tests,
        clear AAA comments

Constraints: Use toBeCloseTo for floating point, independent tests
```
**Output:** 15 comprehensive tests with proper structure
**Quality:** 9/10

## Common Iteration Patterns

### Pattern: "Add More Specificity"
```
Before: "Add error handling"
After:  "Handle these errors: [1. specific, 2. specific, 3. specific]"
```

### Pattern: "Name the Expertise"
```
Before: "Make it secure"
After:  "Role: Security engineer familiar with OWASP Top 10, specifically
         A01 (Broken Access Control) and A03 (Injection)"
```

### Pattern: "Show, Don't Tell"
```
Before: "Use proper formatting"
After:  "Format: Return Result<T, E> pattern like:
         type Result<T, E> = { success: true; data: T } | { success: false; error: E }"
```

### Pattern: "Constrain the Negative Space"
```
Before: "Be careful with security"
After:  "Constraints: NEVER log [x], NEVER store [y], NEVER expose [z]"
```

### Pattern: "Reference Existing Code"
```
Before: "Match our codebase style"
After:  "Context: Follow patterns in src/services/UserService.ts,
         specifically the error handling in lines 45-67"
```

## When to Stop Iterating

| Signal | Action |
|--------|--------|
| Output is 9/10 quality | ✅ Done |
| Output is 8/10 after 3 iterations | ✅ Accept and manually fix small issues |
| Same problem persists after 3 attempts | 🔄 Rethink approach entirely |
| Problem is in 1 specific section | 🎯 Ask Copilot to fix just that section |
| Output keeps getting worse | ⏹️ Start fresh with simpler prompt |

## Quick Reference: Iteration Checklist

Before each iteration, ask:

- [ ] Did I specify the **technology stack** clearly? (Context)
- [ ] Did I name the **expertise needed**? (Role)
- [ ] Did I **number my requirements**? (Action)
- [ ] Did I specify **return types and structure**? (Format)
- [ ] Did I list what **must NOT happen**? (Constraints)

## Anti-Patterns to Avoid

| Anti-Pattern | Problem | Better Approach |
|--------------|---------|-----------------|
| Adding everything at once | Overwhelms, hard to debug | Add one CRAFT element per iteration |
| Vague additions ("make it better") | Doesn't guide AI | Specific additions ("add retry with 3 attempts") |
| Removing working parts | Loses progress | Keep working parts, refine gaps |
| Endless iteration (>5 times) | Diminishing returns | Accept 8/10 or rethink approach |
| Copy-pasting entire output back | Context overflow | Reference specific issues |

---

*Remember: The goal is 9/10 quality in 1-3 iterations. If you're iterating more than 3 times, step back and reconsider your approach.*
