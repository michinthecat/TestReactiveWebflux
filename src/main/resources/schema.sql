CREATE TABLE IF NOT EXISTS account
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(150) UNIQUE
);