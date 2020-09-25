insert
    into
        authorities (authority)
    values
        ('ROLE_USER');

insert
    into
        authorities (authority)
    values
        ('ROLE_ADMIN');

insert
    into
        users (username, password, enabled, authority_id)
    values
        ('root', '$2a$10$qn83UbXhBZT3Wixq.ECOYO6bDRrZLIOAoQG./Tra7N4BYxiUe3k6i', true, (select id from authorities where authority = 'ROLE_ADMIN'));

insert
    into
        posts (name, description, user_id, created)
    values
        ('Языки программирования', 'Какой язык лучше всех?', (select id from users where username = 'root'), now());

insert
    into
        comments (contain, created, post_id, user_id)
    values
        ('Java', now(), (select id from posts where name = 'Языки программирования'), (select id from users where username = 'root'))