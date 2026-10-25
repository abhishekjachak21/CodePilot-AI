---
title: "CodePilot-AI Product Requirements Document"
status: final
created: 2026-10-25
updated: 2026-10-25
---

# Product Requirements Document: CodePilot-AI

## Product intent
CodePilot-AI helps Java developers perform a first-pass review of a code snippet and understand selected maintainability, correctness, security and performance risks. It combines deterministic heuristic findings with optional local-AI explanations. It is an internal demonstration MVP, not a production security gate.

## Users and outcome
Primary user: Java developer. Secondary user: senior developer/reviewer. A developer pastes Java code, requests a review, inspects line-level findings and decides independently whether to apply a suggested change.

## Scope
In scope: single Java snippet input; structured REST review; selected checks for credential-like hardcoded assignments, System.out.println, empty catch blocks and possible string concatenation in loops; category/severity/line details; explanation and illustrative fix; optional local Ollama explanation; deterministic fallback; clear limitations.
Out of scope: repository scanning, multi-file analysis, Git pull-request integration, complete AST/SAST analysis, authentication, database/history, cloud AI, automatic edits or execution of submitted code.

## Functional requirements

### Feature A — Snippet submission
FR-001 The system shall allow a developer to enter or paste Java source in a browser.
FR-002 The system shall submit source and language to the review API.
FR-003 The system shall reject empty source and unsupported languages with a clear error.
FR-004 The API shall return language, review timestamp, summary, findings, AI explanation and AI availability status.

### Feature B — Supported analysis
FR-005 The analyzer shall identify selected credential-like hardcoded assignments as possible security concerns.
FR-006 The analyzer shall identify System.out.println as a code-smell candidate.
FR-007 The analyzer shall identify empty catch blocks as potential bug candidates.
FR-008 The analyzer shall identify selected string concatenation patterns inside loops as possible performance concerns.
FR-009 Each finding shall include rule ID, category, indicative severity, title, line and matched source.
FR-010 An empty findings list shall not be presented as proof that code is defect-free.

### Feature C — Explain and remediate
FR-011 Each finding shall include a plain-language explanation and suggested remediation.
FR-012 A finding may include illustrative corrected code.
FR-013 The system shall never modify submitted source automatically.
FR-014 The UI shall allow a developer to copy an illustrative fix.

### Feature D — Local AI
FR-015 The backend shall optionally request a concise explanation from the configured local Ollama model.
FR-016 AI explanations shall be grounded in deterministic findings and shall not be treated as proof.
FR-017 If Ollama is unavailable, deterministic findings shall still be returned.
FR-018 The UI shall distinguish AI-connected from rules-only results.

### Feature E — Review experience
FR-019 The UI shall present a summary and readable finding cards.
FR-020 The UI shall provide loading, empty, success and error states.
FR-021 The UI shall disclose heuristic limitations and require human verification.

## Non-functional requirements
NFR-001 The MVP shall not persist submitted source or review history.
NFR-002 Local Ollama shall be the default inference endpoint; no hosted provider is required.
NFR-003 AI unavailability shall not block deterministic results.
NFR-004 Findings shall be described as heuristic candidates, not guaranteed defects.
NFR-005 A developer shall be able to use the MVP without login or setup beyond opening the application.
NFR-006 API boundary, orchestration, analyzer and UI shall remain separable.
NFR-007 The MVP shall not execute submitted code or automatically apply edits.
NFR-008 The API shall enforce an input-size/snippet-length limit.

## Success measures and counter-metrics
Demo success: sample Java code produces structured browser results. Resilience success: deterministic results remain available with Ollama stopped. Comprehension success: each finding includes line, reason and remediation. Counter-metric: never claim complete rule coverage; false positives and missed patterns must be evaluated before expanding scope.

## Assumptions and risks
[ASSUMPTION] Internal demo only. Java is the sole supported language. Heuristic rules may produce false positives and false negatives. LLM explanations can be inaccurate. The loop-concatenation check is approximate. Local runtime, input bounds and error handling must be verified before demo sign-off.

## Release acceptance
Backend and frontend run locally; a sample snippet returns findings; connected and rules-only paths work; UI communicates limitations; build and smoke tests pass. A Git commit alone does not constitute acceptance.
