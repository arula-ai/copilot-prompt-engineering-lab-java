# Lab Action Guide - Java 17

Follow these steps using GitHub Copilot Chat. After each stage, run Summarizer Mode with the Hand-Off prompt so progress lands in `docs/workflow-tracker.md`.

## Quick Reference

| Stage | Primary Modes | Core Artifacts / Commands |
| --- | --- | --- |
| 0 | Agent | `mvn clean compile`, `mvn test` |
| 1 | Planning → Agent ↔ Need Review | `#Challenge1Login.java`, `#challenge1-login-solution.md`, CRAFT prompts |
| 2 | Planning → Agent | `#PortfolioServiceChallenge.java`, `#api-service-method.md`, library patterns |
| 3 | Agent ↔ Need Review | `#ContributionTemplate.java`, new pattern file |
| 4 | Testing → Agent | `mvn compile`, `mvn test`, quality summary |

## Mode Loop Reminder
- Planning → Agent → Need Review → Testing
- Use `#filename` to reference files as chat variables
- Use Summarizer Hand-Off after each stage to track progress

## Essential Guides

| Guide | Use For | Location |
|-------|---------|----------|
| CRAFT Framework | Writing effective prompts | [`docs/craft-framework/guide.md`](docs/craft-framework/guide.md) |
| Iteration Guide | Improving prompts that don't work | [`docs/craft-framework/iteration-guide.md`](docs/craft-framework/iteration-guide.md) |
| Pattern Selector | Finding the right pattern | [`docs/pattern-selector.md`](docs/pattern-selector.md) |
| Variable Substitution | Filling in pattern variables | [`docs/variable-substitution-guide.md`](docs/variable-substitution-guide.md) |
| Contribution Rubric | Validating patterns for Lab 3 | [`docs/rubrics/pattern-contribution-rubric.md`](docs/rubrics/pattern-contribution-rubric.md) |

---

## Stage 0 – Environment Setup

- Agent Mode: verify prerequisites with `#runInTerminal java -version` (requires 17+) and `#runInTerminal mvn -version` (requires 3.8+)
- Agent Mode: `#runInTerminal mvn clean compile` in the java folder
- Agent Mode: `#runInTerminal mvn test` to verify test execution
- Agent Mode: confirm Copilot is active by checking the status bar icon
- Agent Mode: open `src/main/java/com/fidelity/promptlab/challenges/lab1/` and verify all challenge files are accessible
- Hand-Off: summarize setup status, note any dependency issues or blockers

---

## Stage 1 – Crafting Effective Prompts with CRAFT

> **Tip:** If your prompt doesn't produce 9/10 quality output on the first try, use the [Iteration Guide](docs/craft-framework/iteration-guide.md) to diagnose and fix the gap.

### 1.1 Challenge 1: Login Method
- Planning Mode: review `#Challenge1Login.java` and identify what makes the prompt "Create a login method" insufficient
- Planning Mode: reference `#guide.md` (CRAFT framework) to plan your improved prompt covering Context, Role, Action, Format, Tone
- Planning Mode: note Java-specific considerations:
  - Spring Security integration
  - BCrypt password encoding
  - JWT token generation
  - Record types for DTOs
  - Account lockout after failed attempts
- Agent Mode: write your CRAFT prompt as a comment in the challenge file, then use Copilot to generate the implementation
- Need Review Mode: compare your generated code against `#challenge1-login-solution.md`; rate quality 1-10
- Hand-Off: record your prompt, quality rating, and key learnings

<details>
<summary>💡 CRAFT Prompt Hint (click to expand)</summary>

**Starter Example:**
```
Context: Spring Boot 3 app with Spring Security 6, JWT auth, BCrypt passwords

Role: Senior Java developer with Spring Security expertise

Action: Create login method that:
1. Accepts LoginRequest record (email, password, rememberMe)
2. Validates inputs and loads user from repository
3. Verifies password with BCryptPasswordEncoder
4. Generates JWT tokens (15 min access, 24h/30d refresh)
5. Tracks failed attempts (lock after 5 failures)

Format: Java 17 with records, Optional handling, @Service, Javadoc

Constraints: Never log passwords, timing-safe comparison, OWASP compliant
```

📖 **Full example:** See [`solutions/challenge1-login-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge1-login-solution.md)
</details>

### 1.2 Challenge 2: Test Generation
- Planning Mode: review `#Challenge2Tests.java` and the `calculatePortfolioReturn` method using BigDecimal
- Planning Mode: note Java-specific testing requirements:
  - JUnit 5 with @Nested and @DisplayName
  - @ParameterizedTest with @CsvSource
  - AssertJ assertions with `isEqualByComparingTo()` for BigDecimal
  - ArithmeticException handling for division by zero
- Agent Mode: craft a CRAFT prompt specifying test framework, edge cases, and parameterized tests
- Agent Mode: generate comprehensive test suite with Copilot
- Need Review Mode: compare against `#challenge2-tests-solution.md`; verify coverage of happy path, edge cases, boundaries
- Hand-Off: record quality rating and which CRAFT elements made the biggest difference

<details>
<summary>💡 CRAFT Prompt Hint (click to expand)</summary>

**Starter Example:**
```
Context: JUnit 5 tests for financial app, testing calculatePortfolioReturn with BigDecimal

Role: QA engineer with expertise in financial calculations and BigDecimal precision

Action: Generate test suite covering:
1. Happy path: positive return, negative return, break-even, with dividends
2. Edge cases: zero initial value, very small/large values
3. Boundary values: precision limits, rounding behavior
4. Invalid inputs: null values, exception handling

Format: JUnit 5 with @Nested, @DisplayName, @ParameterizedTest, AssertJ assertions

Constraints: Use BigDecimal.valueOf(), compare with isEqualByComparingTo()
```

📖 **Full example:** See [`solutions/challenge2-tests-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge2-tests-solution.md)

📚 **Library pattern:** See [`prompt-library/testing/unit-test-suite-java.prompt.md`](prompt-library/testing/unit-test-suite-java.prompt.md)
</details>

### 1.3 Challenge 3: Bug Fix
- Planning Mode: review `#Challenge3Bugfix.java` and identify all bugs:
  - Using `double` instead of `BigDecimal` (precision errors)
  - No thousand separators
  - Wrong negative sign position ("$-50" vs "-$50")
  - Only USD/EUR supported (should use ISO 4217)
- Agent Mode: craft a CRAFT prompt explicitly listing each bug with expected fix approach
- Agent Mode: generate fixed implementation using `NumberFormat.getCurrencyInstance(Locale)` and `Currency.getInstance()`
- Need Review Mode: compare against `#challenge3-bugfix-solution.md`; verify edge cases including JPY (0 decimals), BHD (3 decimals)
- Hand-Off: record quality rating and bug-fix approach learnings

<details>
<summary>💡 CRAFT Prompt Hint (click to expand)</summary>

**Starter Example:**
```
Context: Java financial app with buggy formatCurrency method
Current bugs (with examples):
- formatCurrency(1234.5, "USD") → "$1234.5" (missing thousand separator)
- formatCurrency(-50, "USD") → "$-50" (wrong negative position)
- Uses double causing precision errors

Role: Senior developer with i18n experience in financial applications

Action: Fix these specific bugs:
1. Add locale-aware thousand separators
2. Use proper negative currency formatting
3. Use BigDecimal to avoid floating point errors
4. Support all ISO 4217 currency codes via NumberFormat

Format: Java using NumberFormat.getCurrencyInstance(Locale), BigDecimal

Constraints: Handle null inputs, support JPY (0 decimals), BHD (3 decimals)
```

📖 **Full example:** See [`solutions/challenge3-bugfix-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge3-bugfix-solution.md)
</details>

### 1.4 Challenge 4: Error Handling
- Planning Mode: review `#Challenge4Errors.java` and catalog missing error handling:
  - HTTP status codes: 400, 401, 403, 404, 429, 500+
  - Network errors and timeouts
  - Retry strategy with exponential backoff
  - Typed error responses
- Planning Mode: note Java-specific patterns:
  - Sealed interface for PortfolioError types
  - Result<S, F> pattern instead of exceptions
  - SLF4J logging with MDC context
  - Spring RestClient or WebClient
- Agent Mode: craft a CRAFT prompt specifying error handling strategy and return types
- Agent Mode: generate resilient service method with Copilot
- Need Review Mode: compare against `#challenge4-errors-solution.md`; verify error decision tree coverage
- Hand-Off: record quality rating and sealed interface usage insights

<details>
<summary>💡 CRAFT Prompt Hint (click to expand)</summary>

**Starter Example:**
```
Context: Spring Boot 3 portfolio service calling external API, currently no error handling

Role: Senior Java developer with resilience engineering expertise

Action: Add comprehensive error handling:
1. Handle HTTP errors: 400 (bad request), 401/403 (auth), 404 (not found), 429 (rate limit), 500+ (server)
2. Handle network errors and timeouts
3. Implement retry with exponential backoff (3 attempts, 1s/2s/4s)
4. Return Result<Portfolio, PortfolioError> instead of throwing
5. Log errors with MDC context (correlationId, userId)

Format: Java 17 with sealed interface for error types, Spring RestClient

Constraints: Don't retry on 4xx, include Retry-After header support for 429
```

📖 **Full example:** See [`solutions/challenge4-errors-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge4-errors-solution.md)
</details>

### 1.5 Challenge 5: Optimization
- Planning Mode: review `#Challenge5Optimize.java` and analyze the O(n²) `findDuplicateTransactions` complexity
- Planning Mode: plan optimization approach:
  - HashMap grouping by symbol+quantity+price
  - Timestamp sorting within groups
  - Early termination when time window exceeded
  - Records for configuration options
- Agent Mode: craft a CRAFT prompt specifying target complexity O(n log n) and Java 17 features
- Agent Mode: generate optimized implementation with Copilot
- Need Review Mode: compare against `#challenge5-optimize-solution.md`; verify performance improvement
- Hand-Off: summarize all 5 challenge ratings, average quality score, and top CRAFT insights

<details>
<summary>💡 CRAFT Prompt Hint (click to expand)</summary>

**Starter Example:**
```
Context: Financial app with O(n²) findDuplicateTransactions, processing 10k+ transactions
Currently uses nested loops comparing every pair

Role: Senior Java developer with algorithm optimization expertise

Action: Optimize to O(n log n):
1. Group transactions by key (symbol+quantity+price) using HashMap
2. Sort each group by timestamp
3. Within each group, find duplicates within time window (60s)
4. Use early termination when time window exceeded
5. Return List<DuplicatePair> with both transactions

Format: Java 17 with records, Stream API, Collectors.groupingBy

Constraints: Maintain accuracy, handle edge cases (empty list, single item groups)
```

📖 **Full example:** See [`solutions/challenge5-optimize-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge5-optimize-solution.md)
</details>

---

## Stage 2 – Applying Library Patterns

> **Tips:**
> - Use the [Pattern Selector](docs/pattern-selector.md) to find the right pattern for your task
> - See the [Variable Substitution Guide](docs/variable-substitution-guide.md) for how to fill in `[variables]`

### 2.1 Portfolio Service Pattern
- Planning Mode: review `#PortfolioServiceChallenge.java` and identify the issues (no caching, no retry, no error transformation)
- Planning Mode: open the API service pattern from the prompt library and plan variable substitution:
  - `[service-name]` → PortfolioService
  - `[method-name]` → getPortfolioById
  - `[endpoint]` → GET /api/portfolios/{id}
  - `[return-type]` → Result<Portfolio, PortfolioError>
  - `[caching]` → @Cacheable with 5-minute TTL
  - `[retry]` → Resilience4j or manual 3x exponential backoff
  - `[errors]` → PortfolioNotFoundError, NetworkError (sealed interface)
- Agent Mode: apply the customized pattern prompt in Copilot to generate the improved service
- Agent Mode: verify output includes @Service, @Cacheable, retry logic, and typed errors
- Hand-Off: record pattern effectiveness and quality rating

📚 **Library patterns to use:**
- [`prompt-library/code-generation/api-service-method-java.prompt.md`](prompt-library/code-generation/api-service-method-java.prompt.md) - Java-specific API service pattern
- [`prompt-library/code-generation/api-service-method.prompt.md`](prompt-library/code-generation/api-service-method.prompt.md) - General API service pattern

### 2.2 Validation Pattern
- Planning Mode: review `#ValidationChallenge.java` and open the input validation pattern
- Planning Mode: note Java-specific validation patterns:
  - Bean Validation annotations (@NotNull, @Pattern, @DecimalMin)
  - Custom validators for complex rules
  - ValidationResult sealed interface
- Agent Mode: apply the validation pattern to generate type-safe validation service
- Need Review Mode: verify validators handle IDOR prevention, input sanitization, OWASP alignment
- Hand-Off: record validation pattern insights

📚 **Library patterns to use:**
- [`prompt-library/security/input-validation-java.prompt.md`](prompt-library/security/input-validation-java.prompt.md) - Java Bean Validation pattern
- [`prompt-library/security/input-validation.prompt.md`](prompt-library/security/input-validation.prompt.md) - General validation pattern

### 2.3 Test Generation Pattern
- Planning Mode: review `#TestGenerationChallenge.java` and open the unit test pattern
- Agent Mode: apply the JUnit 5 testing pattern to generate comprehensive test coverage
- Need Review Mode: verify tests include @Nested, @ParameterizedTest, Mockito mocking, AssertJ assertions
- Hand-Off: summarize Stage 2 pattern usage and average quality rating

📚 **Library patterns to use:**
- [`prompt-library/testing/unit-test-suite-java.prompt.md`](prompt-library/testing/unit-test-suite-java.prompt.md) - JUnit 5 + Mockito pattern
- [`prompt-library/testing/unit-test-suite.prompt.md`](prompt-library/testing/unit-test-suite.prompt.md) - General unit test pattern

---

## Stage 3 – Contributing to the Library

> **Important:** Use the [Pattern Contribution Rubric](docs/rubrics/pattern-contribution-rubric.md) to validate your pattern before submitting. It includes the test methodology, scoring criteria, and submission checklist.

- Planning Mode: review `#ContributionTemplate.java` and identify a repeatable prompt pattern you discovered during Labs 1-2
- Planning Mode: consider Java-specific patterns for:
  - Record-based DTOs with validation
  - Spring @Service with @Transactional
  - Repository with custom queries
  - Exception handling with @ControllerAdvice
  - Streams processing pipelines
- Planning Mode: outline your pattern including:
  - Pattern name and category
  - Template with `[variables]`
  - Variable descriptions with examples
  - Expected output quality target
- Agent Mode: create your pattern file following the library structure
- Agent Mode: test your pattern 5 times on different scenarios:
  - Test 1: describe input → record output quality
  - Test 2: describe input → record output quality
  - Test 3: describe input → record output quality
  - Test 4: describe input → record output quality
  - Test 5: describe input → record output quality
- Agent Mode: calculate success rate (target ≥80%)
- Need Review Mode: validate pattern meets library contribution criteria
- Agent Mode: if success rate ≥80%, save pattern to `prompt-library/[category]/[pattern-name]-java.prompt.md`
- Hand-Off: record pattern name, category, success rate, and file location

📚 **Reference patterns for contribution ideas:**
- [`prompt-library/refactoring/extract-method.prompt.md`](prompt-library/refactoring/extract-method.prompt.md) - Extract reusable methods
- [`prompt-library/refactoring/review-and-refactor.prompt.md`](prompt-library/refactoring/review-and-refactor.prompt.md) - Code review with suggestions
- [`prompt-library/workflow/implementation-plan.prompt.md`](prompt-library/workflow/implementation-plan.prompt.md) - Planning before coding
- [`prompt-library/documentation/jsdoc-generation.prompt.md`](prompt-library/documentation/jsdoc-generation.prompt.md) - Documentation generation
- See full library: [`prompt-library/README.md`](prompt-library/README.md)

---

## Stage 4 – Validation & Wrap-Up

- Testing Mode: `#runInTerminal mvn clean compile` – verify no compilation errors
- Testing Mode: `#runInTerminal mvn test` – run all tests
- Agent Mode: compile quality summary across all stages:
  | Challenge | Quality Rating | Target |
  |-----------|----------------|--------|
  | 1. Login | ___/10 | 9/10 |
  | 2. Tests | ___/10 | 9/10 |
  | 3. Bug Fix | ___/10 | 9/10 |
  | 4. Errors | ___/10 | 9.5/10 |
  | 5. Optimize | ___/10 | 9/10 |
  | Lab 2 Avg | ___/10 | 9/10 |
- Agent Mode: document Java 17 features that improved prompts:
  - [ ] Records for DTOs
  - [ ] Sealed interfaces for error types
  - [ ] Pattern matching in switch
  - [ ] Text blocks for multi-line prompts
  - [ ] Optional for null safety
- Agent Mode: document top 3 CRAFT learnings in `docs/workflow-tracker.md`
- Hand-Off: confirm final quality scores, pattern contribution status, and next steps for applying CRAFT at work

---

## Troubleshooting

| Issue | Copilot Solution |
|-------|------------------|
| Copilot not responding | Agent Mode: check internet, restart IDE, verify subscription status |
| Poor quality output | Planning Mode: add more Context, specify Java version explicitly |
| Build errors | Agent Mode: `#runInTerminal mvn clean`, verify Java 17+ with `java -version` |
| Test failures | Agent Mode: check for missing dependencies in `pom.xml` |
| Can't find solutions | Agent Mode: solutions are in `challenges/lab1/solutions/` |

## Java-Specific Prompting Tips

| Scenario | What to Specify in CRAFT Prompt |
|----------|--------------------------------|
| Money calculations | Use BigDecimal, specify RoundingMode.HALF_UP |
| Date/time | Use java.time.*, specify timezone handling |
| Collections | Mutable vs immutable, Stream API usage |
| Null handling | Optional return, @Nullable/@NonNull annotations |
| Testing | JUnit 5, AssertJ, Mockito versions |
| Spring | Version 3.x, Boot vs Framework |

## Key Files Reference

### CRAFT Framework
- [`docs/craft-framework/guide.md`](docs/craft-framework/guide.md) - Complete CRAFT framework guide

### Prompt Library Patterns
| Category | TypeScript/General | Java-Specific |
|----------|-------------------|---------------|
| API Service | [`api-service-method.prompt.md`](prompt-library/code-generation/api-service-method.prompt.md) | [`api-service-method-java.prompt.md`](prompt-library/code-generation/api-service-method-java.prompt.md) |
| Unit Tests | [`unit-test-suite.prompt.md`](prompt-library/testing/unit-test-suite.prompt.md) | [`unit-test-suite-java.prompt.md`](prompt-library/testing/unit-test-suite-java.prompt.md) |
| Validation | [`input-validation.prompt.md`](prompt-library/security/input-validation.prompt.md) | [`input-validation-java.prompt.md`](prompt-library/security/input-validation-java.prompt.md) |
| Refactoring | [`extract-method.prompt.md`](prompt-library/refactoring/extract-method.prompt.md) | - |
| Code Review | [`review-and-refactor.prompt.md`](prompt-library/refactoring/review-and-refactor.prompt.md) | - |
| Planning | [`implementation-plan.prompt.md`](prompt-library/workflow/implementation-plan.prompt.md) | - |

### Solution Files
- [`challenge1-login-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge1-login-solution.md) - Login with CRAFT example
- [`challenge2-tests-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge2-tests-solution.md) - Test generation example
- [`challenge3-bugfix-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge3-bugfix-solution.md) - Bug fix example
- [`challenge4-errors-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge4-errors-solution.md) - Error handling example
- [`challenge5-optimize-solution.md`](src/main/java/com/fidelity/promptlab/challenges/lab1/solutions/challenge5-optimize-solution.md) - Optimization example

### Progress Tracking
- [`docs/workflow-tracker.md`](docs/workflow-tracker.md) - Track your progress
