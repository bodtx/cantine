drop table IF EXISTS authorities;
drop table IF EXISTS users;

create table users(username varchar_ignorecase(50) not null primary key, password varchar_ignorecase(255) not null, enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null, authority varchar_ignorecase(50) not null, constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username, authority);

insert into users values ('CER3100441','$2a$10$lRHm7s/FphEyoYGDBcLn7eiLdyyOJV9q7cPXd0PzYXZ96mGLpLm7S',true);
insert into users values ('CER3100307','$2a$10$o4oZX9wpemPAexdpgFsIU.PKKiRczTV8jwE9Q/okAUt5BggwDzE8.',true);
insert into users values ('CER3100444','$2a$10$llcF2KjNnKTJiIfbCuAnnehiFunvGhtwngkVf3KUQpW3pzPcm1xjG',true);

insert into authorities values ('CER3100441','USER');
insert into authorities values ('CER3100307','USER');
insert into authorities values ('CER3100444','USER');