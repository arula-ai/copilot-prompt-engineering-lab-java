---
agent: 'agent'
description: 'Extracts a method from existing code while maintaining behavior and improving readability'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: the function name and paste the code to refactor'
---

# Extract Method Pattern

**Category:** Refactoring | **Success Rate:** 90%

Refactor by extracting a method with the following specifications:

## Required Information

Please provide:
- **Function name**: The function containing code to extract
- **Current code**: Paste the code block to extract from
- **What to extract**: Describe the lines/functionality to extract
- **Proposed method name**: e.g., validateTransactionFields, calculateFees
- **Language**: TypeScript or Java 17

## Extraction Details

Specify:
- **Parameters needed**: What the new method requires
- **Return type**: void, ValidationResult, BigDecimal, etc.
- **Visibility**: private, package-private, public

## Requirements

1. **Maintain exact same behavior** - no "improvements" while extracting
2. Add appropriate JSDoc/Javadoc comments
3. Handle edge cases from original context
4. Keep method focused on single responsibility
5. Use minimal visibility needed (private for helpers)

## Constraints

- Must preserve exact original behavior
- Extracted helper methods should typically be private
- Package-private allows unit testing in Java
- No new side effects

---

## When to Extract Methods

| Symptom | Action |
|---------|--------|
| Method > 20 lines | Extract logical sections |
| Code comment explaining "what" | Extract to well-named method |
| Same code in 2+ places | Extract and call from both |
| Nested conditions 3+ deep | Extract inner logic |
| Multiple levels of abstraction | Extract lower-level operations |

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Too many parameters | Extracted method needs 5+ params | Extract parameter object first |
| Changing behavior | Accidental logic changes | Write tests before refactoring |
| Wrong scope | Public when should be private | Use minimal visibility needed |
| Breaking transactions | Extracting from @Transactional | Keep transaction boundary on caller |

## Naming Guidelines

- Name describes **what**, not **how**: `validateOrder` not `checkFieldsAndThrow`
- Use verbs for actions: `calculate`, `validate`, `transform`
- Be specific: `calculateCommission` not `doCalculation`

## Expected Output Quality: 9/10
