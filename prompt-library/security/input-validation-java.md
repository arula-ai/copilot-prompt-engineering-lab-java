# Pattern: Input Validation (Java)

**Category:** Security
**Language:** Java 17 / Bean Validation
**Success Rate:** 90%
**Last Verified:** December 2025

## Prompt Template

```
Create input validation for [input-type] in Java 17:

Input structure:
[paste record/class or describe input]

Validation approach: [Bean Validation annotations / Custom Validator / both]

Validation rules:
1. [field-1]: [validation-rules]
2. [field-2]: [validation-rules]
3. [field-3]: [validation-rules]

Security considerations:
- [security-concern-1]
- [security-concern-2]

Return format:
- Success: Validated and sanitized [input-type]
- Failure: [ValidationException / ConstraintViolationException / custom Result type]

Constraints:
- Must sanitize all string inputs (trim, escape)
- Must not throw unchecked exceptions unexpectedly
- Must provide field-specific error messages
- Must be thread-safe
```

## Example Usage

```
Create input validation for LoginRequest in Java 17:

Input structure:
public record LoginRequest(
    String email,
    String password,
    boolean rememberMe
) {}

Validation approach: Bean Validation annotations + custom validator for security

Validation rules:
1. email: @NotBlank, @Email, @Size(max=254), normalize to lowercase
2. password: @NotBlank, @Size(min=8, max=128), no leading/trailing whitespace
3. rememberMe: Optional, defaults to false

Security considerations:
- Prevent email enumeration (generic error messages in responses)
- Never log or include password in error messages
- Sanitize email to prevent header injection
- Consider rate limiting hint in validation response

Return format:
- Success: Validated LoginRequest with sanitized email
- Failure: ValidationResult with field-specific errors (no sensitive data)

Constraints:
- Use Jakarta Bean Validation 3.0 annotations
- Create custom @ValidEmail annotation if needed
- Thread-safe (immutable record)
- Include Javadoc with security notes
```

## Expected Output Quality: 9/10

## Java-Specific Annotations Reference

| Validation | Annotation |
|------------|------------|
| Not null | @NotNull |
| Not empty string | @NotBlank |
| Email format | @Email |
| Size range | @Size(min=, max=) |
| Pattern | @Pattern(regexp=) |
| Range | @Min, @Max |
| Custom | @Constraint + ConstraintValidator |
