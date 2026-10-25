package com.codepilot.codepilot_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CodeReviewRequest {

    @NotBlank(message = "Source code cannot be empty")
    @Size(max = 50000, message = "Source code exceeds the maximum allowed size")
    private String code;

    @NotBlank(message = "Language is required")
    private String language;

    public CodeReviewRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}