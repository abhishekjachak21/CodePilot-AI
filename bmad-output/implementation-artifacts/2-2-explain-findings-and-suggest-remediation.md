---
story: "2.2"
title: "Explain findings and suggest remediation"
status: ready-for-dev
---

# Story 2.2: Explain findings and suggest remediation

## User story
As a developer, I want each finding explained with a practical remediation, so that I can learn why the code may be problematic.

## Acceptance criteria
1. Each finding contains explanation, suggested fix and illustrative corrected code.
2. Suggestions do not mutate submitted source.
3. Explanations are framed as guidance, not certainty.
4. Copying a fix does not automatically apply it.

## Verification plan
Verify each finding renders remediation content and no automatic source writes exist.

## Status note
Source code for an initial slice exists in the repository. This story remains ready-for-dev until its acceptance criteria are verified locally.
