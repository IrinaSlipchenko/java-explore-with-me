CREATE TABLE categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  TEXT NOT NULL,
    email TEXT NOT NULL

);

CREATE TABLE events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    initiator_id      BIGINT REFERENCES users (id),
    category_id       BIGINT REFERENCES categories (id),
    title             TEXT NOT NULL,
    annotation        TEXT,
    description       TEXT,
    created           TIMESTAMP,
    published         TIMESTAMP,
    eventDate         TIMESTAMP,
    lat               FLOAT,
    lon               FLOAT,
    paid              BOOLEAN,
    participantLimit  INT,
    requestModeration BOOLEAN,
    state             TEXT
);

CREATE TABLE requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id     BIGINT REFERENCES events (id),
    requester_id BIGINT REFERENCES users (id),
    created      TIMESTAMP,
    status       TEXT

);

CREATE TABLE compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN,
    title  TEXT
);

CREATE TABLE compilations_events
(
    event_id        BIGINT REFERENCES events (id),
    compilations_id BIGINT REFERENCES compilations (id)
);