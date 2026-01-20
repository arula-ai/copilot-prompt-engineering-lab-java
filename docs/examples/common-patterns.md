# Common Prompt Patterns

Quick reference for frequently used prompt structures.

## Pattern: Specific Action List

```
Create/Implement/Fix [thing] that:
1. [First requirement]
2. [Second requirement]
3. [Third requirement]
```

**Why it works:** Numbered lists are unambiguous and checkable.

---

## Pattern: Error Handling Specification

```
Error handling:
- [Error type 1]: [How to handle]
- [Error type 2]: [How to handle]
- Default: [Fallback behavior]
```

**Why it works:** Explicitly covers all failure modes.

---

## Pattern: Format Specification

```
Format:
- Language: [TypeScript/Python/etc.]
- Style: [Functional/OOP/etc.]
- Include: [JSDoc/types/tests]
- Exclude: [Comments/logging/etc.]
```

**Why it works:** Removes ambiguity about output structure.

---

## Pattern: Constraint List

```
Constraints:
- Must: [Required behavior]
- Must not: [Forbidden behavior]
- Should: [Preferred behavior]
- May: [Optional behavior]
```

**Why it works:** Clear priority of requirements using RFC 2119 language.

---

## Pattern: Context Setting

```
Context:
- Application: [Type and version]
- Existing code: [Patterns to follow]
- Dependencies: [Available libraries]
- Environment: [Browser/Node/etc.]
```

**Why it works:** Grounds the AI in your specific situation.

---

## Pattern: Role Assignment

```
Acting as a [seniority] [specialty] developer with expertise in [domain]:
```

Examples:
- "Acting as a senior security engineer with expertise in OWASP guidelines"
- "Acting as a performance-focused backend developer"
- "Acting as a QA engineer specializing in edge cases"

**Why it works:** Activates domain-specific knowledge.

---

## Anti-Patterns to Avoid

| Anti-Pattern | Problem | Fix |
|--------------|---------|-----|
| "Make it better" | No criteria for "better" | Specify metrics |
| "Add some tests" | No coverage target | List test categories |
| "Handle errors" | No error types | List specific errors |
| "Clean this up" | Subjective | Specify what to change |
| "Optimize" | No target metric | Specify time/space/readability |
