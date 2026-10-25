package com.codepilot.codepilot_backend.service;

import com.codepilot.codepilot_backend.analyzer.JavaRuleAnalyzer;
import com.codepilot.codepilot_backend.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import java.time.Instant;
import java.util.List;

@Service
public class CodeReviewService {
    private final JavaRuleAnalyzer analyzer;
    private final OllamaClient ollama;
    public CodeReviewService(JavaRuleAnalyzer analyzer, OllamaClient ollama) { this.analyzer=analyzer; this.ollama=ollama; }

    public CodeReviewResponse review(CodeReviewRequest request) {
        List<CodeReviewFinding> findings = analyzer.analyze(request.getCode());
        String explanation = null;
        String status = "UNAVAILABLE";
        try {
            explanation = ollama.explain(request.getCode(), findings);
            if (explanation != null) status = "CONNECTED";
        } catch (RestClientException | IllegalStateException ignored) {
            // The deterministic review remains useful when local Ollama is stopped.
        }
        long high=findings.stream().filter(f->"HIGH".equals(f.severity())).count();
        long medium=findings.stream().filter(f->"MEDIUM".equals(f.severity())).count();
        long low=findings.size()-high-medium;
        String summary=findings.size()+" finding(s): "+high+" high, "+medium+" medium, "+low+" low.";
        return new CodeReviewResponse(request.getLanguage(), Instant.now(), summary, findings, explanation, status);
    }
}
