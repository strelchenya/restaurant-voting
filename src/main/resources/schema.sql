DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS users;

CREATE TABLE restaurant
(
    id    INTEGER AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_title_idx ON restaurant (title);

CREATE TABLE dish
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    local_date    DATE DEFAULT now() NOT NULL,
    price         INTEGER            NOT NULL,
    title         VARCHAR(255)       NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    CONSTRAINT dish_unique_restaurant_title_date_idx UNIQUE (restaurant_id, title, local_date)
);
CREATE INDEX dish_date_idx ON dish (local_date);

CREATE TABLE users
(
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL,
    registered TIMESTAMP DEFAULT NOW() NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    local_date    DATE DEFAULT NOW() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    user_id       INTEGER            NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_date_idx ON vote (user_id, local_date);