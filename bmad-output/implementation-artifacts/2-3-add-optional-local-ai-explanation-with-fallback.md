---
story: "2.3"
title: "Add optional local AI explanation with fallback"
status: ready-for-dev
---

# Story 2.3: Add optional local AI explanation with fallback

## User story
As a developer, I want a local model to add context to the review, so that findings are easier to understand without a hosted AI service.

## Acceptance criteria
1. Backend calls configured Ollama /api/chat endpoint.
2. AI explanation supplements but does not replace deterministic findings.
3. Ollama failure does not fail the review request.
4. API/UI distinguish AI-connected from rules-only mode.
5. Prompt instructs model not to invent findings.

## Verification plan
Test Qwen connected path, stop Ollama and test fallback, verify response parsing.

## Status note
Source code for an initial slice exists in the repository. This story remains ready-for-dev until its acceptance criteria are verified locally.
