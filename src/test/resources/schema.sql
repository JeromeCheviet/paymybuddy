CREATE TABLE IF NOT EXISTS user
(
    id        INT          NOT NULL AUTO_INCREMENT,
    email     VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    name      VARCHAR(50)  NOT NULL,
    rib       VARCHAR(100) NOT NULL,
    bank_name VARCHAR(50)  NOT NULL,
    balance   FLOAT        NOT NULL,
    role      BOOLEAN      NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contact
(
    user_id   INT NOT NULL,
    friend_id INT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    KEY `userid` (`user_id`),
    KEY `friendid` (`friend_id`),
    CONSTRAINT userid FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION,
    CONSTRAINT friendid FOREIGN KEY (friend_id) REFERENCES user (id) ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS transaction
(
    id                  INT   NOT NULL AUTO_INCREMENT,
    date                DATE  NOT NULL,
    user_id             INT   NOT NULL,
    beneficiary_user_id INT   NOT NULL,
    amount              FLOAT NOT NULL,
    description         VARCHAR(255),
    fee_amount          FLOAT NOT NULL,
    PRIMARY KEY (id),
    KEY `user_id` (`user_id`),
    KEY `beneficiary_user_id` (`beneficiary_user_id`),
    CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`beneficiary_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);