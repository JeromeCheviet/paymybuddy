CREATE TABLE IF NOT EXISTS user
(
    id                  INT NOT NULL AUTO_INCREMENT,
    email               VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(100) NOT NULL,
    name                VARCHAR(50)  NOT NULL,
    rib                 VARCHAR(100) NOT NULL,
    bank_name           VARCHAR(50)  NOT NULL,
    balance             FLOAT        NOT NULL,
    role                BOOLEAN      NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contact
(
    id           INT         NOT NULL AUTO_INCREMENT,
    user_id      INT         NOT NULL,
    friend_id    INT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT userid FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION,
    CONSTRAINT friendid FOREIGN KEY (friend_id) REFERENCES user (id) ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS transaction
(
    id                  INT         NOT NULL AUTO_INCREMENT,
    date                DATE        NOT NULL,
    user_id             INT         NOT NULL,
    beneficiary_user_id INT         NOT NULL,
    transaction_type    VARCHAR(10) NOT NULL,
    amount              FLOAT       NOT NULL,
    percent_fee         FLOAT       NOT NULL,
    description         VARCHAR(255),
    total_amount        FLOAT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (beneficiary_user_id) REFERENCES user (id) ON DELETE CASCADE
);