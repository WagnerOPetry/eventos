-- Flyway migration: create events table
CREATE TABLE IF NOT EXISTS events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    event_date_time TIMESTAMP NOT NULL,
    location VARCHAR(200),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);
