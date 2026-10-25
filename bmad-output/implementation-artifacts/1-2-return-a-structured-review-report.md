---
story: "1.2"
title: "Return a structured review report"
status: ready-for-dev
---

# Story 1.2: Return a structured review report

## User story
As a developer, I want a predictable response containing summary and finding details, so that the UI can render results consistently.

## Acceptance criteria
1. Valid requests return language, reviewedAt, summary, findings, aiExplanation and aiStatus.
2. Findings expose id, category, severity, title, line, problematicCode, explanation, suggestedFix, correctedCode and source.
3. Submitted source is not persisted.

## Verification plan
Verify response contract against frontend and empty-findings behavior.

## Status note
Source code for an initial slice exists in the repository. This story remains ready-for-dev until its acceptance criteria are verified locally.
