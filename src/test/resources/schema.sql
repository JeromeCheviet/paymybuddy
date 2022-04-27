CREATE TABLE IF NOT EXISTS user
(
    id                  INT AUTO_INCREMENT,
    email               VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(100) NOT NULL,
    name                VARCHAR(50)  NOT NULL,
    rib                 VARCHAR(100) NOT NULL,
    bank_name           VARCHAR(50)  NOT NULL,
    transfert_to_bank   FLOAT,
    transfert_from_bank FLOAT,
    balance             FLOAT        NOT NULL,
    role                BOOLEAN      NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contact
(
    id           INT         NOT NULL AUTO_INCREMENT,
    friend_email VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS assoc_user_contact
(
    user_id    INT NOT NULL,
    contact_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES contact (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (user_id, contact_id)
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