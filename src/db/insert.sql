insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, password, enabled, authority_id)
values ('root', '$2a$10$iT6N/9fbtebNEYUyYW4aLOt8CIrs.2OTdOApl723fE1T83gPxJBOK', true, (select id from authorities where authority = 'ROLE_ADMIN'));
