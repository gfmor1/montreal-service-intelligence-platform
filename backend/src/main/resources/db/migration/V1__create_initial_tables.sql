CREATE TABLE service_requests (
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR(100) UNIQUE NOT NULL,
    created_date TIMESTAMP,
    closed_date TIMESTAMP,
    borough VARCHAR(255),
    geographic_borough VARCHAR(255),
    category VARCHAR(255),
    request_type VARCHAR(255),
    status VARCHAR(100),
    description TEXT,
    street VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    source VARCHAR(100),
    imported_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE import_runs (
    id BIGSERIAL PRIMARY KEY,
    started_at TIMESTAMP NOT NULL,
    finished_at TIMESTAMP,
    records_processed INTEGER NOT NULL DEFAULT 0,
    records_inserted INTEGER NOT NULL DEFAULT 0,
    records_skipped INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL,
    error_message TEXT
);

CREATE INDEX idx_service_requests_borough
ON service_requests(borough);

CREATE INDEX idx_service_requests_category
ON service_requests(category);

CREATE INDEX idx_service_requests_status
ON service_requests(status);

CREATE INDEX idx_service_requests_created_date
ON service_requests(created_date);