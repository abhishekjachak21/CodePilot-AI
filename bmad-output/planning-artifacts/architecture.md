# CodePilot-AI — MVP Architecture

## System flow
React + Vite (5173) -> POST /api/code-review -> Spring Boot controller -> CodeReviewService -> JavaRuleAnalyzer (deterministic checks) + OllamaClient (optional local explanation) -> structured JSON -> React results.

## Components
- React: source editor, submit/loading/error state, findings and copy controls.
- CodeReviewController: HTTP boundary and request validation.
- CodeReviewService: orchestration and fallback behavior.
- JavaRuleAnalyzer: narrow pattern-based checks with line numbers.
- OllamaClient: invokes local Ollama /api/chat; failure does not block deterministic results.
- DTOs: request and stable response contract.

## Decisions
1. Modular monolith: one Spring Boot service is simpler to build, run and debug for this single MVP workflow. Microservices add needless operational cost here.
2. Hybrid analysis: deterministic rules are the source of supported findings; the LLM explains them, not acts as proof.
3. Local AI: Ollama at localhost:11434 and qwen2.5-coder:7b. No cloud AI API is called.
4. Stateless MVP: no DB, login or review history because those do not validate core snippet review.
5. React/Vite: browser demo with a development proxy from /api to port 8080.

## API
POST /api/code-review
Request: {"language":"java","code":"..."}
Response: language, reviewedAt, summary, findings, aiExplanation and aiStatus. Finding: id, category, severity, title, line, problematicCode, explanation, suggestedFix, correctedCode and source.

## Configuration
Backend :8080; Vite :5173; OLLAMA_BASE_URL defaults to http://localhost:11434; OLLAMA_MODEL defaults to qwen2.5-coder:7b.

## Risks and limitations
Current checks use text patterns, not AST or bytecode analysis. False positives and missed issues are expected. The loop performance rule is approximate. AI explanations can be inaccurate. Production readiness requires parser-backed rules, tests and evaluation data, security controls, API timeouts/rate limits, accessibility checks and privacy review.
