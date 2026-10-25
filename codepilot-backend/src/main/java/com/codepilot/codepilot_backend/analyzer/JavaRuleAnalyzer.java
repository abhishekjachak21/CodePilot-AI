package com.codepilot.codepilot_backend.analyzer;

import com.codepilot.codepilot_backend.dto.CodeReviewFinding;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class JavaRuleAnalyzer {
    private static final Pattern SECRET = Pattern.compile("(?i).*(password|passwd|secret|api[_-]?key)\\s*=\\s*[\"'][^\"']+[\"'].*");

    public List<CodeReviewFinding> analyze(String code) {
        List<CodeReviewFinding> findings = new ArrayList<>();
        String[] lines = code.split("\\R", -1);
        boolean inLoop = false;
        for (int i = 0; i < lines.length; i++) {
            String raw = lines[i];
            String line = raw.trim();
            int n = i + 1;
            if (line.matches(".*\\b(for|while)\\s*\\(.*")) inLoop = true;

            if (SECRET.matcher(raw).matches()) findings.add(new CodeReviewFinding(
                "JAVA-SEC-001","Security","HIGH","Possible hardcoded secret",n,line,
                "A credential-like value appears to be assigned directly in source code. This can expose secrets through source control or builds.",
                "Load the value from a secret manager or environment variable and rotate any exposed credential.",
                "String password = System.getenv(\"APP_PASSWORD\");","RULE"));

            if (line.contains("System.out.println")) findings.add(new CodeReviewFinding(
                "JAVA-STYLE-001","Code Smell","LOW","Console output in application code",n,line,
                "Direct console output is difficult to control and correlate in a server application.",
                "Use a logging framework and an appropriate log level.",
                "private static final Logger log = LoggerFactory.getLogger(YourClass.class);\nlog.info(\"Meaningful message\");","RULE"));

            if (isEmptyCatch(lines, i, line)) findings.add(new CodeReviewFinding(
                "JAVA-BUG-001","Potential Bug","MEDIUM","Empty catch block",n,line,
                "The exception is swallowed, which can hide failures and leave the application in an unexpected state.",
                "Handle the exception, log it with context, or rethrow it.",
                "catch (Exception ex) {\n    log.error(\"Operation failed\", ex);\n    throw ex;\n}","RULE"));

            if (inLoop && (line.contains("+=") || line.matches(".*\\w+\\s*=.*\\+.*"))) {
                findings.add(new CodeReviewFinding(
                    "JAVA-PERF-001","Performance","LOW","Possible string concatenation in loop",n,line,
                    "Repeated string concatenation inside a loop may create temporary String objects. Confirm this is a hot path.",
                    "Consider StringBuilder and append() for repeated accumulation.",
                    "StringBuilder result = new StringBuilder();\nfor (...) {\n    result.append(value);\n}\nString text = result.toString();","RULE"));
                inLoop = false;
            }
            if (line.equals("}") && inLoop) inLoop = false;
        }
        return findings;
    }

    private boolean isEmptyCatch(String[] lines, int index, String line) {
        if (!line.contains("catch")) return false;
        int openingBrace = line.indexOf('{');
        if (openingBrace < 0) return false;
        String remainder = line.substring(openingBrace + 1).trim();
        if (remainder.equals("}")) return true;
        if (!remainder.isEmpty()) return false;
        for (int i = index + 1; i < lines.length; i++) {
            String next = lines[i].trim();
            if (next.isEmpty()) continue;
            return next.equals("}");
        }
        return false;
    }
}
