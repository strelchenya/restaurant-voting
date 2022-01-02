DELETE
FROM user_roles;
DELETE
FROM vote;
DELETE
FROM users;
DELETE
FROM restaurant;
DELETE
FROM dish;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (title)
VALUES ('Just restaurant'),
       ('Burger Empire'),
       ('Eatery');

INSERT INTO vote (local_date, local_time, restaurant_id, user_id)
VALUES ('2021-12-12', '09:00', 1, 1),
       ('2021-12-12', '08:00', 2, 2),
       ('2021-12-13', '07:00', 2, 1),
       ('2021-12-13', '06:00', 3, 2),
       ('2021-12-14', '05:00', 3, 1),
       ('2021-12-14', '04:00', 1, 2);

INSERT INTO dish (local_date, price, title, restaurant_id)
VALUES ('2021-12-10', 211, 'Just Lunch', 1),
       ('2021-12-10', 3425, 'Coca-Cola', 3),
       (now(), 123, 'Burger', 2);