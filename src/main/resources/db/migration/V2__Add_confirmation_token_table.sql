CREATE TABLE confirmation_token (
    id BIGINT NOT NULL AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    app_user_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    expires_at DATETIME(6),
    confirmed_at DATETIME(6),

    PRIMARY KEY (id),
    FOREIGN KEY (app_user_id) REFERENCES app_user (id)
);