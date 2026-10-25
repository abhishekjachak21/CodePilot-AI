# Product Brief: CodePilot-AI

**Status:** MVP baseline for manager demonstration

## Executive Summary
CodePilot-AI is a browser-based assistant that helps Java developers and senior reviewers inspect a code snippet and understand selected code-quality risks. It combines deterministic checks for a small set of known patterns with optional local language-model explanations, then presents categorized findings with severity and remediation guidance.

The first release is deliberately narrow: a developer pastes Java code, requests a review and receives a report. Local Ollama processing avoids sending source code to a hosted AI provider in this MVP. The product is an explainable review assistant, not a replacement for a compiler, mature static-analysis platform or security audit.

## The Problem
Manual code review takes time and feedback can vary. Developers need explanations and practical remediation guidance, not only warning labels.

## The Solution
A no-login web application for Java snippets. The backend runs selected deterministic checks and may call a local Ollama model to explain findings. Results include category, severity, source line, problematic snippet, explanation and suggested improvement. Developers validate and apply changes themselves.

## What Makes This Different
- Combines predictable supported-pattern checks with AI-assisted explanations.
- Local model execution is the MVP default.
- Results include remediation guidance while keeping developers in control.
- Clear path from snippet review to future repository-level analysis.

No novel detection engine or complete code coverage is claimed.

## Who This Serves
Primary: Java developers seeking fast, understandable feedback.
Secondary: Senior developers and reviewers seeking a consistent first-pass checklist.

## Success Criteria
- Submit a non-empty Java snippet from the browser.
- Receive structured findings with category, severity and line reference.
- Display explanation and suggested improvement.
- Show AI-connected or rules-only status.
- Complete a demo without login, database or cloud AI credentials.

## Scope
In: React UI, Spring Boot API, selected heuristic checks (credential-like hardcoding, console output, empty catch blocks, possible string concatenation in loops), optional local Ollama explanation.
Out: GitHub repository scanning, multi-file analysis, login, review history, automatic edits, complete AST/SAST analysis and cloud AI in the current MVP.

## Vision
Evolve toward repository-aware code intelligence, pull-request feedback, configurable rules and selectable AI providers after validating the snippet-review experience and strengthening analysis quality.
