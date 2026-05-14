package com.gianfranco.montreal_intelligence.dto;

public class StatsSummary {

    private String label;
    private Long count;

    public StatsSummary(String label, Long count) {
        this.label = label;
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public Long getCount() {
        return count;
    }
}