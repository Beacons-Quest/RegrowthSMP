CREATE TABLE IF NOT EXISTS regrowthsmp_users
(
    uuid BINARY(16) NOT NULL PRIMARY KEY,
    username CHAR(17),
    data JSON
);