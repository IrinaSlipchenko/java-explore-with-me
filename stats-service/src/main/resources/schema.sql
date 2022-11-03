CREATE TABLE hits
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app     VARCHAR,
    uri     VARCHAR,
    ip      VARCHAR,
    created TIMESTAMP
);