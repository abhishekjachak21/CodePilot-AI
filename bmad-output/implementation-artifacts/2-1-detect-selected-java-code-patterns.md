---
story: "2.1"
title: "Detect selected Java code patterns"
status: ready-for-dev
---

# Story 2.1: Detect selected Java code patterns

## User story
As a Java developer, I want a first-pass check for selected code patterns, so that common review concerns are surfaced consistently.

## Acceptance criteria
1. Credential-like hardcoded assignment produces a Security finding.
2. System.out.println produces a Code Smell finding.
3. Empty catch produces a Potential Bug finding.
4. Selected string concatenation in loops produces a Performance candidate.
5. Findings include stable ID, severity, line and matched source.
6. Results are explicitly heuristic, not complete static analysis.

## Verification plan
Add positive/negative unit tests for every rule and verify line numbers.

## Status note
Source code for an initial slice exists in the repository. This story remains ready-for-dev until its acceptance criteria are verified locally.
