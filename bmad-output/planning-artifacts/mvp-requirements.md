# CodePilot-AI — MVP Requirements

## Decisions
- React browser UI; Spring Boot REST backend; Java snippet input.
- Primary users: Java developers. Secondary: senior developers/reviewers.
- No login, persistence or review history in MVP.
- Categories: code smells, potential bugs, security and performance.
- Findings: title, severity, line, snippet, explanation, suggested fix and copyable example improvement.
- Hybrid analysis: deterministic rules plus optional local Ollama/Qwen explanation.
- GitHub repository scanning is a future phase.

## User stories and acceptance criteria

### US-01 Submit snippet
As a Java developer, I want to paste Java code and request a review.
- UI submits language and code as JSON to POST /api/code-review.
- Empty source is rejected.
- Java is the only supported language in this MVP.

### US-02 Detect supported patterns
As a developer, I want selected patterns highlighted.
- Current heuristic rules cover credential-like hardcoding, System.out output, empty catch blocks and possible loop string concatenation.
- Findings contain category, severity, line and rule ID.
- No findings must not be interpreted as proof that code is defect-free.

### US-03 Understand and improve
As a developer, I want explanations and suggested improvements.
- Finding displays its problematic line, explanation, suggested fix and example improvement.
- Original source is never modified automatically.

### US-04 Local AI explanation
As a developer, I want local model context without cloud transmission.
- Backend calls Ollama /api/chat with configured model.
- If Ollama is down, rule findings still return.
- UI indicates connected or rules-only mode.

### US-05 Inspect report
As developer/reviewer, I want severity and categories visible.
- Summary includes finding count.
- UI presents categorized cards, line numbers and copyable example improvements.

## Non-functional requirements
- Source is not persisted by this MVP.
- Request is limited to 50,000 characters by the request DTO.
- Local AI is the default; no cloud endpoint is called.
- Findings are heuristic and need human verification.

## Future
GitHub repository and pull-request scans, multi-file context, accounts, history, AST-backed rules, quality evaluation and alternate model providers.
