package com.gianfranco.montreal_intelligence.controller;

import com.gianfranco.montreal_intelligence.dto.StatsSummary;
import com.gianfranco.montreal_intelligence.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/by-borough")
    public List<StatsSummary> getRequestsByBorough() {
        return statsService.getRequestsByBorough();
    }

    @GetMapping("/by-category")
    public List<StatsSummary> getRequestsByCategory() {
        return statsService.getRequestsByCategory();
    }

    @GetMapping("/by-status")
    public List<StatsSummary> getRequestsByStatus() {
        return statsService.getRequestsByStatus();
    }

    @GetMapping("/monthly-trends")
    public List<StatsSummary> getMonthlyTrends() {
        return statsService.getMonthlyTrends();
    }
}