# Copilot Instructions - Prompt Engineering Lab

This repository is a hands-on workshop for learning effective prompt engineering with GitHub Copilot using the CRAFT framework.

## Repository Structure

```
copilot-prompt-engineering-lab/
├── angular/                    # TypeScript/Angular challenges
│   ├── src/challenges/
│   │   ├── lab1-basic/        # CRAFT framework exercises
│   │   │   ├── solutions/     # Expert prompt solutions
│   │   ├── lab2-library/      # Library pattern exercises
│   │   └── lab3-contribute/   # Pattern contribution
│   └── LAB_ACTION_GUIDE.md    # Step-by-step Angular guide
├── java/                       # Java 17 challenges
│   ├── src/main/java/.../challenges/
│   │   ├── lab1/              # CRAFT framework exercises
│   │   │   ├── solutions/     # Expert prompt solutions
│   │   ├── lab2/              # Library pattern exercises
│   │   └── lab3/              # Pattern contribution
│   └── LAB_ACTION_GUIDE.md    # Step-by-step Java guide
├── prompt-library/             # Reusable prompt patterns
│   ├── code-generation/
│   ├── testing/
│   ├── refactoring/
│   ├── documentation/
│   └── security/
└── docs/
    ├── craft-framework/       # CRAFT methodology guide
    ├── workflow-tracker.md    # Progress tracking
    └── rubrics/               # Quality assessment
```

## CRAFT Framework

All prompts in this lab follow the CRAFT framework:

- **C**ontext: Tech stack, situation, dependencies
- **R**ole: Expert persona Copilot should adopt
- **A**ction: Specific numbered steps to perform
- **F**ormat: Output structure (types, patterns, documentation)
- **T**one/Constraints: Rules, security requirements, best practices

## Language-Specific Guidelines

### TypeScript/Angular
- Angular 17+ with standalone components
- Use `inject()` for dependency injection
- Return `Observable<T>` for async operations
- Use RxJS operators: `catchError`, `retry`, `shareReplay`
- Jest or Jasmine for testing
- Prefer signals for state management

### Java 17
- Use records for DTOs
- Use sealed interfaces for type-safe error handling
- Use `Optional<T>` for nullable returns
- Use `BigDecimal` for financial calculations
- Spring Boot 3 patterns when applicable
- JUnit 5 with AssertJ and Mockito for testing

## Quality Targets

Generated code should achieve:
- 9/10 quality rating on the CRAFT rubric
- Proper error handling for all failure modes
- Complete JSDoc/Javadoc documentation
- Edge case coverage in tests
- Security best practices (OWASP alignment)

## Workflow Modes

This lab uses multiple Copilot Chat modes:
- **Agent Mode**: Execute tasks, run commands, generate code
- **Planning Mode**: Review files, plan approach, outline solutions
- **Need Review Mode**: Compare output against solutions, rate quality
- **Testing Mode**: Run builds/tests, validate output
- **Summarizer/Hand-Off**: Document progress to workflow-tracker.md
