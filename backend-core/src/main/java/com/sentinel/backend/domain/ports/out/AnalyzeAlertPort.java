package com.sentinel.backend.domain.ports.out;

import com.sentinel.backend.domain.model.InfrastructureAlert;

public interface AnalyzeAlertPort {
    InfrastructureAlert analyze(InfrastructureAlert alert);
}
