---
status: draft
stepsCompleted: [1, 2]
inputDocuments:
  - bmad-output/planning-artifacts/prds/prd-CodePilot-AI-2026-10-25/prd.md
  - bmad-output/planning-artifacts/architecture.md
  - bmad-output/planning-artifacts/product-brief.md
---

# CodePilot-AI - Epic Breakdown

## Overview
Two user-value epics decompose the single-snippet Java review MVP. The repository contains an initial implementation; stories remain subject to local build and acceptance verification.

## Requirements Inventory

### Functional Requirements
FR-001 browser snippet entry; FR-002 submit to API; FR-003 validate source and language; FR-004 structured response; FR-005 hardcoded-secret heuristic; FR-006 console-output heuristic; FR-007 empty-catch heuristic; FR-008 loop-concatenation heuristic; FR-009 structured finding metadata; FR-010 no false clean bill of health; FR-011 explanation; FR-012 illustrative fix; FR-013 no automatic edits; FR-014 copy fix; FR-015 optional Ollama explanation; FR-016 ground AI in findings; FR-017 rules-only fallback; FR-018 AI availability status; FR-019 readable result cards; FR-020 loading/error/success states; FR-021 limitation disclosure.

### NonFunctional Requirements
NFR-001 no source persistence; NFR-002 local inference by default; NFR-003 deterministic fallback; NFR-004 transparent heuristic language; NFR-005 no-login workflow; NFR-006 separable components; NFR-007 no automatic execution or edits; NFR-008 bounded input size.

### Additional Requirements
- Existing Spring Boot / Gradle backend and React / Vite frontend.
- Java 21 backend runtime target.
- POST /api/code-review.
- Local Ollama /api/chat endpoint with configurable base URL and model.
- Modular monolith; no microservices or database in MVP.
- Preserve submitted source and return suggestions only.
- Local verification and tests required before marking stories done.

### UX Design Requirements
No separate BMAD UX design contract exists. UI behavior is captured by PRD FR-019 to FR-021 and the browser-story acceptance criteria.

### FR Coverage Map
Epic 1: FR-001–FR-004, FR-019–FR-020.
Epic 2: FR-005–FR-018, FR-021.
NFR-001–NFR-008 apply across both epics.

## Epic List
1. Snippet Review Experience — submit Java source and receive a structured review in the browser.
2. Explainable Hybrid Analysis — detect a narrow set of patterns and provide grounded explanations with local-AI fallback.

## Epic 1: Snippet Review Experience
A Java developer can enter a snippet, submit it through a stable API and inspect a clear report.

### Story 1.1: Submit a Java snippet
As a Java developer, I want to paste Java source and submit it for review, so that I can request feedback without repository setup.
**Acceptance Criteria:**
**Given** the review page is open
**When** I enter non-empty Java source and select Review
**Then** the browser sends language and code to POST /api/code-review
**And** the UI displays a loading state.
**Given** source is empty or language unsupported
**When** I submit
**Then** the system returns a clear validation error and the UI displays it without crashing.

### Story 1.2: Return a structured review report
As a developer, I want a predictable response, so that the UI can render results consistently.
**Acceptance Criteria:**
**Given** a valid Java review request
**When** analysis completes
**Then** the response includes language, reviewedAt, summary, findings, aiExplanation and aiStatus
**And** each finding includes id, category, severity, title, line, problematicCode, explanation, suggestedFix, correctedCode and source
**And** the original source is not persisted.

### Story 1.3: Inspect findings in the browser
As a Java developer, I want findings presented with severity and remediation, so that I can understand and act on feedback.
**Acceptance Criteria:**
**Given** one or more findings are returned
**When** the result is rendered
**Then** each finding shows category, severity, title, source line, explanation and suggested fix
**And** illustrative corrected code can be copied
**And** empty results are accompanied by a warning that no findings does not prove defect-free code.

## Epic 2: Explainable Hybrid Analysis
The review engine detects a deliberately narrow set of Java patterns and optionally uses a local model to explain those findings. The developer validates every recommendation.

### Story 2.1: Detect selected Java code patterns
As a Java developer, I want a first-pass check for selected code patterns, so that common review concerns are surfaced consistently.
**Acceptance Criteria:**
**Given** a credential-like hardcoded assignment
**When** the analyzer runs
**Then** it emits a Security finding with rule ID, severity and line
**Given** source containing System.out.println, an empty catch or selected loop string concatenation
**When** the analyzer runs
**Then** it emits the corresponding Code Smell, Potential Bug or Performance candidate
**And** results are explicitly heuristic, not complete static analysis.

### Story 2.2: Explain findings and suggest remediation
As a developer, I want each finding explained with a practical remediation, so that I can learn why the code may be problematic.
**Acceptance Criteria:**
**Given** a deterministic finding
**When** the report is built
**Then** it includes explanation, suggested fix and illustrative corrected code
**And** no source changes are applied automatically
**And** guidance does not claim certainty beyond the heuristic.

### Story 2.3: Add optional local AI explanation with fallback
As a developer, I want a local model to add context to the review, so that findings are easier to understand without a hosted AI service.
**Acceptance Criteria:**
**Given** Ollama and the configured model are reachable
**When** a review is requested
**Then** the backend requests a concise explanation and reports AI connected
**Given** Ollama is unavailable or returns an unusable response
**When** a review is requested
**Then** deterministic findings are still returned and status is rules-only
**And** AI must not invent additional authoritative findings.

## Delivery notes
An initial code slice already exists. Do not mark stories done until local acceptance criteria and build verification pass.
