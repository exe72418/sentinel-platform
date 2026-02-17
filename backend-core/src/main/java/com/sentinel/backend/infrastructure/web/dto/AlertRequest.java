package com.sentinel.backend.infrastructure.web.dto;

import java.time.LocalDateTime;

public record AlertRequest(
    String podName,
    String errorMessage,
    String severity,
    String status
) {}
