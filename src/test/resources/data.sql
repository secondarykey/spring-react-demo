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

insert into plans(id,places_id,date)
values( 1, 1, '2021-10-10T12:00:00');

insert into plans(id,places_id,date)
values( 2, 1, '2021-10-11T12:00:00');

insert into plans(id,places_id,date)
values( 3, 2, '2021-10-10T12:00:00');

insert into plan_details(id,plans_id,name,start,"end")
values( 1, 1, '1 = 1-1', '2021-10-10T08:00:00','2021-10-10T11:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 2, 1, '2 = 1-2', '2021-10-10T11:00:00','2021-10-10T13:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 3, 1, '3 = 1-3', '2021-10-10T14:00:00','2021-10-10T18:00:00');

insert into plan_details(id,plans_id,name,start,"end")
values( 4, 2, '4 = 2-1', '2021-10-11T08:00:00','2021-10-11T11:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 5, 2, '5 = 2-2', '2021-10-11T11:00:00','2021-10-11T13:00:00');

insert into plan_details(id,plans_id,name,start,"end")
values( 6, 3, '6 = 3-1', '2021-10-10T08:00:00','2021-10-10T11:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 7, 3, '7 = 3-2', '2021-10-10T11:00:00','2021-10-10T13:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 8, 3, '8 = 3-3', '2021-10-10T14:00:00','2021-10-10T18:00:00');
insert into plan_details(id,plans_id,name,start,"end")
values( 9, 3, '9 = 3-4', '2021-10-10T21:00:00','2021-10-11T03:00:00');
