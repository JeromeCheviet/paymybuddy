USE paymybuddy;

CREATE TABLE `user`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `email`     varchar(50)  NOT NULL,
    `password`  varchar(100) NOT NULL,
    `name`      varchar(50)  NOT NULL,
    `rib`       varchar(100) NOT NULL,
    `bank_name` varchar(50)  NOT NULL,
    `balance`   float        NOT NULL,
    `role`      boolean      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
);

CREATE TABLE `contact`
(
    `user_id`   int NOT NULL,
    `friend_id` int NOT NULL,
    PRIMARY KEY (`user_id`, `friend_id`),
    KEY `userid` (`user_id`),
    KEY `friendid` (`friend_id`),
    CONSTRAINT `friendid` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`),
    CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `transaction`
(
    `id`                  int   NOT NULL AUTO_INCREMENT,
    `date`                date  NOT NULL,
    `user_id`             int   NOT NULL,
    `beneficiary_user_id` int   NOT NULL,
    `amount`              float NOT NULL,
    `description`         varchar(255) DEFAULT NULL,
    `fee_amount`          float        DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `beneficiary_user_id` (`beneficiary_user_id`),
    CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`beneficiary_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

INSERT INTO `user` (`email`, `password`, `name`, `rib`, `bank_name`, `balance`, `role`)
VALUES ('jerome@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Jerome',
        'FR700932922111114444', 'banque A', '0.0', '1'),
       ('hayley@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Hayley',
        'FR702134456787663332', 'banque B', '0.0', '0'),
       ('clara@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Clara',
        'FR706545033373569645', 'banque A', '0.0', '0'),
       ('smith@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Smith', 'FR70765498230992134',
        'banque B', '0.0', '0');