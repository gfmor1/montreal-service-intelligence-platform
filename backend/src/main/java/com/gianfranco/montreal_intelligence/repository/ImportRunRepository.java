package com.gianfranco.montreal_intelligence.repository;

import com.gianfranco.montreal_intelligence.model.ImportRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRunRepository extends JpaRepository<ImportRun, Long> {
}