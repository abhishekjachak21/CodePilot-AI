---
story: "1.1"
title: "Submit a Java snippet"
status: ready-for-dev
---

# Story 1.1: Submit a Java snippet

## User story
As a Java developer, I want to paste Java source and submit it for review, so that I can request feedback without repository setup.

## Acceptance criteria
1. Non-empty Java source can be submitted from the browser to POST /api/code-review.
2. The request contains language and source code as JSON.
3. Empty source and unsupported language return clear validation errors.
4. The UI displays loading and request-error states.

## Verification plan
Verify backend build, API validation and browser submission locally.

## Status note
Source code for an initial slice exists in the repository. This story remains ready-for-dev until its acceptance criteria are verified locally.
