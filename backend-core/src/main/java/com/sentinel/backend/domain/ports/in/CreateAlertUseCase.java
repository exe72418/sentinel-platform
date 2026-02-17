package com.sentinel.backend.domain.ports.in;

import com.sentinel.backend.domain.model.InfrastructureAlert;

public interface CreateAlertUseCase {
    InfrastructureAlert createAlert(InfrastructureAlert alert);
}
