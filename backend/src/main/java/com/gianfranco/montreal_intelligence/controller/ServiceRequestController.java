package com.gianfranco.montreal_intelligence.controller;

import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import com.gianfranco.montreal_intelligence.repository.ServiceRequestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceRequestController {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @GetMapping("/api/requests")
    public List<ServiceRequest> getRequests() {
        return serviceRequestRepository.findAll();
    }
}