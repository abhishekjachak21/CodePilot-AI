package com.codepilot.codepilot_backend.controller;

import com.codepilot.codepilot_backend.dto.CodeReviewRequest;
import com.codepilot.codepilot_backend.dto.CodeReviewResponse;
import com.codepilot.codepilot_backend.service.CodeReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code-review")
@CrossOrigin(origins="http://localhost:5173")
public class CodeReviewController {
    private final CodeReviewService service;
    public CodeReviewController(CodeReviewService service) { this.service=service; }

    @PostMapping
    public CodeReviewResponse review(@Valid @RequestBody CodeReviewRequest request) {
        if (!"java".equalsIgnoreCase(request.getLanguage())) throw new IllegalArgumentException("The MVP currently supports Java snippets only.");
        return service.review(request);
    }
}
