---
agent: 'agent'
description: 'Refactors a function with many parameters into one using a parameter object'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: function signature with many parameters'
---

# Introduce Parameter Object Pattern

**Category:** Refactoring | **Success Rate:** 88%

Refactor to use a parameter object with the following specifications:

## Required Information

Please provide:
- **Function name**: The function to refactor
- **Current signature**: Paste the function signature with all parameters
- **Language**: TypeScript or Java 17

## Parameter Grouping

Organize parameters into logical groups:
- **Core/Required**: Primary inputs that must be provided
- **Context**: Identifiers like userId, portfolioId
- **Options**: Optional configuration with defaults

## Object Design

Specify:
- **Object name**: e.g., CreateTransactionRequest, NotificationConfig
- **Optional parameters with defaults**: e.g., executeAt defaults to now
- **Validation constraints**: e.g., quantity > 0, valid email format

## Backward Compatibility

Choose strategy:
1. **Overloaded function**: Keep old signature, add new one
2. **@Deprecated old signature**: With migration path
3. **No compatibility needed**: Breaking change is acceptable

## Format Requirements

- **TypeScript**: interface with readonly properties, Partial<> for optionals
- **Java**: record with compact constructor validation, optional Builder pattern
- Immutable (readonly/final)
- JSON serializable if needed for APIs

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too many optionals | Object becomes as complex as params | Group into nested objects |
| No validation | Invalid states possible | Validate in constructor/factory |
| Breaking changes | Existing callers break | Provide overloaded compatibility method |
| Mutable object | State changes after creation | Use readonly/final, records |

## TypeScript Example

```typescript
interface CreateTransactionRequest {
  // Core (required)
  readonly symbol: string;
  readonly quantity: number;
  readonly type: 'BUY' | 'SELL';

  // Context (required)
  readonly portfolioId: string;

  // Options (optional)
  readonly notes?: string;
  readonly executeAt?: Date;
}
```

## Java Example

```java
public record NotificationRequest(
    String userId,
    String title,
    String body,
    Priority priority,  // defaults in compact constructor
    List<String> channels
) {
    public NotificationRequest {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(title);
        priority = priority != null ? priority : Priority.NORMAL;
        channels = channels != null ? List.copyOf(channels) : List.of("EMAIL");
    }
}
```

## Expected Output Quality: 9/10
