INSERT INTO artist (name) VALUES ('Serj Tankian');
INSERT INTO artist (name) VALUES ('Mike Shinoda');
INSERT INTO artist (name) VALUES ('Michel Teló');
INSERT INTO artist (name) VALUES ('Guns N'' Roses');

INSERT INTO album (title) VALUES ('Harakiri');
INSERT INTO album (title) VALUES ('Black Blooms');
INSERT INTO album (title) VALUES ('The Rough Dog');
INSERT INTO album (title) VALUES ('The Rising Tied');
INSERT INTO album (title) VALUES ('Post Traumatic');
INSERT INTO album (title) VALUES ('Post Traumatic EP');
INSERT INTO album (title) VALUES ('Where''d You Go');
INSERT INTO album (title) VALUES ('Bem Sertanejo');
INSERT INTO album (title) VALUES ('Bem Sertanejo - O Show (Ao Vivo)');
INSERT INTO album (title) VALUES ('Bem Sertanejo - (1ª Temporada) - EP');
INSERT INTO album (title) VALUES ('Use Your Illusion I');
INSERT INTO album (title) VALUES ('Use Your Illusion II');
INSERT INTO album (title) VALUES ('Greatest Hits');

-- Serj Tankian
INSERT INTO artist_album (artist_id, album_id) VALUES (1, 1);
INSERT INTO artist_album (artist_id, album_id) VALUES (1, 2);
INSERT INTO artist_album (artist_id, album_id) VALUES (1, 3);

-- Mike Shinoda
INSERT INTO artist_album (artist_id, album_id) VALUES (2, 4);
INSERT INTO artist_album (artist_id, album_id) VALUES (2, 5);
INSERT INTO artist_album (artist_id, album_id) VALUES (2, 6);
INSERT INTO artist_album (artist_id, album_id) VALUES (2, 7);

-- Michel Teló
INSERT INTO artist_album (artist_id, album_id) VALUES (3, 8);
INSERT INTO artist_album (artist_id, album_id) VALUES (3, 9);
INSERT INTO artist_album (artist_id, album_id) VALUES (3, 10);

-- Guns N' Roses
INSERT INTO artist_album (artist_id, album_id) VALUES (4, 11);
INSERT INTO artist_album (artist_id, album_id) VALUES (4, 12);
INSERT INTO artist_album (artist_id, album_id) VALUES (4, 13);

-- Default user is now created by the application on startup
