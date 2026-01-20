---
agent: 'agent'
description: 'Creates an authentication guard/filter with token validation, role-based access, and security best practices'
tools: ['search/changes', 'search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: framework (Angular/Spring), auth mechanism (JWT/Session), and requirements'
---

# Authentication Guard Pattern

**Category:** Security | **Success Rate:** 92%

Create an authentication guard with the following specifications:

## Required Information

Please provide:
- **Framework**: Angular 17+, Spring Boot 3 with Spring Security 6, Express.js, or React Router
- **Auth mechanism**: JWT with refresh tokens, Session cookies, or OAuth 2.0/OIDC
- **Token storage**: httpOnly cookie, localStorage, Authorization header, or memory

## Guard Requirements

Specify:
- **What to check**: Token exists, not expired, has required roles/authorities
- **On success**: Allow navigation, set user context, update activity timestamp
- **On failure**: Redirect to login with returnUrl, return 401, trigger SSO

## Additional Features (select applicable)

- Role-based access via route data or @PreAuthorize
- Automatic token refresh if access token expired but refresh valid
- Remember original URL for post-login redirect
- MFA verification check
- API key support alongside JWT

## Security Requirements

- Never expose tokens in URLs or logs
- Validate token signature before trusting claims
- Clear ALL auth state on logout or invalidation
- Reject tokens with 'none' algorithm
- Log auth failures with request context (no sensitive data)

## Format Requirements

- Full type definitions
- Error handling for all failure modes
- Audit logging hooks
- Testable code structure
- Framework-specific best practices

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Token in URL | Leaks via referrer headers | Use httpOnly cookies or Authorization header |
| Missing expiry check | Expired tokens accepted | Always validate `exp` claim |
| Race condition on refresh | Multiple requests trigger multiple refreshes | Queue requests during refresh |
| Role check after navigation | Unauthorized content briefly visible | Check roles in guard BEFORE allowing |
| Hardcoded secrets | Secrets in source code | Use environment variables |

## Angular Implementation Example

```typescript
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url }
    });
  }

  const requiredRoles = route.data['roles'] as string[] | undefined;
  if (requiredRoles && !authService.hasAnyRole(requiredRoles)) {
    return router.createUrlTree(['/unauthorized']);
  }

  return true;
};
```

## Spring Security Example

```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        // Extract JWT from Authorization header
        // Validate signature and claims
        // Set SecurityContext on success
        // Return 401 with WWW-Authenticate on failure
    }
}
```

## Expected Output Quality: 9/10
