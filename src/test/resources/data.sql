DELETE FROM `user`;

INSERT INTO `user` (`email`, `password`, `name`, `rib`, `bank_name`, `transfert_to_bank`, `transfert_from_bank`,
                    `balance`, `role`)
VALUES ('jerome@mail.fr', '$2y$10$aLCy3mRKF2giARJVZ6kXLevp/.gvM3E5ynVgoiBRx9Rb9Wz32aRrq', 'Jerome',
        'FR700932922111114444', 'banque A', '0.0', '0.0', '0.0', '1'),
       ('hayley@mail.fr', '$2y$10$aLCy3mRKF2giARJVZ6kXLevp/.gvM3E5ynVgoiBRx9Rb9Wz32aRrq', 'Hayley',
        'FR702134456787663332', 'banque B', '0.0', '0.0', '0.0', '0'),
       ('clara@mail.fr', '$2y$10$aLCy3mRKF2giARJVZ6kXLevp/.gvM3E5ynVgoiBRx9Rb9Wz32aRrq', 'Clara',
        'FR706545033373569645', 'banque A', '0.0', '0.0', '0.0', '0'),
       ('smith@mail.fr', '$2y$10$aLCy3mRKF2giARJVZ6kXLevp/.gvM3E5ynVgoiBRx9Rb9Wz32aRrq', 'Smith', 'FR70765498230992134',
        'banque B', '0.0', '0.0', '0.0', '0');
