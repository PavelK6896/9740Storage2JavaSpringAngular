
create table clients
(
    id    bigserial,
    phone VARCHAR(30)  not null UNIQUE,
    name  VARCHAR(250),
    title VARCHAR(250) not null,
    PRIMARY KEY (id)
);


insert into clients (phone, name, title)
values ('7920887', 'Vasy', 'ooo company 12414'),
       ('79208891', 'Valentin', 'ooo company 342324'),
       ('79208892', 'Valentin', 'ooo eee 342324'),
       ('79208893', 'Valentin', 'ooo www 342324'),
       ('79208815323', 'Viktor', 'ooo rr 342324'),
       ('7920881235', 'Viktor', 'ww asd 342324'),
       ('79208813245', 'Viktor', 'ee 3'),
       ('7920881455', 'Vany', 'rr 3'),
       ('792088123425', 'Vany', 'qq 3'),
       ('79208813455', 'Vany', 'ssss 3'),
       ('79208816785', 'Vova', 'hhh 3'),
       ('792088167865', 'Vova', 'ddddd 3'),
       ('792089567866', 'Vika', 'sssssss 4');




