# Lab 1: Transform These Bad Prompts (Java 17)

Your task: Transform each bad prompt using the CRAFT framework for Java 17 development.

## Prompt 1: Login Method
**Bad:** "Create a login method"

**Your CRAFT transformation:**
```
Context: [Spring Boot version, security framework, auth mechanism (JWT/OAuth2/Session)]

Role: [e.g., Senior Java developer with Spring Security expertise]

Action:
1. [specific step]
2. [specific step]
3. [specific step]

Format: [Return type, exceptions, annotations, etc.]

Constraints: [OWASP compliance, no field injection, thread safety, etc.]
```

**Test your prompt in Copilot, then rate:**
- Quality Rating (1-10): ___
- What made the difference: ___

---

## Prompt 2: Write Tests
**Bad:** "Write tests for this"

**Your CRAFT transformation:**
```
Context: [JUnit 5, testing a service/repository/controller, Spring Boot Test]

Role:

Action:

Format: [@Nested classes, @DisplayName, @ParameterizedTest, etc.]

Constraints: [coverage goals, mocking with Mockito, AssertJ assertions]
```

**Quality Rating (1-10): ___

---

## Prompt 3: Fix the Bug
**Bad:** "Fix the bug"

**Your CRAFT transformation:**
```
Context: [describe the bug, affected class, Java version features to use]

Role:

Action:

Format:

Constraints: [BigDecimal for money, null safety, immutability, etc.]
```

**Quality Rating (1-10): ___

---

## Prompt 4: Add Error Handling
**Bad:** "Add error handling"

**Your CRAFT transformation:**
```
Context: [REST API, service layer, external API calls, database operations]

Role:

Action:

Format: [Custom exceptions, @ExceptionHandler, Result<T,E> type, etc.]

Constraints: [logging with SLF4J, no sensitive data in logs, retry with Resilience4j]
```

**Quality Rating (1-10): ___

---

## Prompt 5: Optimize This Code
**Bad:** "Optimize this code"

**Your CRAFT transformation:**
```
Context: [what's slow - N+1 queries, O(n²) algorithm, memory usage]

Role:

Action:

Format:

Constraints: [Stream API, parallel streams, caching strategy, etc.]
```

**Quality Rating (1-10): ___

---

## Evaluation Summary

| Prompt | Clarity | Completeness | Specificity | Output Quality | Overall |
|--------|---------|--------------|-------------|----------------|---------|
| 1 | /10 | /10 | /10 | /10 | /10 |
| 2 | /10 | /10 | /10 | /10 | /10 |
| 3 | /10 | /10 | /10 | /10 | /10 |
| 4 | /10 | /10 | /10 | /10 | /10 |
| 5 | /10 | /10 | /10 | /10 | /10 |

## Java 17 Keywords to Include

| Category | Useful Keywords |
|----------|-----------------|
| Types | `Optional<T>`, `record`, `sealed`, `BigDecimal` for money |
| Patterns | Builder pattern, Factory, Strategy, DI with constructor |
| Testing | `@Nested`, `@ParameterizedTest`, `@MockBean`, AssertJ |
| Security | `@PreAuthorize`, BCrypt, input validation, OWASP |
| Performance | Stream API, `parallelStream()`, `@Cacheable`, lazy loading |
| Spring | `@Transactional`, `@Retryable`, `@Async`, `@Valid` |

## Java 17 Features to Consider

| Feature | When to Use |
|---------|-------------|
| Records | DTOs, value objects, immutable data |
| Sealed classes | Restricted hierarchies, ADTs |
| Pattern matching | `instanceof` checks, switch expressions |
| Text blocks | Multi-line strings, SQL, JSON |
| `Optional<T>` | Nullable return values |
| Stream API | Collection processing |
