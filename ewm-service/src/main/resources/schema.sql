DROP TABLE IF EXISTS categories, users, events, requests, compilations, compilations_events, friends;

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE

);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    initiator_id       BIGINT REFERENCES users (id),
    category_id        BIGINT REFERENCES categories (id),
    title              VARCHAR(120) NOT NULL,
    annotation         VARCHAR(2000),
    description        VARCHAR(7000),
    created            TIMESTAMP WITHOUT TIME ZONE,
    published          TIMESTAMP WITHOUT TIME ZONE,
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    lat                FLOAT,
    lon                FLOAT,
    paid               BOOLEAN,
    participant_limit  INT,
    request_moderation BOOLEAN,
    state              VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id     BIGINT REFERENCES events (id),
    requester_id BIGINT REFERENCES users (id),
    created      TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(30)

);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN,
    title  VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    event_id        BIGINT REFERENCES events (id),
    compilations_id BIGINT REFERENCES compilations (id)
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id   BIGINT REFERENCES users (id),
    friend_id BIGINT REFERENCES users (id)
);