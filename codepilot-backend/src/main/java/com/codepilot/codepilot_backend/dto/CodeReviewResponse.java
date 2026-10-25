package com.codepilot.codepilot_backend.dto;
import java.time.Instant;
import java.util.List;
public record CodeReviewResponse(String language,Instant reviewedAt,String summary,List<CodeReviewFinding> findings,String aiExplanation,String aiStatus) {}
