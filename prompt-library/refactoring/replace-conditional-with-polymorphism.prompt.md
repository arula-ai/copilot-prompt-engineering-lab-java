---
agent: 'agent'
description: 'Refactors conditional logic (if/else, switch) into a polymorphic class hierarchy'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: function with if/else or switch statements to refactor'
---

# Replace Conditional with Polymorphism Pattern

**Category:** Refactoring | **Success Rate:** 85%

Refactor conditionals to polymorphism with the following specifications:

## Required Information

Please provide:
- **Function name**: The function with conditionals
- **Current code**: Paste the code with if/else or switch statements
- **Language**: TypeScript or Java 17

## Analysis

Identify:
- **Discriminator**: What the conditional branches on (e.g., account.type, tx.getType())
- **Branch descriptions**: What each branch handles

## Polymorphic Solution Design

Specify:
- **Base type name**: Interface or abstract class (e.g., FeeCalculator, TransactionProcessor)
- **Implementations**: List concrete types (e.g., PremiumFeeCalculator, StandardFeeCalculator)
- **Factory method**: How to create correct type (e.g., FeeCalculatorFactory.forAccount(account))
- **Shared behavior**: What goes in base class (e.g., logging, validation)

## Requirements

1. Eliminate ALL if/switch statements for this logic
2. Each variant class has single responsibility
3. Adding new variants WITHOUT modifying existing code (Open/Closed Principle)
4. Include factory or registry for type creation
5. Make implementations injectable/testable

## Format

- **TypeScript**: Classes implementing interface
- **Java**: Sealed interface with record implementations (where stateless)

---

## When to Apply

- 3+ branches with substantially different logic
- Branches likely to grow (new types added over time)
- Each branch has testable independent behavior
- Type switching happens in multiple places

## When NOT to Apply

- Simple 2-way conditionals
- Conditions based on runtime values, not types
- Branches share 90%+ of their code
- One-off conditional in single location

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too many small classes | Class explosion | Only extract truly different behavior |
| Shared state issues | Polymorphic objects become stateful | Keep processors stateless |
| Factory complexity | Factory becomes new conditional hub | Use registry or DI container |
| Over-abstraction | Simple if/else doesn't need this | Only apply when 3+ distinct branches |

## Java Example with Sealed Interface

```java
public sealed interface TransactionProcessor
    permits BuyProcessor, SellProcessor, DividendProcessor {

    TransactionResult process(Transaction tx);
}

public record BuyProcessor(AccountService accounts) implements TransactionProcessor {
    @Override
    public TransactionResult process(Transaction tx) {
        // Buy-specific logic only
    }
}
```

## Expected Output Quality: 8/10
