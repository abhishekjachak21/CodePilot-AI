package com.codepilot.codepilot_backend.dto;
public record CodeReviewFinding(String id,String category,String severity,String title,int line,String problematicCode,String explanation,String suggestedFix,String correctedCode,String source) {}
