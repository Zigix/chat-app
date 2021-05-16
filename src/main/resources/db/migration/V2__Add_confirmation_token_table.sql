CREATE TABLE confirmation_token (
    id BIGSERIAL NOT NULL,
    token VARCHAR(255) NOT NULL,
    app_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP,
    confirmed_at TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (app_user_id) REFERENCES app_user (id)
);