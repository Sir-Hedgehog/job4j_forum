CREATE TABLE authorities (
  id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
  id serial primary key,
  username VARCHAR(50) NOT NULL unique,
  password VARCHAR(100) NOT NULL,
  enabled boolean,
  authority_id int not null references authorities(id)
);

CREATE TABLE posts (
  id serial primary key,
  name varchar(2000),
  description varchar(200000),
  created timestamp without time zone not null default now(),
  user_id int references users(id)
);

CREATE TABLE comments (
  id serial primary key,
  contain varchar(200000),
  created timestamp without time zone not null default now(),
  post_id int references posts(id),
  user_id int references users(id)
);