drop table if exists users;
create table users
(
    id       bigserial,
    password VARCHAR(80) not null,
    email    VARCHAR(50) not null UNIQUE,
    username VARCHAR(50) not null UNIQUE,
    PRIMARY KEY (id)
);


insert into users (password, email, username)
values ('$2y$12$CFS6mJLQHsUMQnFfr8tAtOjcHAJGQ1JVlq77ZenO3JE2FEa.E2Nfu', 'admin@gmail.com', 'admin'),
       ('$2y$12$viohTRLsOIqZYszXY4DhSOjuDI/x8T/QZ0mUG0leWCm7N8r0Oi1FK', 'user@gmail.com', 'user');

drop table if exists roles;
create table roles
(
    id   serial,
    name VARCHAR(50) not null,
    primary key (id)
);

insert into roles (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

drop table if exists users_roles;
create table users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2);

create table clients
(
    id    bigserial,
    phone VARCHAR(30)  not null UNIQUE,
    name  VARCHAR(250),
    title VARCHAR(250) not null,
    PRIMARY KEY (id)
);


insert into clients (phone, name, title)
values ('79201956756', 'Vasy', 'ooo company 1'),
       ('79208956759', 'Valentin', 'ooo company 8'),
       ('79238956758', 'Vova', 'ooo company 7'),
       ('79208956744', 'Valentin', 'ooo company 6'),
       ('79108956733', 'Viktor', 'ooo company 4'),
       ('79108956751', 'Vasy', 'ooo company 3'),
       ('79108946353', 'Viktor', 'ooo company 2'),
       ('79108956755', 'Vany', 'ooo company 3'),
       ('79108956435', 'Valentin', 'ooo company 6'),
       ('79108156723', 'Vany', 'ooo company 5');



