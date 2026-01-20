---
agent: 'agent'
description: 'Creates OWASP-aligned input validation with sanitization and security-conscious error handling'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: input type name, fields to validate, and language (TypeScript or Java)'
---

# Input Validation Pattern

**Category:** Security | **Success Rate:** 88%

Create input validation with the following specifications:

## Required Information

Please provide:
- **Input type name**: e.g., LoginRequest, TransactionDTO, UserProfile
- **Input structure**: Paste the interface/record or describe the fields
- **Language**: TypeScript OR Java 17 with Bean Validation
- **Validation rules**: For each field (e.g., email: required, max 254 chars, valid format)

## Security Considerations (OWASP-Aligned)

Specify which apply:
- **Prevent enumeration**: Use generic error messages for auth failures
- **No sensitive data in errors**: Never echo passwords, tokens, or PII
- **Sanitize for injection**: Escape/sanitize before storage or queries
- **Rate limiting hints**: Include in response structure if needed
- **Constant-time comparison**: For sensitive field comparisons

## Return Format Options

1. **Success/Failure object**: `{ valid: true, data: sanitized }` or `{ valid: false, errors: [] }`
2. **Throw ValidationException**: With field-specific error details
3. **Result<T, ValidationError>**: Sealed type for functional handling

## Format Requirements

- Sanitize ALL string inputs (trim whitespace, normalize case as needed)
- Must NOT throw unexpected exceptions
- Field-specific error messages (but never echo sensitive input)
- Synchronous and thread-safe
- NEVER log passwords, tokens, or sensitive data

## OWASP Top 10 Alignment

| OWASP Risk | Validation Defense |
|------------|-------------------|
| A01 Broken Access Control | Validate ownership/authorization |
| A03 Injection | Sanitize + parameterized queries |
| A04 Insecure Design | Validate all inputs server-side |
| A07 Auth Failures | Generic errors prevent enumeration |

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Throwing exceptions | Caller forgets try/catch | Return typed ValidationResult |
| Password in error message | Security leak | Only say "invalid", never echo input |
| Specific "not found" errors | Enables enumeration | Generic "invalid credentials" |
| Missing sanitization | XSS/injection risks | Always sanitize before storage |
| Client-only validation | Easily bypassed | Always validate server-side too |
| Regex ReDoS | Denial of service | Use bounded patterns, test with long inputs |

## Implementation Guidelines

1. **Never echo user input** in error messages
2. **Sanitize before validate** - trim whitespace, normalize case
3. **Use allowlists** over blocklists for format validation
4. **Consider timing attacks** - use constant-time comparisons for sensitive fields
5. **Log validation failures** for security monitoring (without sensitive data)

## Expected Output Quality: 9/10
