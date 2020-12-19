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



