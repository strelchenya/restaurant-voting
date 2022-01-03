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
VALUES ('2021-12-12', '06:01', 1, 1),
       ('2021-12-12', '08:00', 2, 2),
       ('2021-12-13', '08:20', 3, 1),
       ('2021-12-13', '10:59', 2, 2);

INSERT INTO dish (local_date, price, title, restaurant_id)
VALUES ('2021-12-12', 211, 'Just Lunch', 1),
       ('2021-12-12', 244, 'Steak frites', 1),
       ('2021-12-12', 135, 'Chicken confit', 1),
       ('2021-12-12', 1251, 'French onion soup', 2),
       ('2021-12-12', 742, 'Bouillabaisse', 2),
       ('2021-12-12', 3425, 'Salmon en papillote', 2),
       ('2021-12-12', 1125, 'Quiche Lorraine', 3),
       ('2021-12-12', 980, 'Croque monsieur', 3),
       ('2021-12-12', 4423, 'Boeuf bourguignon', 3),
       ('2021-12-13', 332, 'Cassoulet', 1),
       ('2021-12-13', 75, 'Coca-Cola', 1),
       ('2021-12-13', 121, 'Lamb shank navarin', 1),
       ('2021-12-13', 99, 'Hazelnut dacquoise', 2),
       ('2021-12-13', 1292, 'Frangipane tart', 2),
       ('2021-12-13', 519, 'Souffle', 2),
       ('2021-12-13', 1200, 'Paris-brest', 3),
       ('2021-12-13', 119, 'Cheesy tuna pasta', 3),
       ('2021-12-13', 113, 'Tarte tatin', 3);