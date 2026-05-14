package com.gianfranco.montreal_intelligence.repository;

import com.gianfranco.montreal_intelligence.dto.StatsSummary;
import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    boolean existsByExternalId(String externalId);

    @Query("""
        SELECT r FROM ServiceRequest r
        WHERE (:borough IS NULL OR r.borough = :borough)
          AND (:status IS NULL OR r.status = :status)
          AND (:category IS NULL OR r.category = :category)
    """)
    Page<ServiceRequest> findWithFilters(
            @Param("borough") String borough,
            @Param("status") String status,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("""
        SELECT new com.gianfranco.montreal_intelligence.dto.StatsSummary(r.borough, COUNT(r))
        FROM ServiceRequest r
        GROUP BY r.borough
        ORDER BY COUNT(r) DESC
    """)
    List<StatsSummary> countByBorough();

    @Query("""
        SELECT new com.gianfranco.montreal_intelligence.dto.StatsSummary(r.category, COUNT(r))
        FROM ServiceRequest r
        GROUP BY r.category
        ORDER BY COUNT(r) DESC
    """)
    List<StatsSummary> countByCategory();

    @Query("""
        SELECT new com.gianfranco.montreal_intelligence.dto.StatsSummary(r.status, COUNT(r))
        FROM ServiceRequest r
        GROUP BY r.status
        ORDER BY COUNT(r) DESC
    """)
    List<StatsSummary> countByStatus();
}