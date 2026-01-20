# Prompt Quality Rubric

Use this rubric to evaluate prompt effectiveness.

## Scoring Guide

### Clarity (1-10)

| Score | Description |
|-------|-------------|
| 1-3 | Ambiguous, could be interpreted multiple ways |
| 4-6 | Mostly clear, some room for misinterpretation |
| 7-8 | Clear intent, minor ambiguities |
| 9-10 | Crystal clear, no room for misinterpretation |

### Completeness (1-10)

| Score | Description |
|-------|-------------|
| 1-3 | Missing most context, requirements unclear |
| 4-6 | Some context provided, gaps in requirements |
| 7-8 | Good context, most requirements specified |
| 9-10 | Full context, all requirements explicit |

### Specificity (1-10)

| Score | Description |
|-------|-------------|
| 1-3 | Very vague, no concrete details |
| 4-6 | Some specifics, many assumptions needed |
| 7-8 | Specific requirements, few assumptions |
| 9-10 | Highly specific, no assumptions needed |

### Output Quality (1-10)

| Score | Description |
|-------|-------------|
| 1-3 | Output needs complete rewrite |
| 4-6 | Output needs significant editing |
| 7-8 | Output needs minor adjustments |
| 9-10 | Output is ready to use as-is |

### Consistency (1-10)

| Score | Description |
|-------|-------------|
| 1-3 | Different outputs each time |
| 4-6 | Similar outputs, some variation |
| 7-8 | Consistent outputs, minor differences |
| 9-10 | Identical structure/quality each time |

## Overall Score Calculation

```
Overall = (Clarity + Completeness + Specificity + Output + Consistency) / 5
```

## Score Interpretation

| Overall | Interpretation | Action |
|---------|---------------|--------|
| 1-3 | Poor | Rewrite from scratch |
| 4-5 | Needs Work | Apply CRAFT framework |
| 6-7 | Good | Refine specifics |
| 8-9 | Very Good | Minor polish |
| 10 | Excellent | Ready for library |

## Self-Assessment Template

```markdown
## Prompt Evaluation

**Prompt:**
[Your prompt here]

**Scores:**
- Clarity: ___ /10
- Completeness: ___ /10
- Specificity: ___ /10
- Output Quality: ___ /10
- Consistency: ___ /10
- **Overall: ___ /10**

**What worked well:**
[Notes]

**What could improve:**
[Notes]

**Revised prompt:**
[If needed]
```
