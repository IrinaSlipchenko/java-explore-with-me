CREATE TABLE categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR NOT NULL,
    email VARCHAR NOT NULL

);

CREATE TABLE events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    initiator_id      BIGINT REFERENCES users (id),
    category_id       BIGINT REFERENCES categories (id),
    title             VARCHAR(120) NOT NULL,
    annotation        VARCHAR(2000),
    description       VARCHAR(7000),
    created           TIMESTAMP,
    published         TIMESTAMP,
    eventDate         TIMESTAMP,
    lat               FLOAT,
    lon               FLOAT,
    paid              BOOLEAN,
    participantLimit  INT,
    requestModeration BOOLEAN,
    state             VARCHAR(20)
);

CREATE TABLE requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id     BIGINT REFERENCES events (id),
    requester_id BIGINT REFERENCES users (id),
    created      TIMESTAMP,
    status       VARCHAR(20)

);

CREATE TABLE compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN,
    title  VARCHAR
);

CREATE TABLE compilations_events
(
    event_id        BIGINT REFERENCES events (id),
    compilations_id BIGINT REFERENCES compilations (id)
);