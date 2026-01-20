# Fidelity Copilot Prompt Library

A curated collection of tested, approved prompts for GitHub Copilot using the **official prompt file format**.

## Purpose

This library provides:
- **Consistent patterns** across teams
- **Proven prompts** for common tasks with 85-95% success rates
- **Interactive guidance** via `argument-hint` when used in VS Code
- **Quality-assured outputs** aligned with enterprise standards

## Structure

```
prompt-library/
├── code-generation/     # Creating new code
│   ├── api-service-method.prompt.md
│   ├── api-service-method-java.prompt.md
│   └── data-transformer.prompt.md
├── testing/             # Test creation and enhancement
│   ├── unit-test-suite.prompt.md
│   └── unit-test-suite-java.prompt.md
├── refactoring/         # Improving existing code
│   ├── extract-method.prompt.md
│   ├── introduce-parameter-object.prompt.md
│   ├── replace-conditional-with-polymorphism.prompt.md
│   ├── simplify-complex-expression.prompt.md
│   └── review-and-refactor.prompt.md       # NEW
├── documentation/       # Docs and comments
│   ├── jsdoc-generation.prompt.md
│   └── readme-generator.prompt.md          # NEW
├── security/            # Security-focused patterns
│   ├── input-validation.prompt.md
│   ├── input-validation-java.prompt.md
│   └── auth-guard.prompt.md
└── workflow/            # Planning and process (NEW)
    ├── conventional-commit.prompt.md
    ├── implementation-plan.prompt.md
    └── feature-breakdown.prompt.md
```

## How to Use

### Option 1: Copy to `.github/prompts/` (Recommended)

1. Copy the desired `.prompt.md` file to your project's `.github/prompts/` directory
2. In VS Code with GitHub Copilot Chat, type `/` followed by the prompt name
3. The `argument-hint` will guide you on what information to provide

### Option 2: Use Directly in Copilot Chat

1. Open the `.prompt.md` file
2. Copy the content (after the YAML frontmatter `---`)
3. Paste into Copilot Chat and provide the requested information

### Option 3: Run from Command Palette

1. Open VS Code Command Palette (Cmd/Ctrl + Shift + P)
2. Type "Chat: Run Prompt"
3. Select the prompt from your `.github/prompts/` folder

## Prompt File Format

Each `.prompt.md` file follows the [official VS Code Copilot prompt file format](https://code.visualstudio.com/docs/copilot/customization/prompt-files):

```markdown
---
agent: 'agent'
description: 'Brief description of what this prompt does'
tools: ['search/codebase', 'edit/editFiles', 'read/problems']
argument-hint: 'Guide the user on what to provide'
---

# Prompt Title

Your prompt content here...
```

### Supported Frontmatter Attributes

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `agent` | Execution mode | `'agent'`, `'ask'`, `'edit'` |
| `description` | Shows in Copilot's prompt picker | `'Creates an API service method'` |
| `tools` | Available tools for the prompt | `['search/codebase', 'edit/editFiles']` |
| `argument-hint` | Guidance shown in chat input | `'Provide: service name, method name'` |
| `name` | Display name (defaults to filename) | `'api-service'` |
| `model` | Specific model to use | `'gpt-4o'`, `'claude-sonnet-4'` |

### Available Tools

| Tool | Purpose |
|------|---------|
| `search/codebase` | Search code in workspace |
| `search/changes` | View recent changes |
| `edit/editFiles` | Modify files |
| `read/problems` | View diagnostics/errors |
| `runCommands/runInTerminal` | Execute terminal commands |

## Available Patterns

### Code Generation

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| API Service Method | `api-service-method.prompt.md` | 94% | HTTP services with caching/retry |
| API Service (Java) | `api-service-method-java.prompt.md` | 93% | Spring Boot services |
| Data Transformer | `data-transformer.prompt.md` | 91% | Type-safe transformations |

### Testing

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| Unit Test Suite | `unit-test-suite.prompt.md` | 89% | Jest/Vitest test generation |
| Unit Test (Java) | `unit-test-suite-java.prompt.md` | 91% | JUnit 5 + Mockito tests |

### Security

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| Input Validation | `input-validation.prompt.md` | 88% | OWASP-aligned validation |
| Input Validation (Java) | `input-validation-java.prompt.md` | 90% | Bean Validation patterns |
| Auth Guard | `auth-guard.prompt.md` | 92% | Route/endpoint protection |

### Refactoring

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| Extract Method | `extract-method.prompt.md` | 90% | Extract reusable methods |
| Parameter Object | `introduce-parameter-object.prompt.md` | 88% | Replace long parameter lists |
| Polymorphism | `replace-conditional-with-polymorphism.prompt.md` | 85% | Replace if/switch with classes |
| Simplify Expression | `simplify-complex-expression.prompt.md` | 91% | Clean up complex conditionals |
| Review & Refactor | `review-and-refactor.prompt.md` | 87% | Code review with suggestions |

### Documentation

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| JSDoc/Javadoc | `jsdoc-generation.prompt.md` | 92% | Generate comprehensive docs |
| README Generator | `readme-generator.prompt.md` | 91% | Create project documentation |

### Workflow (NEW)

| Pattern | File | Success Rate | Description |
|---------|------|-------------|-------------|
| Conventional Commit | `conventional-commit.prompt.md` | 95% | Standardized commit messages |
| Implementation Plan | `implementation-plan.prompt.md` | 88% | Plan before coding (CRAFT) |
| Feature Breakdown | `feature-breakdown.prompt.md` | 90% | Break epics into tasks |

## Contributing

See contribution guidelines in each pattern template.
Patterns require **5+ successful uses** before submission.

### Creating New Patterns

1. Use the official `.prompt.md` format with YAML frontmatter
2. Include `agent`, `description`, `tools`, and `argument-hint`
3. Provide clear "Required Information" section in the body
4. Include common pitfalls and implementation hints
5. Test your pattern at least 5 times
6. Document success rate and edge cases discovered

## Resources

### Using Patterns
- [Pattern Selector](../docs/pattern-selector.md) - Find the right pattern for your task
- [Variable Substitution Guide](../docs/variable-substitution-guide.md) - How to fill in `[variables]`
- [CRAFT Framework Guide](../docs/craft-framework/guide.md) - The prompting framework
- [Iteration Guide](../docs/craft-framework/iteration-guide.md) - Improve prompts that don't work

### Contributing Patterns
- [Pattern Contribution Rubric](../docs/rubrics/pattern-contribution-rubric.md) - Validation criteria

### External References
- [VS Code Prompt Files Documentation](https://code.visualstudio.com/docs/copilot/customization/prompt-files)
- [GitHub Awesome Copilot Prompts](https://github.com/github/awesome-copilot/tree/main/prompts)
- [Conventional Commits Specification](https://www.conventionalcommits.org/)

## Legacy Files

The original `.md` files (without `.prompt.md` extension) are kept for reference. The new `.prompt.md` files use the official GitHub Copilot prompt file format.
