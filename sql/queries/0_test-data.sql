-- password is 'ahxik9oow0tiv8eKiZoh'
INSERT INTO traveller (email, first_name, last_name, username, team_fk, password)
VALUES ('loic.herman@heig-vd.ch', 'Loïc', 'Herman', 'lherman', null,
        '$argon2id$v=19$m=65536,t=3,p=4$ZUo1SmZienZYWDFEVmd4TFVNR2pReWVNN3RuN21wTWlKakdZMENndnVsMD0$s4ye3AmnDaYD+UnMHs7hAK5bxsxh9sbsKv9TfiWfmkY'),
       ('sacha.butty@heig-vd.ch', 'Sacha', 'Butty', 'sbutty', 1,
        '$argon2id$v=19$m=65536,t=3,p=4$ZUo1SmZienZYWDFEVmd4TFVNR2pReWVNN3RuN21wTWlKakdZMENndnVsMD0$s4ye3AmnDaYD+UnMHs7hAK5bxsxh9sbsKv9TfiWfmkY'),
       ('vicky.butty@heig-vd.ch', 'Vicky', 'Butty', 'vbutty', 1,
        '$argon2id$v=19$m=65536,t=3,p=4$ZUo1SmZienZYWDFEVmd4TFVNR2pReWVNN3RuN21wTWlKakdZMENndnVsMD0$s4ye3AmnDaYD+UnMHs7hAK5bxsxh9sbsKv9TfiWfmkY');

INSERT INTO journey (start_date, end_date, grade, user_fk)
VALUES (NOW(), NOW(), 5.0, 'loic.herman@heig-vd.ch');

INSERT INTO journey_segment (journey_fk, segment_fk)
VALUES (1, 26),
       (1, 235),
       (1, 354),
       (1, 438),
       (1, 540),
       (1, 541),
       (1, 819),
       (1, 1142),
       (1, 1143),
       (1, 1177),
       (1, 1263),
       (1, 1269),
       (1, 2354);

INSERT INTO journey (id, start_date, end_date, grade, user_fk)
VALUES (2, NOW(), NOW(), 3.2, 'vicky.butty@heig-vd.ch');

INSERT INTO journey_segment (journey_fk, segment_fk)
VALUES (2, 392),
       (2, 985),
       (2, 2127),
       (2, 2499),
       (2, 2501),
       (2, 2503),
       (2, 2636),
       (2, 2650);
