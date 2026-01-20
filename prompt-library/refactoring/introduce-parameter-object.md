# Pattern: Introduce Parameter Object

**Category:** Refactoring
**Language:** TypeScript, Java
**Success Rate:** 88%
**Last Verified:** December 2025

## Prompt Template

```
Refactor [function-name] to use a parameter object:

Current signature:
[paste current function signature with many parameters]

Create a parameter object that:
1. Groups related parameters: [list parameter groups]
2. Provides defaults for: [optional parameters]
3. Enables validation of: [constraints]
4. Is named: [ProposedNameRequest/Options/Config]

Requirements:
1. Create interface/class for the parameter object
2. Add JSDoc/Javadoc for each property
3. Maintain backward compatibility with [strategy]
4. Include builder pattern if > 5 required fields

Format: [TypeScript interface | Java record | class with builder]
Constraints: [any constraints, e.g., "Immutable", "Serializable"]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[function-name]` | Function to refactor | createTransaction, sendNotification |
| `[parameter-groups]` | Logical groupings | user info, transaction details, options |
| `[optional-parameters]` | Params with defaults | pagination, sorting, filters |
| `[constraints]` | Validation rules | amount > 0, email format, date range |
| `[strategy]` | Compatibility approach | Overloaded method, @Deprecated old signature |

## Example Usage

### TypeScript

```
Refactor createTransaction to use a parameter object:

Current signature:
createTransaction(
  symbol: string,
  quantity: number,
  price: number,
  type: 'BUY' | 'SELL',
  portfolioId: string,
  userId: string,
  notes?: string,
  executeAt?: Date,
  limitPrice?: number,
  stopLoss?: number
): Promise<Transaction>

Create a parameter object that:
1. Groups related parameters:
   - Core: symbol, quantity, price, type
   - Context: portfolioId, userId
   - Options: notes, executeAt, limitPrice, stopLoss
2. Provides defaults for: executeAt (now), notes (empty)
3. Enables validation of: quantity > 0, price > 0, valid symbol format
4. Is named: CreateTransactionRequest

Requirements:
1. Create TypeScript interface with readonly properties
2. Add JSDoc for each property with examples
3. Maintain backward compatibility with overloaded function signature
4. Include factory function for common transaction types

Format: TypeScript interface with Partial<> for optional fields
Constraints: Immutable (readonly), JSON serializable
```

### Java

```
Refactor sendNotification to use a parameter object:

Current signature:
public void sendNotification(
    String userId,
    String title,
    String body,
    NotificationType type,
    Priority priority,
    Map<String, String> metadata,
    List<String> channels,
    LocalDateTime scheduledAt,
    Duration ttl,
    boolean requireAck
)

Create a parameter object that:
1. Groups related parameters:
   - Target: userId, channels
   - Content: title, body, type
   - Delivery: priority, scheduledAt, ttl, requireAck
   - Extra: metadata
2. Provides defaults for: priority (NORMAL), channels ([EMAIL]), ttl (24h)
3. Enables validation of: userId not blank, title length < 100
4. Is named: NotificationRequest

Requirements:
1. Create Java record with compact constructor validation
2. Add Javadoc for each component with @param tags
3. Maintain backward compatibility with @Deprecated builder method
4. Include builder pattern for fluent construction

Format: Java 17 record with builder
Constraints: Immutable, implements Serializable
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too many optionals | Object becomes as complex as params | Group into nested objects |
| No validation | Invalid states possible | Validate in constructor/factory |
| Breaking changes | Existing callers break | Provide overloaded compatibility method |
| Mutable object | State changes after creation | Use readonly/final, records |

## Expected Output Quality: 9/10

A well-crafted prompt produces:
- Clean, immutable parameter object
- Proper grouping of related fields
- Validation in constructor or factory
- Backward compatibility strategy
