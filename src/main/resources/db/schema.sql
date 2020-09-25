--ALTER DATABASE name_of_database SET timezone TO 'Europe/Moscow';

CREATE TABLE if not exists authorities (
  id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE if not exists users (
  id serial primary key,
  username VARCHAR(50) NOT NULL unique,
  password VARCHAR(100) NOT NULL,
  enabled boolean,
  authority_id int not null references authorities(id)
);

CREATE TABLE if not exists posts (
  id serial primary key,
  name varchar(2000),
  description varchar(200000),
  created timestamp with time zone not null default now(),
  user_id int references users(id)
);

CREATE TABLE if not exists comments (
  id serial primary key,
  contain varchar(200000),
  created timestamp with time zone not null default now(),
  post_id int references posts(id),
  user_id int references users(id)
);