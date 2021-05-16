CREATE TABLE app_user (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    authority VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    locked BOOLEAN NOT NULL,

    PRIMARY KEY (id)
);