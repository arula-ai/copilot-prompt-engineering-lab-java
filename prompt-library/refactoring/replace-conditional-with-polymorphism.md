# Pattern: Replace Conditional with Polymorphism

**Category:** Refactoring
**Language:** TypeScript, Java
**Success Rate:** 85%
**Last Verified:** December 2025

## Prompt Template

```
Refactor [function-name] to replace conditionals with polymorphism:

Current code with conditionals:
[paste code with if/else or switch statements]

The conditional branches on: [discriminator field/type]
Each branch handles: [describe what each branch does]

Create a polymorphic solution that:
1. Base type: [interface/abstract class name]
2. Implementations: [list each variant]
3. Factory method: [how to create correct type]
4. Shared behavior: [what goes in base]

Requirements:
1. Eliminate all [if/switch] statements for this logic
2. Each variant class has single responsibility
3. Add new variants without modifying existing code (OCP)
4. Include factory or registry for type creation

Format: [TypeScript classes | Java sealed interface | Strategy pattern]
Constraints: [e.g., "Must remain serializable", "Compatible with DI"]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[function-name]` | Function with conditionals | calculateFees, processPayment |
| `[discriminator]` | What determines the branch | type field, instanceof, enum |
| `[base-type]` | Common interface | FeeCalculator, PaymentProcessor |
| `[implementations]` | Concrete types | StandardFee, PremiumFee, VolumeFee |
| `[factory-method]` | How to instantiate | FeeCalculator.forAccount(type) |

## Example Usage

### TypeScript

```
Refactor calculateFees to replace conditionals with polymorphism:

Current code with conditionals:
function calculateFees(transaction: Transaction, account: Account): number {
  if (account.type === 'PREMIUM') {
    return transaction.amount * 0.005; // 0.5%
  } else if (account.type === 'VOLUME' && transaction.amount > 100000) {
    return transaction.amount * 0.0025; // 0.25%
  } else if (account.type === 'VOLUME') {
    return transaction.amount * 0.005; // 0.5%
  } else {
    // Standard
    if (transaction.amount > 10000) {
      return transaction.amount * 0.008; // 0.8%
    }
    return transaction.amount * 0.01; // 1%
  }
}

The conditional branches on: account.type (PREMIUM, VOLUME, STANDARD)
Each branch handles: Different fee calculation logic per account tier

Create a polymorphic solution that:
1. Base type: FeeCalculator interface with calculate(transaction): number
2. Implementations: PremiumFeeCalculator, VolumeFeeCalculator, StandardFeeCalculator
3. Factory method: FeeCalculatorFactory.forAccount(account): FeeCalculator
4. Shared behavior: Min/max fee capping in base abstract class

Requirements:
1. Eliminate all if/switch statements for fee logic
2. Each calculator handles one account type
3. Adding new account type = new class only (no existing code changes)
4. Factory selects calculator based on account.type

Format: TypeScript classes implementing interface
Constraints: Must be injectable, each calculator stateless
```

### Java

```
Refactor processTransaction to replace conditionals with polymorphism:

Current code with conditionals:
public TransactionResult processTransaction(Transaction tx) {
    switch (tx.getType()) {
        case BUY:
            validateFunds(tx);
            debitAccount(tx.getAmount());
            creditHolding(tx.getSymbol(), tx.getQuantity());
            return new TransactionResult(Status.COMPLETED, "Buy executed");
        case SELL:
            validateHolding(tx);
            debitHolding(tx.getSymbol(), tx.getQuantity());
            creditAccount(tx.getAmount());
            return new TransactionResult(Status.COMPLETED, "Sell executed");
        case DIVIDEND:
            creditAccount(tx.getAmount());
            return new TransactionResult(Status.COMPLETED, "Dividend received");
        case TRANSFER:
            // complex transfer logic...
        default:
            throw new IllegalArgumentException("Unknown type: " + tx.getType());
    }
}

The conditional branches on: tx.getType() (BUY, SELL, DIVIDEND, TRANSFER)
Each branch handles: Different transaction processing logic

Create a polymorphic solution that:
1. Base type: TransactionProcessor sealed interface
2. Implementations: BuyProcessor, SellProcessor, DividendProcessor, TransferProcessor
3. Factory method: TransactionProcessorFactory.getProcessor(TransactionType)
4. Shared behavior: Audit logging, validation framework in abstract base

Requirements:
1. Eliminate switch statement entirely
2. Each processor handles one transaction type
3. Use Java 17 sealed interface to restrict implementations
4. Spring-compatible: processors are @Component beans

Format: Java 17 sealed interface with record implementations where stateless
Constraints: Thread-safe, compatible with Spring DI, auditable
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too many small classes | Class explosion | Only extract truly different behavior |
| Shared state issues | Polymorphic objects become stateful | Keep processors stateless |
| Factory complexity | Factory becomes new conditional hub | Use registry or DI container |
| Over-abstraction | Simple if/else doesn't need this | Only apply when 3+ branches with distinct logic |

## When to Apply

- 3+ branches with substantially different logic
- Branches likely to grow (new types added)
- Each branch has testable independent behavior
- Type switching happens in multiple places

## When NOT to Apply

- Simple 2-way conditionals
- Conditions based on runtime values, not types
- Branches share 90%+ of their code
- One-off conditional in single location

## Expected Output Quality: 8/10

A well-crafted prompt produces:
- Clean polymorphic hierarchy
- Factory for type creation
- Elimination of conditional logic
- Extensible design (OCP compliant)
