package com.sentinel.backend.domain.model;

import java.time.LocalDateTime;

public record InfrastructureAlert(
    Long id,
    String podName,
    String errorMessage,
    String severity,
    String status,
    LocalDateTime createdAt,
    String aiAnalysis,
    String suggestedAction
) {}
