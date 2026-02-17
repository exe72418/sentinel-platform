package com.sentinel.backend.infrastructure.web.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.out.AnalyzeAlertPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiBrainAdapter implements AnalyzeAlertPort {

    private final RestTemplate restTemplate;
    // In Docker network, service name 'ai-brain' resolves to container IP. Port 8000 is internal container port.
    private static final String AI_SERVICE_URL = "http://ai-brain:8000/analyze";

    @Override
    public InfrastructureAlert analyze(InfrastructureAlert alert) {
        try {
            log.info("Requesting analysis from AI Brain for alert: {}", alert.id());
            AiAnalysisRequest request = new AiAnalysisRequest(
                alert.id(),
                alert.podName(),
                alert.errorMessage(),
                alert.severity(),
                alert.status()
            );

            AiAnalysisResponse response = restTemplate.postForObject(AI_SERVICE_URL, request, AiAnalysisResponse.class);

            if (response != null) {
                log.info("Received analysis: {}", response);
                return new InfrastructureAlert(
                    alert.id(),
                    alert.podName(),
                    alert.errorMessage(),
                    alert.severity(),
                    alert.status(),
                    alert.createdAt(),
                    response.analysis(),
                    response.suggestedAction()
                );
            }
        } catch (Exception e) {
            log.error("Failed to get analysis from AI Brain", e);
            // Fallback: Return alert with error note in analysis, or leave null
            return new InfrastructureAlert(
                alert.id(),
                alert.podName(),
                alert.errorMessage(),
                alert.severity(),
                alert.status(),
                alert.createdAt(),
                "AI Service Unavailable: " + e.getMessage(),
                "Manual Investigation Required"
            );
        }
        return alert;
    }

    // Inner DTOs for JSON mapping
    private record AiAnalysisRequest(
        Long id,
        String podName,
        String errorMessage,
        String severity,
        String status
    ) {}

    private record AiAnalysisResponse(
        @JsonProperty("original_alert_id") Long originalAlertId,
        String analysis,
        @JsonProperty("suggested_action") String suggestedAction
    ) {}
}
