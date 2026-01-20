---
agent: 'agent'
description: 'Generates a comprehensive README.md for a project or module based on codebase analysis'
tools: ['search/codebase', 'search/changes', 'edit/editFiles']
argument-hint: 'Provide: project/module path or describe what the README should cover'
---

# README Generator

**Category:** Documentation | **Success Rate:** 91%

Generate a comprehensive, well-structured README.md by analyzing the codebase.

## Required Information

Please provide:
- **Scope**: Entire project OR specific module/package
- **Audience**: Developers, end users, or both
- **Existing README**: Update existing OR create new

## README Structure

### Essential Sections

1. **Title and Description**
   - Clear project name
   - One-line description
   - Badges (build status, version, license)

2. **Quick Start**
   - Installation command
   - Minimal usage example
   - "Hello World" equivalent

3. **Installation**
   - Prerequisites
   - Step-by-step installation
   - Verification steps

4. **Usage**
   - Basic usage examples
   - Common use cases
   - Code snippets

5. **API Reference** (if applicable)
   - Key functions/methods
   - Parameters and return values
   - Examples for each

### Optional Sections

6. **Configuration**
   - Environment variables
   - Config file options
   - Default values

7. **Architecture** (for complex projects)
   - High-level overview
   - Directory structure
   - Key components

8. **Contributing**
   - How to contribute
   - Development setup
   - Code standards

9. **Testing**
   - How to run tests
   - Test coverage
   - Writing new tests

10. **Troubleshooting**
    - Common issues
    - FAQ
    - Where to get help

11. **License**
    - License type
    - Copyright notice

## Analysis Process

1. **Scan package.json / pom.xml / build files**
   - Name, version, description
   - Dependencies
   - Scripts/commands

2. **Analyze directory structure**
   - Source organization
   - Key directories
   - Entry points

3. **Review existing documentation**
   - Code comments
   - JSDoc/Javadoc
   - Existing docs

4. **Identify key exports/APIs**
   - Public interfaces
   - Main classes/functions
   - Configuration options

## Output Format

```markdown
# Project Name

> One-line description of what this project does

[![Build Status](badge-url)](link)
[![Version](badge-url)](link)
[![License](badge-url)](link)

## Quick Start

\`\`\`bash
npm install project-name
\`\`\`

\`\`\`typescript
import { something } from 'project-name';

// Minimal example
const result = something();
\`\`\`

## Installation

### Prerequisites

- Node.js >= 18
- npm >= 9

### Steps

1. Clone the repository
   \`\`\`bash
   git clone https://github.com/org/project.git
   \`\`\`

2. Install dependencies
   \`\`\`bash
   npm install
   \`\`\`

3. Configure environment
   \`\`\`bash
   cp .env.example .env
   \`\`\`

## Usage

### Basic Example

\`\`\`typescript
// Example code here
\`\`\`

### Advanced Usage

...

## Configuration

| Variable | Description | Default |
|----------|-------------|---------|
| `API_URL` | Backend API URL | `http://localhost:3000` |
| `DEBUG` | Enable debug logging | `false` |

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md) for guidelines.

## License

MIT © [Organization Name]
```

---

## README Best Practices

| Practice | Why |
|----------|-----|
| Lead with value | Users decide in seconds whether to use it |
| Show, don't tell | Code examples > descriptions |
| Keep it current | Outdated docs are worse than none |
| Use consistent formatting | Improves scannability |
| Include troubleshooting | Reduces support burden |

## Badge Resources

```markdown
<!-- Build Status -->
![Build](https://github.com/org/repo/actions/workflows/ci.yml/badge.svg)

<!-- npm Version -->
![npm](https://img.shields.io/npm/v/package-name)

<!-- License -->
![License](https://img.shields.io/badge/license-MIT-blue.svg)

<!-- Coverage -->
![Coverage](https://img.shields.io/codecov/c/github/org/repo)
```

## Common Pitfalls to Avoid

| Pitfall | Problem | Solution |
|---------|---------|----------|
| No quick start | Users abandon | Add 3-step quick start |
| Wall of text | Not scannable | Use headers, lists, tables |
| Outdated examples | Broken code | Test examples in CI |
| Missing prerequisites | Installation fails | List all requirements |

## Expected Output Quality: 9/10
