CREATE TABLE artist (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE album (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year INTEGER
);

CREATE TABLE artist_album (
    artist_id BIGINT NOT NULL,
    album_id BIGINT NOT NULL,
    PRIMARY KEY (artist_id, album_id),
    FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE,
    FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE
);

CREATE TABLE album_image (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100),
    url VARCHAR(500),
    FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE
);

CREATE TABLE regional (
    internal_id BIGSERIAL PRIMARY KEY,
    id INTEGER,
    nome VARCHAR(200),
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
