# Pattern: Authentication Guard

**Category:** Security
**Language:** TypeScript/Angular, Java/Spring
**Success Rate:** 92%
**Last Verified:** December 2025

## Prompt Template

```
Create an authentication guard for [framework] that:

Context:
- Framework: [Angular/React/Express]
- Auth mechanism: [JWT/Session/OAuth]
- Token storage: [localStorage/httpOnly cookie/memory]

Guard requirements:
1. Check: [what to verify]
2. On success: [what happens]
3. On failure: [where to redirect/what to return]

Additional features:
- [feature-1, e.g., "Role-based access"]
- [feature-2, e.g., "Token refresh handling"]
- [feature-3, e.g., "Remember original URL"]

Security requirements:
- [requirement-1]
- [requirement-2]

Format: [Guard/Middleware/HOC] with full types
Include: Error handling, logging hooks, testability
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[framework]` | Target framework | Angular 17, React 18, Express 4, Spring Boot 3 |
| `[auth-mechanism]` | Authentication type | JWT, Session cookies, OAuth 2.0, OIDC |
| `[token-storage]` | Where tokens are stored | localStorage, httpOnly cookie, memory, sessionStorage |
| `[what-to-verify]` | Auth check logic | Token exists + not expired, Session valid, Has required role |
| `[what-happens]` | Success behavior | Allow navigation, Return next(), Set user context |
| `[where-to-redirect]` | Failure behavior | /login with returnUrl, 401 response, SSO redirect |
| `[feature-N]` | Additional capabilities | Role-based access, Token refresh, URL preservation, MFA check |
| `[requirement-N]` | Security constraints | No token in URL, Validate on every request, Log auth failures |

## Example Usage

### Angular 17+ Functional Guard

```
Create an authentication guard for Angular 17 that:

Context:
- Framework: Angular 17 with standalone components
- Auth mechanism: JWT with refresh tokens
- Token storage: httpOnly cookie for refresh, memory for access token

Guard requirements:
1. Check: Access token exists in AuthService and is not expired
2. On success: Allow navigation, update last activity timestamp
3. On failure: Redirect to /login with returnUrl query param

Additional features:
- Role-based access via route data: { roles: ['ADMIN', 'MANAGER'] }
- Automatic token refresh if access token expired but refresh valid
- Store intended URL for post-login redirect

Security requirements:
- Never expose tokens in URLs or logs
- Validate token signature client-side before trusting claims
- Clear all auth state on logout or token invalidation

Format: Functional guard (CanActivateFn) using inject()
Include: Error handling, audit logging hook, unit test examples
```

### Spring Boot 3 Security Filter

```
Create an authentication guard for Spring Boot 3 that:

Context:
- Framework: Spring Boot 3.2 with Spring Security 6
- Auth mechanism: JWT (RS256 signed)
- Token storage: Authorization header (Bearer token)

Guard requirements:
1. Check: Valid JWT signature, not expired, has required authorities
2. On success: Set SecurityContext with authenticated principal
3. On failure: Return 401 with WWW-Authenticate header

Additional features:
- Role-based access via @PreAuthorize annotations
- Extract custom claims (userId, tenantId) into principal
- Support both access tokens and API keys

Security requirements:
- Validate issuer and audience claims
- Reject tokens with 'none' algorithm
- Log authentication failures with request context (no sensitive data)

Format: OncePerRequestFilter with SecurityFilterChain configuration
Include: Exception handling, test configuration, OpenAPI security scheme
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Token in URL | Tokens in query params leak via referrer headers | Use httpOnly cookies or Authorization header |
| Missing expiry check | Expired tokens accepted | Always validate `exp` claim before trusting |
| Race condition on refresh | Multiple requests trigger multiple refreshes | Queue requests during refresh, use singleton refresh |
| Role check after navigation | Unauthorized content briefly visible | Check roles in guard before allowing navigation |
| Hardcoded secrets | Secrets in source code | Use environment variables, secret managers |

## Related Patterns

- [api-service-method.md](../code-generation/api-service-method.md) - For auth API calls
- [error-handler.md](./error-handler.md) - For handling 401/403 responses

## Expected Output Quality: 9/10

A well-crafted prompt using this pattern typically produces:
- Correct framework-specific implementation
- Proper error handling and edge cases
- Security best practices included
- Testable code structure
