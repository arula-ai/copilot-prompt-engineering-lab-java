---
agent: 'agent'
description: 'Generates a standardized commit message following Conventional Commits specification'
tools: ['search/changes', 'runCommands/runInTerminal']
argument-hint: 'Run after staging changes - will analyze git diff and generate commit message'
---

# Conventional Commit Generator

**Category:** Workflow | **Success Rate:** 95%

Generate a commit message following the [Conventional Commits](https://www.conventionalcommits.org/) specification.

## Workflow

1. **Analyze staged changes** using `git diff --staged`
2. **Determine commit type** based on the nature of changes
3. **Identify scope** from affected files/modules
4. **Write description** in imperative mood
5. **Generate commit command** ready to execute

## Commit Message Format

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

## Commit Types

| Type | When to Use |
|------|-------------|
| `feat` | New feature for the user |
| `fix` | Bug fix for the user |
| `docs` | Documentation only changes |
| `style` | Formatting, missing semicolons (no code change) |
| `refactor` | Code change that neither fixes nor adds feature |
| `perf` | Performance improvement |
| `test` | Adding or correcting tests |
| `build` | Changes to build system or dependencies |
| `ci` | Changes to CI configuration |
| `chore` | Other changes that don't modify src or test |
| `revert` | Reverts a previous commit |

## Rules

1. **Description**: Use imperative mood ("add" not "added" or "adds")
2. **Scope**: Optional but recommended - identifies the affected area
3. **Length**: Description should be 50 characters or less
4. **Breaking changes**: Add `!` after type/scope and explain in footer

## Examples

```bash
# Simple feature
feat(auth): add password reset functionality

# Bug fix with scope
fix(api): handle null response from user service

# Breaking change
feat(api)!: change authentication to OAuth 2.0

BREAKING CHANGE: JWT tokens are no longer accepted.
Use OAuth 2.0 tokens instead.

# Documentation
docs(readme): add installation instructions

# Refactoring
refactor(portfolio): extract validation into separate module
```

## Output Format

Provide the commit message and the git command:

```bash
git commit -m "<type>(<scope>): <description>"
```

Or for multi-line messages:

```bash
git commit -m "<type>(<scope>): <description>" -m "<body>" -m "<footer>"
```

---

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Past tense | "added feature" | Use imperative: "add feature" |
| Too vague | "fix bug" | Be specific: "fix null pointer in login" |
| Missing scope | "feat: add button" | Add scope: "feat(ui): add submit button" |
| Too long | 80+ character description | Keep under 50 characters |

## Expected Output Quality: 9/10
