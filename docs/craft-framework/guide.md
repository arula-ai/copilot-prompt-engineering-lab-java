# The CRAFT Framework for Prompt Engineering

## Overview

CRAFT is a structured approach to writing effective prompts for AI coding assistants.

## The Framework

### C - Context
**What is the situation?**

Provide background information that helps the AI understand:
- What application/project this is for
- What technologies are in use
- What existing patterns should be followed
- What constraints exist

**Example:**
```
Context: Angular 16 application with NgRx state management,
using our standard service patterns with dependency injection.
This is for the portfolio management module.
```

### R - Role
**Who should the AI act as?**

Specify the expertise level and domain:
- Senior/Junior developer
- Specific specialty (security, performance, testing)
- Domain expertise (financial services, healthcare)

**Example:**
```
Acting as a senior TypeScript developer with expertise in
financial calculations and regulatory compliance...
```

### A - Action
**What exactly should happen?**

Be specific and use numbered lists:
- What to create/modify/fix
- Step-by-step requirements
- Success criteria

**Example:**
```
Create a service method that:
1. Accepts a userId and date range
2. Fetches transactions from the API
3. Aggregates by category
4. Returns a summary with totals
```

### F - Format
**How should the output look?**

Specify:
- Code style (TypeScript, ES6, etc.)
- Documentation requirements
- Structure expectations

**Example:**
```
Format: TypeScript with strict types
Include JSDoc comments for all public methods
Follow Angular style guide conventions
```

### T - Tone/Constraints
**What rules must be followed?**

Include:
- Things to avoid
- Performance requirements
- Compatibility needs
- Style constraints

**Example:**
```
Constraints:
- No external dependencies
- Must be backwards compatible with existing callers
- Maximum 50 lines per method
- Must handle null inputs gracefully
```

## Putting It Together

**Bad Prompt:**
```
Create a login function
```

**CRAFT Prompt:**
```
Context: Express.js API for a financial services application
using JWT authentication with refresh tokens.

Role: Senior backend developer focused on security best practices.

Action: Create a login endpoint handler that:
1. Validates email and password inputs
2. Checks credentials against user database
3. Generates JWT access token (15 min) and refresh token (7 days)
4. Sets httpOnly cookie for refresh token
5. Returns access token in response body
6. Logs authentication attempts for audit

Format: TypeScript async function with full error handling
Include request/response types and JSDoc documentation

Constraints:
- Must use bcrypt for password comparison
- Must rate limit to 5 attempts per minute per IP
- Must not leak whether email exists
- Must sanitize all inputs
```

## Quick Reference

| Element | Question | Key Words |
|---------|----------|-----------|
| Context | What's the situation? | "In our...", "For the...", "Using..." |
| Role | Who's the expert? | "Acting as...", "As a senior..." |
| Action | What to do? | "Create...", "Implement...", "1. 2. 3." |
| Format | How to output? | "Format as...", "Include...", "Style:" |
| Tone | What rules? | "Must not...", "Constraints:", "Avoid..." |
