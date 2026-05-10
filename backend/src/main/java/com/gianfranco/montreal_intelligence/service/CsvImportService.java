package com.gianfranco.montreal_intelligence.service;

import com.gianfranco.montreal_intelligence.model.ServiceRequest;
import com.gianfranco.montreal_intelligence.repository.ServiceRequestRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class CsvImportService {

    private final ServiceRequestRepository serviceRequestRepository;

    public CsvImportService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Transactional
    public String importSampleData() {
        int processed = 0;
        int inserted = 0;
        int skipped = 0;

        try {
            ClassPathResource resource = new ClassPathResource("data/sample-service-requests.csv");

            try (
                    Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                    CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)
            ) {
                for (CSVRecord record : csvParser) {
                    processed++;

                    String externalId = record.get("external_id");

                    if (serviceRequestRepository.existsByExternalId(externalId)) {
                        skipped++;
                    } else {
                        ServiceRequest request = new ServiceRequest();

                        request.setExternalId(externalId);
                        request.setCreatedDate(parseDate(record.get("created_date")));
                        request.setClosedDate(parseDate(record.get("closed_date")));
                        request.setBorough(record.get("borough"));
                        request.setGeographicBorough(record.get("geographic_borough"));
                        request.setCategory(record.get("category"));
                        request.setRequestType(record.get("request_type"));
                        request.setStatus(record.get("status"));
                        request.setDescription(record.get("description"));
                        request.setStreet(record.get("street"));
                        request.setLatitude(parseDouble(record.get("latitude")));
                        request.setLongitude(parseDouble(record.get("longitude")));
                        request.setSource(record.get("source"));

                        serviceRequestRepository.save(request);
                        inserted++;
                    }
                }
            }

            return "Import complete. Processed: " + processed +
                    ", Inserted: " + inserted +
                    ", Skipped: " + skipped;

        } catch (Exception e) {
            throw new RuntimeException("Failed to import sample CSV data: " + e.getMessage(), e);
        }
    }

    private LocalDateTime parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return LocalDateTime.parse(value);
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Double.parseDouble(value);
    }
}