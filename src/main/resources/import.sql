drop table IF EXISTS authorities;
drop table IF EXISTS users;

create table users(username varchar_ignorecase(50) not null primary key, password varchar_ignorecase(255) not null, enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null, authority varchar_ignorecase(50) not null, constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username, authority);

insert into users values ('CER3100441','$2a$10$dwho.3iD8eqhH3JbTxjUj.syogUtvSpOpSjm7XBRVPB/o7f4FuZLW',true);
insert into users values ('CER3100307','$2a$10$R3Pa1UXWA9Cr5jDtoQPiveimiubTDc0Ud7sOLFyS6.K3p1Bd84RN2',true);
insert into users values ('CER3100444','$2a$10$BJ5sju4TsU.wu1xE4UJVbuaBW0donzEUax3WqvN5jX/zxgL8sX8we',true);

insert into authorities values ('CER3100441','USER');
insert into authorities values ('CER3100307','USER');
insert into authorities values ('CER3100444','USER');