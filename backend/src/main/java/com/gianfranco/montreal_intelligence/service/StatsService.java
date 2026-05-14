package com.gianfranco.montreal_intelligence.service;

import com.gianfranco.montreal_intelligence.dto.StatsSummary;
import com.gianfranco.montreal_intelligence.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private final ServiceRequestRepository serviceRequestRepository;

    public StatsService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<StatsSummary> getRequestsByBorough() {
        return serviceRequestRepository.countByBorough();
    }

    public List<StatsSummary> getRequestsByCategory() {
        return serviceRequestRepository.countByCategory();
    }

    public List<StatsSummary> getRequestsByStatus() {
        return serviceRequestRepository.countByStatus();
    }

    public List<StatsSummary> getMonthlyTrends() {
        return serviceRequestRepository.countMonthlyTrendsRaw()
                .stream()
                .map(row -> new StatsSummary(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
}