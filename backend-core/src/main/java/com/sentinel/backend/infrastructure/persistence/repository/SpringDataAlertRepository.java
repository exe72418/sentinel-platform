package com.sentinel.backend.infrastructure.persistence.repository;

import com.sentinel.backend.infrastructure.persistence.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAlertRepository extends JpaRepository<AlertEntity, Long> {
}
