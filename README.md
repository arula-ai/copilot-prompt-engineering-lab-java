# Copilot Prompt Engineering Lab - Java 17

A hands-on workshop for learning expert-level prompt engineering with GitHub Copilot using the CRAFT framework.

## Workshop Overview

**Duration:** 75 minutes
**Language:** Java 17
**Goal:** Transform vague prompts into expert prompts that generate 9/10 quality code

## Quick Start

### 1. Setup

**Prerequisites:**
- Java 17 or higher
- Maven 3.8+

```bash
mvn clean compile
mvn test
```

### 2. Open Your Guides

- **Action Guide:** [`LAB_ACTION_GUIDE.md`](LAB_ACTION_GUIDE.md) - Step-by-step walkthrough with examples and hints
- **Progress Tracker:** [`docs/workflow-tracker.md`](docs/workflow-tracker.md) - Track your progress
- **CRAFT Framework:** [`docs/craft-framework/guide.md`](docs/craft-framework/guide.md) - Complete CRAFT reference

### 3. Verify Copilot

- Check Copilot icon is active in your IDE
- Open Copilot Chat panel
- You're ready to begin!

## Lab Structure

### Lab 1: Crafting Effective Prompts (25 min)

Transform 5 vague prompts into expert prompts using the CRAFT framework.

Location: `src/main/java/com/fidelity/promptlab/challenges/lab1/`

| Challenge | Bad Prompt | What You'll Learn |
|-----------|------------|-------------------|
| 1. Login | "Create a login method" | Security-focused authentication |
| 2. Tests | "Write tests for this" | Comprehensive test coverage |
| 3. Bug Fix | "Fix the bug" | Systematic bug identification |
| 4. Errors | "Add error handling" | Resilient error management |
| 5. Optimize | "Optimize this code" | Algorithm optimization |

**Solutions included:** Each challenge has collapsible hints in the Action Guide, plus full expert CRAFT prompts in `solutions/` folders.

### Lab 2: Applying Library Patterns (15 min)

Location: `src/main/java/com/fidelity/promptlab/challenges/lab2/`

Use established patterns from the prompt library to generate production-ready code.

- API service methods with caching and retry
- Comprehensive test suites with JUnit 5 and Mockito
- Input validation with Bean Validation

### Lab 3: Contributing to the Library (10 min)

Create your own reusable prompt pattern and test it for consistency (≥80% success rate).

## The CRAFT Framework

| Element | Question | Example |
|---------|----------|---------|
| **C**ontext | What's the situation? | "Spring Boot 3.x app with JWT auth..." |
| **R**ole | Who should Copilot act as? | "Senior Java developer with security expertise..." |
| **A**ction | What exactly should happen? | "1. Validate input 2. Call service 3. Handle errors..." |
| **F**ormat | How should output look? | "ResponseEntity<Result<T>>, Javadoc, custom exceptions..." |
| **T**one/Constraints | What rules apply? | "SOLID principles, OWASP compliant..." |

Full guide: [`docs/craft-framework/guide.md`](docs/craft-framework/guide.md)

## Java-Specific Considerations

When writing prompts for Java, consider specifying:
- Java version (17 features like records, sealed classes, pattern matching)
- Build tool (Maven/Gradle)
- Frameworks (Spring Boot, Quarkus, etc.)
- Testing framework (JUnit 5, AssertJ, Mockito)
- Logging framework (SLF4J, Log4j2)
- Exception handling strategy (checked vs unchecked)

## Repository Structure

```
copilot-prompt-engineering-lab-java/
├── LAB_ACTION_GUIDE.md              # Step-by-step walkthrough with hints
├── README.md                        # This file
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/java/com/fidelity/promptlab/
│   │   └── challenges/
│   │       ├── lab1/                # CRAFT challenges
│   │       │   └── solutions/       # Expert CRAFT prompt examples
│   │       ├── lab2/                # Library pattern exercises
│   │       └── lab3/                # Pattern contribution
│   └── test/java/                   # Test files
│
├── prompt-library/                  # 18 reusable prompt patterns (.prompt.md)
│   ├── code-generation/             # API services, data transformers
│   ├── testing/                     # Unit tests (JUnit 5, Mockito)
│   ├── refactoring/                 # Extract method, review & refactor
│   ├── documentation/               # Javadoc, README generation
│   ├── security/                    # Validation, auth guards
│   ├── workflow/                    # Planning, commits, feature breakdown
│   └── README.md                    # Library documentation
│
├── .github/
│   └── copilot-chat/                # Chat mode definitions
│       ├── need-review.chatmode.md  # Quality review mode
│       └── hand-off.chatmode.md     # Progress summarizer mode
│
├── docs/
│   ├── craft-framework/
│   │   ├── guide.md                 # Complete CRAFT framework guide
│   │   └── iteration-guide.md       # How to improve prompts iteratively
│   ├── pattern-selector.md          # Decision tree for choosing patterns
│   ├── variable-substitution-guide.md # How to fill in pattern variables
│   ├── workflow-tracker.md          # Progress tracking template
│   ├── rubrics/                     # Quality assessment rubrics
│   │   ├── prompt-quality-rubric.md
│   │   └── pattern-contribution-rubric.md
│   └── examples/                    # Before/after prompt examples
│
└── exercises/                       # Practice exercises
    ├── bad-prompts/                 # Prompts to transform
    ├── good-prompts/                # Reference examples
    └── your-prompts/                # Save your work here
```

## Resources

### Core Guides
- **CRAFT Framework:** [`docs/craft-framework/guide.md`](docs/craft-framework/guide.md)
- **Iteration Guide:** [`docs/craft-framework/iteration-guide.md`](docs/craft-framework/iteration-guide.md)
- **Pattern Selector:** [`docs/pattern-selector.md`](docs/pattern-selector.md)
- **Variable Substitution:** [`docs/variable-substitution-guide.md`](docs/variable-substitution-guide.md)

### Library & Patterns
- **Prompt Library:** [`prompt-library/`](prompt-library/)
- **Before/After Examples:** [`docs/examples/before-after.md`](docs/examples/before-after.md)

### Validation & Quality
- **Quality Rubric:** [`docs/rubrics/prompt-quality-rubric.md`](docs/rubrics/prompt-quality-rubric.md)
- **Contribution Rubric:** [`docs/rubrics/pattern-contribution-rubric.md`](docs/rubrics/pattern-contribution-rubric.md)

---

*Expert prompts are specific, complete, and tested. Use CRAFT for every prompt.*
