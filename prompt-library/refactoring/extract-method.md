# Pattern: Extract Method

**Category:** Refactoring
**Language:** TypeScript / Java
**Success Rate:** 90%
**Last Verified:** December 2025

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[function-name]` | Original function to refactor | `processTransaction`, `handleUserLogin` |
| `[proposed-name]` | Name for extracted method | `validateTransactionFields`, `calculateFees` |
| `[parameters]` | What the new method needs | `tx: Transaction`, `amount: BigDecimal` |
| `[return type]` | What extracted method returns | `void`, `ValidationResult`, `BigDecimal` |

## Prompt Template

```
Refactor by extracting a method from [function-name]:

Current code:
[paste the code block to extract from]

Extract the following logic into a new method:
- Lines/functionality: [describe what to extract]
- New method name: [proposed-name]
- Parameters needed: [list parameters]
- Return type: [expected return type]

Requirements:
1. Maintain existing behavior exactly
2. Add appropriate JSDoc comments
3. Handle edge cases from original context
4. Keep method focused on single responsibility

Format: TypeScript with full types
Constraints: [any constraints, e.g., "Must remain a pure function"]
```

## Example 1: TypeScript Extraction

```
Refactor by extracting a method from processTransaction:

Current code:
async processTransaction(tx: Transaction): Promise<Transaction> {
  // Validate
  if (!tx.symbol) throw new Error('Symbol required');
  if (tx.quantity <= 0) throw new Error('Invalid quantity');
  if (tx.price <= 0) throw new Error('Invalid price');

  // Calculate
  const total = tx.quantity * tx.price;
  const fees = total * 0.01;

  // Process
  tx.total = total;
  tx.fees = fees;
  tx.status = 'completed';
  return tx;
}

Extract the following logic into a new method:
- Lines/functionality: Validation logic (first 3 if statements)
- New method name: validateTransactionFields
- Parameters needed: tx: Transaction
- Return type: void (throws on invalid)

Requirements:
1. Maintain existing behavior exactly
2. Add appropriate JSDoc comments
3. Throw same error messages
4. Keep validation focused

Format: TypeScript with full types
Constraints: Must throw same errors as original
```

### Expected TypeScript Output

```typescript
/**
 * Validates required transaction fields.
 * @param tx - The transaction to validate
 * @throws {Error} If symbol is missing, quantity is invalid, or price is invalid
 */
private validateTransactionFields(tx: Transaction): void {
  if (!tx.symbol) throw new Error('Symbol required');
  if (tx.quantity <= 0) throw new Error('Invalid quantity');
  if (tx.price <= 0) throw new Error('Invalid price');
}

async processTransaction(tx: Transaction): Promise<Transaction> {
  this.validateTransactionFields(tx);

  const total = tx.quantity * tx.price;
  const fees = total * 0.01;

  tx.total = total;
  tx.fees = fees;
  tx.status = 'completed';
  return tx;
}
```

## Example 2: Java Complex Extraction

```
Refactor by extracting methods from OrderService.submitOrder:

Current code:
@Transactional
public OrderResult submitOrder(OrderRequest request) {
    // Validate order
    if (request.getSymbol() == null || request.getSymbol().isBlank()) {
        throw new ValidationException("Symbol is required");
    }
    if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValidationException("Quantity must be positive");
    }
    if (request.getOrderType() == OrderType.LIMIT && request.getLimitPrice() == null) {
        throw new ValidationException("Limit price required for limit orders");
    }

    // Calculate costs
    BigDecimal notional = request.getQuantity().multiply(
        request.getOrderType() == OrderType.MARKET
            ? marketDataService.getPrice(request.getSymbol())
            : request.getLimitPrice()
    );
    BigDecimal commission = notional.multiply(new BigDecimal("0.001"));
    BigDecimal totalCost = notional.add(commission);

    // Check buying power
    Account account = accountService.getAccount(request.getAccountId());
    if (account.getBuyingPower().compareTo(totalCost) < 0) {
        throw new InsufficientFundsException("Insufficient buying power");
    }

    // Submit to exchange
    Order order = orderRepository.save(Order.from(request, notional, commission));
    exchangeGateway.submit(order);
    return new OrderResult(order.getId(), OrderStatus.SUBMITTED, totalCost);
}

Extract into multiple focused methods:
1. validateOrderRequest(OrderRequest) -> void
   - All validation logic
2. calculateOrderCosts(OrderRequest) -> OrderCosts record
   - Notional, commission, total calculation
3. verifyBuyingPower(UUID accountId, BigDecimal required) -> void
   - Account lookup and balance check

Requirements:
1. Each method has single responsibility
2. Methods are testable in isolation
3. Original behavior unchanged
4. Add Javadoc to each extracted method

Format: Java 17+ with records for DTOs
Constraints:
- Keep @Transactional on main method only
- Extracted methods should be package-private for testing
```

### Expected Java Output

```java
public record OrderCosts(
    BigDecimal notional,
    BigDecimal commission,
    BigDecimal total
) {}

/**
 * Validates all required fields on an order request.
 *
 * @param request the order request to validate
 * @throws ValidationException if any required field is missing or invalid
 */
void validateOrderRequest(OrderRequest request) {
    if (request.getSymbol() == null || request.getSymbol().isBlank()) {
        throw new ValidationException("Symbol is required");
    }
    if (request.getQuantity() == null ||
        request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValidationException("Quantity must be positive");
    }
    if (request.getOrderType() == OrderType.LIMIT &&
        request.getLimitPrice() == null) {
        throw new ValidationException("Limit price required for limit orders");
    }
}

/**
 * Calculates the costs associated with an order.
 *
 * @param request the order request
 * @return an OrderCosts record containing notional, commission, and total
 */
OrderCosts calculateOrderCosts(OrderRequest request) {
    BigDecimal price = request.getOrderType() == OrderType.MARKET
        ? marketDataService.getPrice(request.getSymbol())
        : request.getLimitPrice();

    BigDecimal notional = request.getQuantity().multiply(price);
    BigDecimal commission = notional.multiply(new BigDecimal("0.001"));
    BigDecimal total = notional.add(commission);

    return new OrderCosts(notional, commission, total);
}

/**
 * Verifies the account has sufficient buying power.
 *
 * @param accountId the account to check
 * @param requiredAmount the amount needed
 * @throws InsufficientFundsException if buying power is insufficient
 */
void verifyBuyingPower(UUID accountId, BigDecimal requiredAmount) {
    Account account = accountService.getAccount(accountId);
    if (account.getBuyingPower().compareTo(requiredAmount) < 0) {
        throw new InsufficientFundsException("Insufficient buying power");
    }
}

@Transactional
public OrderResult submitOrder(OrderRequest request) {
    validateOrderRequest(request);

    OrderCosts costs = calculateOrderCosts(request);

    verifyBuyingPower(request.getAccountId(), costs.total());

    Order order = orderRepository.save(
        Order.from(request, costs.notional(), costs.commission())
    );
    exchangeGateway.submit(order);

    return new OrderResult(order.getId(), OrderStatus.SUBMITTED, costs.total());
}
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| **Too many parameters** | Extracted method needs 5+ params | Extract parameter object first |
| **Changing behavior** | Accidental logic changes | Write tests before refactoring |
| **Wrong scope** | Public when should be private | Use minimal visibility needed |
| **Mixed concerns** | Extracted method still does 2 things | Extract further |
| **Missing dependencies** | Extracted method needs class state | Pass as parameters or keep as method |
| **Breaking transactions** | Extracting from @Transactional | Keep transaction boundary on caller |

## When to Extract Methods

| Symptom | Action |
|---------|--------|
| Method > 20 lines | Extract logical sections |
| Code comment explaining "what" | Extract to well-named method |
| Same code in 2+ places | Extract and call from both |
| Nested conditions 3+ deep | Extract inner logic |
| Multiple levels of abstraction | Extract lower-level operations |

## Extraction Decision Tree

```
Is this code block...
    │
    ├─► Used in multiple places? ──► Extract to shared method
    │
    ├─► A distinct logical step? ──► Extract with descriptive name
    │
    ├─► Complex with nested logic? ──► Extract inner conditions
    │
    ├─► Testable in isolation? ──► Extract for unit testing
    │
    └─► A guard clause? ──► Keep inline or extract to validator
```

## Implementation Hints for AI

When extracting methods:
1. **Preserve exact behavior** - don't "improve" while extracting
2. **Name describes what, not how** - `validateOrder` not `checkFieldsAndThrow`
3. **Minimize parameters** - more than 3 suggests need for parameter object
4. **Return meaningful types** - prefer records over tuples/arrays
5. **Consider visibility** - extracted helper methods are often private
6. **Maintain testability** - package-private allows unit testing in Java

## Expected Output Quality: 9/10
