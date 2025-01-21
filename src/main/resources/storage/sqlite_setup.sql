CREATE TABLE IF NOT EXISTS regrowthsmp_users
(
    uuid BINARY(128) NOT NULL PRIMARY KEY,
    username CHAR(17),
    data JSON
);