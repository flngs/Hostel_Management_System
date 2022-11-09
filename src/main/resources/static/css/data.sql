DELETE FROM room;
ALTER TABLE room AUTO_INCREMENT = 1;
INSERT INTO room (is_occupied, number, standard)
VALUES (0, 1, 'Standard room'),
       (0, 2, 'Standard room'),
       (0, 3, 'Standard room'),
       (0, 4, 'Standard room'),
       (0, 5, 'Standard room'),
       (0, 6, 'Standard room'),
       (0, 7, 'Standard room'),
       (0, 8, 'Standard room'),
       (0, 9, 'Standard room'),
       (0, 10, 'Standard room'),
       (0, 11, 'Standard room'),
       (0, 12, 'Standard room'),
       (0, 13, 'Standard room'),
       (0, 14, 'Standard room'),
       (0, 15, 'Standard room'),
       (0, 16, 'Standard room'),
       (0, 17, 'Standard room'),
       (0, 18, 'Standard room'),
       (0, 19, 'Standard room'),
       (0, 20, 'Standard room'),
       (0, 21, 'Standard room'),
       (0, 22, 'Standard room');

DELETE FROM user;
INSERT INTO (first_name, last_name, login, password)
VALUES ('admin', 'admin', 'admin', 'admin');