# Challenge 1: Login Method - SOLUTION

## Bad Prompt
```
Create a login method
```

## Expert CRAFT Prompt

```
Context:
- Spring Boot 3 financial services application
- JWT authentication with refresh tokens
- Spring Security 6 for authentication
- Must integrate with existing UserRepository and JwtTokenService
- Uses BCrypt for password hashing

Role: Senior Java developer with Spring Security expertise in financial applications.

Action: Create a login method in AuthenticationService that:
1. Accepts LoginRequest record (email: String, password: String, rememberMe: boolean)
2. Validates inputs (email format, password not blank)
3. Loads user from UserRepository, throw UsernameNotFoundException if missing
4. Verify password with BCryptPasswordEncoder
5. On success: generate JWT access token (15 min) and refresh token (rememberMe ? 30 days : 24 hours)
6. On invalid password: throw BadCredentialsException (DO NOT reveal which field failed)
7. Track login attempts (lock account after 5 failures for 15 minutes)
8. Log attempt with MDC context (userId, IP, success/failure) - NEVER log passwords

Format: Java 17+ with:
- Record types for DTOs (LoginRequest, LoginResponse, AuthTokens)
- Optional<User> handling with orElseThrow
- Result<T, E> pattern for typed errors OR throw specific exceptions
- Javadoc with @param, @return, @throws
- @Service, @Transactional where appropriate

Constraints:
- Never log passwords or tokens
- Passwords must be compared with timing-safe BCrypt.matches()
- Rate limit awareness (throw TooManyRequestsException on lockout)
- Clear any sensitive data from memory on failure
- Follow OWASP authentication guidelines
```

## Why This Works

| CRAFT Element | What It Provides | Impact |
|---------------|------------------|--------|
| **Context** | Framework, auth mechanism, dependencies | AI knows Spring Security patterns |
| **Role** | Security + financial expertise | Gets OWASP-compliant patterns |
| **Action** | 8 numbered requirements | Nothing ambiguous |
| **Format** | Records, Optional, Javadoc | Idiomatic Java 17+ output |
| **Constraints** | Security rules, timing safety | Avoids common vulnerabilities |

## Expected Output Quality: 9/10

The AI should generate:

```java
public record LoginRequest(
    @NotBlank @Email String email,
    @NotBlank String password,
    boolean rememberMe
) {}

public record LoginResponse(
    String accessToken,
    String refreshToken,
    long expiresIn,
    UserInfo user
) {}

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final LoginAttemptService loginAttemptService;

    /**
     * Authenticates a user and generates JWT tokens.
     *
     * @param request the login credentials
     * @return login response with tokens and user info
     * @throws BadCredentialsException if credentials are invalid
     * @throws LockedException if account is locked due to failed attempts
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        // Check if account is locked
        if (loginAttemptService.isBlocked(email)) {
            log.warn("Login attempt for locked account: {}", email);
            throw new LockedException("Account temporarily locked. Try again later.");
        }

        // Load user
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                loginAttemptService.recordFailure(email);
                return new BadCredentialsException("Invalid credentials");
            });

        // Verify password (timing-safe comparison)
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            loginAttemptService.recordFailure(email);
            log.info("Failed login attempt for user: {}", email);
            throw new BadCredentialsException("Invalid credentials");
        }

        // Clear failed attempts on success
        loginAttemptService.clearFailures(email);

        // Generate tokens
        Duration accessTokenDuration = Duration.ofMinutes(15);
        Duration refreshTokenDuration = request.rememberMe()
            ? Duration.ofDays(30)
            : Duration.ofDays(1);

        String accessToken = jwtTokenService.generateAccessToken(user, accessTokenDuration);
        String refreshToken = jwtTokenService.generateRefreshToken(user, refreshTokenDuration);

        log.info("Successful login for user: {}", email);

        return new LoginResponse(
            accessToken,
            refreshToken,
            accessTokenDuration.toSeconds(),
            UserInfo.from(user)
        );
    }
}
```

## Key Security Patterns Used

1. **Same error message for user not found AND wrong password** - Prevents username enumeration
2. **Timing-safe password comparison** - BCrypt.matches() is constant-time
3. **Account lockout** - Prevents brute force attacks
4. **No sensitive data in logs** - Only email, never passwords/tokens
5. **Input sanitization** - trim() and toLowerCase() on email

## Common Variations

### With MFA Support
```
Additional requirements:
- Check if user has MFA enabled
- If MFA enabled, return partial response with mfaRequired: true
- Accept optional totpCode in LoginRequest
- Verify TOTP code using TOTP library
```

### With Audit Trail
```
Additional requirements:
- Record login event in audit_log table
- Include: userId, timestamp, IP address, user agent, success/failure
- Use @Async for non-blocking audit recording
```
