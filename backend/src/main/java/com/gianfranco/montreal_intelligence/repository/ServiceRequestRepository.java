package com.gianfranco.montreal_intelligence.repository;

import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}