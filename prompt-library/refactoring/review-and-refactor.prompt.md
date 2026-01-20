---
agent: 'agent'
description: 'Performs code review with actionable refactoring suggestions following project coding standards'
tools: ['search/codebase', 'search/changes', 'edit/editFiles', 'read/problems']
argument-hint: 'Provide: file path or paste code to review'
---

# Code Review and Refactor

**Category:** Refactoring | **Success Rate:** 87%

Perform a comprehensive code review with specific, actionable refactoring suggestions.

## Required Information

Please provide:
- **Code to review**: File path OR paste the code
- **Context**: What the code does (if not obvious)
- **Focus areas** (optional): Performance, security, readability, testability

## Review Process

### 1. First Pass: Understand
- What does this code do?
- What is the intent?
- What patterns are being used?

### 2. Second Pass: Analyze
- Check against coding standards
- Identify code smells
- Look for potential bugs
- Assess testability

### 3. Third Pass: Recommend
- Prioritize issues by severity
- Provide specific fixes
- Explain the "why" behind each suggestion

## Review Categories

### 🔴 Critical (Must Fix)
- Security vulnerabilities
- Potential runtime errors
- Data loss risks
- Breaking changes

### 🟡 Important (Should Fix)
- Performance issues
- Code smells
- Missing error handling
- Poor testability

### 🟢 Suggestions (Nice to Have)
- Readability improvements
- Documentation gaps
- Style consistency
- Minor optimizations

## Review Checklist

### Code Quality
- [ ] Single Responsibility Principle followed
- [ ] Functions/methods are focused and small (< 20 lines)
- [ ] No code duplication (DRY)
- [ ] Clear, descriptive naming
- [ ] Appropriate abstraction level

### Error Handling
- [ ] All error paths handled
- [ ] Errors don't expose sensitive info
- [ ] Appropriate error types used
- [ ] Graceful degradation where applicable

### Security (OWASP-Aligned)
- [ ] Input validation present
- [ ] No hardcoded secrets
- [ ] SQL/injection prevention
- [ ] Authentication/authorization checked
- [ ] Sensitive data not logged

### Performance
- [ ] No N+1 queries
- [ ] Appropriate caching
- [ ] No unnecessary computations in loops
- [ ] Memory leaks prevented

### Testability
- [ ] Dependencies injectable
- [ ] Pure functions where possible
- [ ] Clear inputs and outputs
- [ ] Side effects isolated

## Output Format

```markdown
# Code Review: [File/Component Name]

## Summary
[1-2 sentence overview of code quality]

## Critical Issues 🔴
### Issue 1: [Title]
**Location:** Line X-Y
**Problem:** [Description]
**Fix:**
\`\`\`typescript
// Before
[problematic code]

// After
[fixed code]
\`\`\`
**Why:** [Explanation]

## Important Issues 🟡
...

## Suggestions 🟢
...

## What's Good ✅
- [Positive observation 1]
- [Positive observation 2]

## Refactoring Priority
1. [First thing to fix]
2. [Second thing to fix]
3. [Third thing to fix]
```

---

## Common Code Smells

| Smell | Symptom | Refactoring |
|-------|---------|-------------|
| Long Method | > 20 lines | Extract Method |
| Long Parameter List | > 3 params | Introduce Parameter Object |
| Duplicated Code | Same code in 2+ places | Extract and reuse |
| Feature Envy | Method uses other class's data | Move Method |
| Data Clumps | Same fields grouped together | Extract Class |
| Primitive Obsession | Using primitives for domain concepts | Create Value Objects |
| Switch Statements | Type-based branching | Replace with Polymorphism |
| Speculative Generality | "Just in case" abstractions | Remove unused abstraction |

## Review Tone Guidelines

- Be specific, not vague: "Line 42 has potential null pointer" not "watch out for nulls"
- Be constructive: Explain WHY and HOW to fix
- Acknowledge good practices: Positive reinforcement matters
- Prioritize: Not everything needs fixing immediately

## Expected Output Quality: 9/10
