package com.gianfranco.montreal_intelligence.repository;

import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    boolean existsByExternalId(String externalId);
}