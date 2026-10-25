package com.codepilot.codepilot_backend.service;

import com.codepilot.codepilot_backend.dto.CodeReviewFinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OllamaClient {
    private final RestClient client;
    private final String model;

    public OllamaClient(@Value("${codepilot.ollama.base-url:http://localhost:11434}") String url,
                        @Value("${codepilot.ollama.model:qwen2.5-coder:7b}") String model) {
        this.client = RestClient.builder().baseUrl(url).build();
        this.model = model;
    }

    public String explain(String source, List<CodeReviewFinding> findings) {
        String details = findings.stream()
                .map(f -> "- " + f.category() + " / " + f.severity() + ": " + f.title() +
                        " (line " + f.line() + ") — " + f.explanation())
                .collect(Collectors.joining("\n"));
        String prompt = "Explain these Java review findings in plain language. Do not invent findings. " +
                "State that heuristic results need verification. Keep under 150 words.\nFindings:\n" +
                (details.isBlank() ? "No supported rule patterns detected." : details) + "\nSource:\n" + source;

        Map<?, ?> response = client.post().uri("/api/chat").body(Map.of(
                "model", model,
                "stream", false,
                "messages", List.of(
                        Map.of("role", "system", "content", "Be concise and precise."),
                        Map.of("role", "user", "content", prompt)
                )
        )).retrieve().body(Map.class);

        if (response == null || !(response.get("message") instanceof Map<?, ?> message)) return null;
        Object content = message.get("content");
        return content instanceof String text && !text.isBlank() ? text : null;
    }
}
