# Pattern Contribution Rubric

Use this rubric to validate your prompt pattern before contributing to the library.

## Overview

A pattern is ready for contribution when:
- ✅ 5 tests completed with documented results
- ✅ ≥80% success rate (4/5 or better)
- ✅ Follows `.prompt.md` format correctly
- ✅ Includes all required sections
- ✅ Passes quality checklist

## Test Methodology

### How to Test Your Pattern

1. **Select 5 different scenarios** that your pattern should handle
2. **Use the pattern** in Copilot Chat for each scenario
3. **Evaluate the output** using the scoring criteria below
4. **Record results** in the test log template
5. **Calculate success rate** and determine if pattern is ready

### Scenario Selection Guidelines

Choose scenarios that are:
- **Varied**: Different inputs, edge cases, domains
- **Realistic**: Things developers actually need
- **Challenging**: Include at least 1-2 edge cases

**Example for an "API Service" pattern:**
| Test | Scenario | Why This Tests the Pattern |
|------|----------|---------------------------|
| 1 | Simple GET endpoint | Baseline functionality |
| 2 | POST with request body | Data handling |
| 3 | Endpoint with authentication | Security integration |
| 4 | Endpoint needing retry logic | Error handling |
| 5 | Endpoint with caching | Performance feature |

## Scoring Criteria

### Per-Test Scoring (1-10 scale)

| Score | Classification | Criteria |
|-------|---------------|----------|
| **9-10** | ✅ Success | Output is production-ready with no modifications needed |
| **7-8** | ✅ Success | Output needs minor edits (<5 minutes of work) |
| **5-6** | ❌ Partial | Output needs significant edits (5-15 minutes) |
| **3-4** | ❌ Failure | Output has fundamental issues, mostly rewrite |
| **1-2** | ❌ Failure | Output is wrong approach entirely |

### What Counts as "Success" (Score ≥7)

The output must:
- [ ] **Compile/parse** without syntax errors
- [ ] **Implement core functionality** as requested
- [ ] **Follow the format** specified in the pattern
- [ ] **Handle basic edge cases** mentioned in the pattern
- [ ] **Be usable** with ≤5 minutes of minor edits

### What Counts as "Failure" (Score <7)

Any of these disqualify as success:
- Syntax errors that prevent compilation
- Missing core functionality
- Wrong framework/library used
- Security vulnerabilities introduced
- Completely wrong approach
- Would take >15 minutes to fix

## Test Log Template

Copy this template for each pattern you test:

```markdown
## Pattern Test Log: [Pattern Name]

**Pattern File:** `prompt-library/[category]/[name].prompt.md`
**Tester:** [Your Name]
**Date:** [Date]

### Test Results

| Test # | Scenario | Score | Success? | Notes |
|--------|----------|-------|----------|-------|
| 1 | [Description] | _/10 | ✅/❌ | [What worked/didn't] |
| 2 | [Description] | _/10 | ✅/❌ | [What worked/didn't] |
| 3 | [Description] | _/10 | ✅/❌ | [What worked/didn't] |
| 4 | [Description] | _/10 | ✅/❌ | [What worked/didn't] |
| 5 | [Description] | _/10 | ✅/❌ | [What worked/didn't] |

### Summary

- **Total Score:** _/50
- **Average Score:** _/10
- **Success Count:** _/5
- **Success Rate:** _%

### Ready for Contribution?

- [ ] ≥80% success rate (4/5 successes)
- [ ] Average score ≥7/10
- [ ] Pattern follows .prompt.md format
- [ ] All required sections present

**Result:** ✅ Ready / ❌ Needs refinement
```

## Pattern Format Checklist

Your `.prompt.md` file must include:

### Required YAML Frontmatter
```yaml
---
agent: 'agent'
description: '[One-line description of what pattern does]'
tools: ['search/codebase', 'edit/editFiles']  # At minimum
argument-hint: '[What user should provide]'
---
```

### Required Body Sections

| Section | Purpose | Required? |
|---------|---------|-----------|
| **Title** | Pattern name with category | ✅ Yes |
| **Category & Success Rate** | `**Category:** X \| **Success Rate:** Y%` | ✅ Yes |
| **One-line description** | What the pattern does | ✅ Yes |
| **Required Information** | What user must provide | ✅ Yes |
| **Main Content** | The actual prompt template | ✅ Yes |
| **Output Format** | Expected output structure | ✅ Yes |
| **Common Pitfalls** | What can go wrong | ✅ Yes |
| **Expected Output Quality** | Target score (e.g., 9/10) | ✅ Yes |

### Quality Checklist

Before submitting, verify:

**Structure:**
- [ ] YAML frontmatter is valid (no syntax errors)
- [ ] `agent: 'agent'` (not `mode: 'agent'`)
- [ ] `tools` array includes relevant tools
- [ ] `argument-hint` guides user clearly

**Content:**
- [ ] Required Information section lists all inputs needed
- [ ] Variables use `[variable-name]` format consistently
- [ ] Each variable has an example value
- [ ] Output format section shows expected structure
- [ ] At least 3 common pitfalls documented

**Quality:**
- [ ] Pattern tested 5 times with ≥80% success
- [ ] Success rate is documented accurately
- [ ] Examples are realistic and varied
- [ ] No typos or formatting issues

## Example: Completed Test Log

```markdown
## Pattern Test Log: Repository Query Method

**Pattern File:** `prompt-library/code-generation/repository-query-java.prompt.md`
**Tester:** Jane Developer
**Date:** 2024-01-15

### Test Results

| Test # | Scenario | Score | Success? | Notes |
|--------|----------|-------|----------|-------|
| 1 | Simple findById query | 9/10 | ✅ | Perfect output, ready to use |
| 2 | Complex join with filtering | 8/10 | ✅ | Minor tweak to sort order needed |
| 3 | Pagination with sorting | 9/10 | ✅ | Included Pageable correctly |
| 4 | Native query with projection | 7/10 | ✅ | Had to adjust column mapping |
| 5 | Query with optional parameters | 6/10 | ❌ | Missed null check on optional param |

### Summary

- **Total Score:** 39/50
- **Average Score:** 7.8/10
- **Success Count:** 4/5
- **Success Rate:** 80%

### Ready for Contribution?

- [x] ≥80% success rate (4/5 successes)
- [x] Average score ≥7/10
- [x] Pattern follows .prompt.md format
- [x] All required sections present

**Result:** ✅ Ready
```

## Improving a Failed Pattern

If your pattern doesn't reach 80% success:

### Analyze Failures
1. What CRAFT element was weakest in failed tests?
2. Were failures due to missing context, actions, or constraints?
3. Did the same issue cause multiple failures?

### Common Fixes

| Failure Type | Likely Cause | Fix |
|--------------|--------------|-----|
| Wrong framework/library | Missing Context | Add explicit framework/version |
| Missing features | Incomplete Action | Number and list all requirements |
| Wrong output structure | Vague Format | Show exact expected structure |
| Security issues | Missing Constraints | Add explicit security rules |
| Edge cases unhandled | Missing Action items | List edge cases explicitly |

### Re-test After Fixes
1. Fix the pattern based on failure analysis
2. Re-run the **failed tests** (not new ones)
3. If those pass, run 2 new tests to verify
4. Recalculate success rate

## Contribution Submission

Once your pattern passes:

1. **File location:** `prompt-library/[category]/[pattern-name].prompt.md`
   - Use kebab-case for filename
   - Add `-java` or `-python` suffix for language-specific patterns

2. **Update library README:** Add your pattern to the appropriate table in `prompt-library/README.md`

3. **Include test log:** Keep your test log for reference (can be in PR description)

4. **PR description template:**
```markdown
## New Pattern: [Pattern Name]

**Category:** [code-generation/testing/refactoring/etc.]
**Success Rate:** [X]% (tested [N] times)

### What it does
[One paragraph description]

### Test Summary
- Test 1: [scenario] → [result]
- Test 2: [scenario] → [result]
- Test 3: [scenario] → [result]
- Test 4: [scenario] → [result]
- Test 5: [scenario] → [result]

### Checklist
- [ ] Follows .prompt.md format
- [ ] ≥80% success rate
- [ ] Added to prompt-library/README.md
- [ ] Tested on both simple and edge cases
```

---

*A well-tested pattern helps everyone. Take the time to test thoroughly before contributing.*
