/* 権限マスタ */
create table role(
    id varchar(16) primary key,
    name varchar(128)
);

/* ユーザ情報 */
create table users(
    id varchar(255) primary key,
    name varchar(255),
    password varchar(255),
    role varchar(16),
    expiry timestamp,
    foreign key(role) references role(id)
);

create table todos(
    id SERIAL,
    value varchar(32)
);