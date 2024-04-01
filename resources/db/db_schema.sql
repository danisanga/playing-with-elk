/* Custom schema */
CREATE SCHEMA IF NOT EXISTS my_schema;
SET search_path TO my_schema;

/* Tables */
CREATE TABLE my_schema.dispensers
(
    id          VARCHAR(255)   NOT NULL PRIMARY KEY,
    flow_volume DECIMAL(10, 8) NOT NULL,
    created_at  TIMESTAMP      NOT NULL
);

CREATE TABLE my_schema.usages
(
    id           VARCHAR(255) NOT NULL PRIMARY KEY,
    dispenser_id VARCHAR(255) NOT NULL REFERENCES dispensers (id),
    opened_at    TIMESTAMP    NOT NULL,
    closed_at    TIMESTAMP
);
