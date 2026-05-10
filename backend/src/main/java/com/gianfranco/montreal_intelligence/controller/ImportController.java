package com.gianfranco.montreal_intelligence.controller;

import com.gianfranco.montreal_intelligence.service.CsvImportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {

    private final CsvImportService csvImportService;

    public ImportController(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostMapping("/api/import/sample")
    public String importSampleData() {
        return csvImportService.importSampleData();
    }
}