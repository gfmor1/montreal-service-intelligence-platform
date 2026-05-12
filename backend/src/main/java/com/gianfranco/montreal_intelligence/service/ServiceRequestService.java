package com.gianfranco.montreal_intelligence.service;

import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import com.gianfranco.montreal_intelligence.repository.ServiceRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public Page<ServiceRequest> getRequests(
            String borough,
            String status,
            String category,
            Pageable pageable
    ) {
        return serviceRequestRepository.findWithFilters(
                cleanFilter(borough),
                cleanFilter(status),
                cleanFilter(category),
                pageable
        );
    }

    public Optional<ServiceRequest> getRequestById(Long id) {
        return serviceRequestRepository.findById(id);
    }

    private String cleanFilter(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value;
    }
}