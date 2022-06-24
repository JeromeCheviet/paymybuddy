# Pay My Buddy

Pay my buddy is an application to easily send money to your friend.

## Conception

Insert UML and Relational Model.

To create Database, run this script.

```mysql
CREATE DATABASE IF NOT EXISTS paymybuddy CHARACTER SET utf8mb4 COLLATE utf8mb4_general_cli;

CREATE TABLE IF NOT EXISTS user (
id INT AUTO_INCREMENT,
email VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
name VARCHAR(50) NOT NULL,
rib VARCHAR(100) NOT NULL,
bank_name VARCHAR(50) NOT NULL,
balance FLOAT NOT NULL,
role BOOLEAN NOT NULL DEFAULT 0,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contact (
id INT NOT NULL AUTO_INCREMENT,
friend_email VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS assoc_user_contact (
user_id INT NOT NULL,
contact_id INT NOT NULL,
FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY (contact_id) REFERENCES contact (id) ON DELETE RESTRICT ON UPDATE CASCADE,
PRIMARY KEY (user_id, contact_id)
);

CREATE TABLE IF NOT EXISTS transaction (
id INT NOT NULL AUTO_INCREMENT,
date DATE NOT NULL,
user_id INT NOT NULL,
beneficiary_user_email INT NOT NULL,
transaction_type VARCHAR(10) NOT NULL,
amount FLOAT NOT NULL,
percent_fee FLOAT NOT NULL,
description VARCHAR(255),
total_amount FLOAT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
FOREIGN KEY (beneficiary_user_id) REFERENCES user (id) ON DELETE CASCADE
);
```

To try this POC, you can populate the database with this script :

```mysql
INSERT INTO `user` (`email`, `password`, `name`, `rib`, `bank_name`, `transfert_to_bank`, `transfert_from_bank`, `balance`, `role`)
VALUES
('jerome@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Jerome', 'FR700932922111114444', 'banque A', '0.0', '0.0', '0.0', '1'),
('hayley@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Hayley', 'FR702134456787663332', 'banque B', '0.0', '0.0', '0.0', '0'),
('clara@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Clara', 'FR706545033373569645', 'banque A', '0.0', '0.0', '0.0', '0'),
('smith@mail.fr', '$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2', 'Smith', 'FR70765498230992134', 'banque B', '0.0', '0.0', '0.0', '0');
```
For all users, the password is ___password123___.

__Please drop table before use them in production environment !__
