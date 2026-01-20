---
agent: 'agent'
description: 'Creates Java Bean Validation with custom validators and security-conscious error handling'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: input record/class name, fields, and validation rules'
---

# Input Validation Pattern (Java)

**Category:** Security | **Language:** Java 17 / Bean Validation | **Success Rate:** 90%

Create Java input validation with the following specifications:

## Required Information

Please provide:
- **Input type name**: e.g., LoginRequest, TradeOrderRequest
- **Input structure**: Paste the record/class or describe fields
- **Validation rules**: For each field with constraints

## Validation Approach Options

1. **Bean Validation annotations only**: @NotNull, @Email, @Size, @Pattern
2. **Bean Validation + custom validators**: For complex business rules
3. **Programmatic validation**: With ValidationResult<T> sealed type

## Security Considerations

- **Prevent IDOR**: Validate resource ownership (accountId belongs to user)
- **Prevent enumeration**: Generic error messages for auth failures
- **Sanitize inputs**: Escape before downstream queries
- **Log securely**: Log validation failures without sensitive data

## Return Format Options

1. **ValidationResult<T> sealed interface**: Valid/Invalid with errors
2. **Throw ValidationException**: With field-level details
3. **Spring BindingResult**: Standard Spring pattern

## Format Requirements

- Jakarta Bean Validation 3.0 annotations
- Custom @Constraint where needed
- Thread-safe (use immutable records)
- Javadoc with security notes
- i18n-ready error messages

---

## Bean Validation Annotations Reference

| Validation | Annotation |
|------------|------------|
| Not null | @NotNull |
| Not empty string | @NotBlank |
| Email format | @Email |
| Size range | @Size(min=, max=) |
| Pattern | @Pattern(regexp=) |
| Range | @Min, @Max, @DecimalMin, @DecimalMax |
| Custom | @Constraint + ConstraintValidator |

## Example: Sealed ValidationResult

```java
public sealed interface ValidationResult<T> permits Valid, Invalid {
    record Valid<T>(T value) implements ValidationResult<T> {}
    record Invalid<T>(List<ValidationError> errors) implements ValidationResult<T> {}
}

public record ValidationError(String field, String code, String message) {}
```

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Throwing exceptions | Caller forgets try/catch | Return typed ValidationResult |
| Specific "not found" errors | Enables enumeration | Generic "invalid" message |
| Missing IDOR check | Unauthorized access | Validate resource ownership |
| Echo user input | Information disclosure | Never include input in errors |

## Expected Output Quality: 9/10
