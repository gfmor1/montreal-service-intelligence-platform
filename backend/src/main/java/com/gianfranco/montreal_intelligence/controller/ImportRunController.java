package com.gianfranco.montreal_intelligence.controller;

import com.gianfranco.montreal_intelligence.model.ImportRun;
import com.gianfranco.montreal_intelligence.repository.ImportRunRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImportRunController {

    private final ImportRunRepository importRunRepository;

    public ImportRunController(ImportRunRepository importRunRepository) {
        this.importRunRepository = importRunRepository;
    }

    @GetMapping("/api/import-runs")
    public List<ImportRun> getImportRuns() {
        return importRunRepository.findAll();
    }
}