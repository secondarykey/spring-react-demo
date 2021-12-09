insert into role values('user', '一般ユーザ');
insert into role values('manager', '管理者');
insert into role values('admin', 'システム管理者');

insert into users(id,name,role,password,expiry)
values(
    'user', '山田太郎','user',
    md5('passw0rd'),
    CURRENT_TIMESTAMP
);
insert into users(id,name,role,password,expiry)
values(
    'manager', '管理士太郎','manager',
    md5('passw0rd'),
    CURRENT_TIMESTAMP
);
insert into users(id,name,role,password,expiry)
values(
    'admin', 'アドミン太郎','admin',
    md5('passw0rd'),
    CURRENT_TIMESTAMP
);

insert into todos(value) values ('TODO1');
insert into todos(value) values ('TODO2');

insert into places(id,name,timezone)
values(
    1, '日本','Asia/Tokyo'
);

insert into places(id,name,timezone)
values(
    2, '中国','Asia/Shanghai'
);