# CodePilot-AI

Intelligent Java Code Review Assistant — a BMAD-planned MVP.

## What it does
- Browser-based React UI for submitting a Java snippet.
- Spring Boot REST API runs selected deterministic checks for credential-like hardcoding, console output, empty catch blocks and possible string concatenation in loops.
- Optional local Ollama + Qwen explanation.
- Results include category, severity, source line, explanation and suggested improvement.
- If Ollama is not running, deterministic findings still return in rules-only mode.

This is a focused demonstration, not a compiler, complete static analyzer or production SAST product. Pattern rules can miss issues or produce false positives. Verify all recommendations.

## Architecture
Browser (React/Vite :5173) -> Spring Boot API (:8080) -> JavaRuleAnalyzer + optional OllamaClient -> Ollama (:11434) -> Qwen2.5-Coder 7B.

## Prerequisites
Java 21, Node.js 20+, and Ollama. Download the model with: ollama pull qwen2.5-coder:7b

## Run
Terminal 1: ensure Ollama is running. If needed: ollama serve
Terminal 2:
    cd codepilot-backend
    ./gradlew bootRun
Health: http://localhost:8080/actuator/health
Terminal 3:
    cd frontend
    npm install
    npm run dev
Open http://localhost:5173

## API
POST http://localhost:8080/api/code-review
JSON body: {"language":"java","code":"public class Demo { ... }"}

## Configuration
OLLAMA_BASE_URL defaults to http://localhost:11434
OLLAMA_MODEL defaults to qwen2.5-coder:7b

## BMAD artifacts
See bmad-output/planning-artifacts for the product brief, MVP requirements and architecture.

## Limitations
No authentication, history, database, GitHub scanning or automatic code modification in MVP. Heuristic findings and AI explanations need developer verification. Do not paste real credentials or sensitive proprietary source during a demo.
