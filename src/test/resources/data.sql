insert into role values('user', '一般ユーザ');
insert into role values('manager', '管理者');
insert into role values('admin', 'システム管理者');

insert into users(id,name,role,password,expiry)
values(
    'user', '山田太郎','user',
    hash('sha256', stringtoutf8('passw0rd')),
    CURRENT_TIME
);
insert into users(id,name,role,password,expiry)
values(
    'manager', '管理士太郎','manager',
    hash('sha256', stringtoutf8('passw0rd')),
    CURRENT_TIME
);
insert into users(id,name,role,password,expiry)
values(
    'admin', 'アドミン太郎','admin',
    hash('sha256', stringtoutf8('passw0rd')),
    CURRENT_TIME
);

insert into todos(value) values ('TODO1');
insert into todos(value) values ('TODO2');
