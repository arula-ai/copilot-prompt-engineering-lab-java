---
agent: 'agent'
description: 'Creates an API service method with caching, retry logic, and error handling for TypeScript/Angular or Java/Spring'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: service name, method name, endpoint path, and language (TypeScript or Java)'
---

# API Service Method Pattern

**Category:** Code Generation | **Success Rate:** 94%

Create an API service method with the following specifications:

## Required Information

Please provide (or I will ask for):
- **Service name**: e.g., Portfolio, User, Transaction
- **Method name**: e.g., getAccountBalances, findById
- **Language/Framework**: TypeScript with Angular 17+ OR Java 17 with Spring Boot 3
- **HTTP method and endpoint**: e.g., GET /api/users/{userId}/accounts
- **Return type**: e.g., Observable<AccountBalance[]> or Mono<List<AccountBalance>>

## Features to Include

1. **Caching** with configurable TTL (default 5 minutes)
2. **Retry logic** with exponential backoff (3 attempts for 5xx/network errors only)
3. **Error transformation** to domain-specific errors
4. **Request cancellation** support (takeUntilDestroyed for Angular)
5. **Loading state** signal/observable for UI binding

## Error Handling Requirements

- **401 Unauthorized**: Clear auth state, redirect to login
- **404 Not Found**: Return empty array/Optional.empty() (not an error)
- **429 Rate Limited**: Retry after delay from header
- **500+ Server Error**: Transform to user-facing error with retry option

## Format Requirements

- Full type definitions (no `any` types)
- JSDoc/Javadoc with @example
- Use inject() for Angular or constructor injection for Spring
- Follow existing patterns in the codebase

## Constraints

- Only retry on 5xx and network errors, NOT 4xx
- Cache key must include ALL relevant parameters (sorted if arrays)
- NEVER log sensitive data (tokens, passwords, PII)
- Must be thread-safe (for Java)

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Retrying 4xx errors | Wastes resources, won't succeed | Only retry 5xx and network errors |
| Cache key missing params | Wrong cached data returned | Include ALL parameters in cache key |
| Blocking in reactive | Thread starvation | Use subscribeOn(Schedulers.boundedElastic()) |
| No request timeout | Hung requests consume resources | Set explicit timeout (default 30s) |
| Logging sensitive data | Security/compliance violation | Mask tokens, passwords, PII in logs |

## Expected Output Quality: 9/10
