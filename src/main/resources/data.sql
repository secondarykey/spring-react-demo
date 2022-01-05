INSERT INTO ROLE VALUES('user', '一般ユーザ');
INSERT INTO ROLE VALUES('manager', '管理者');
INSERT INTO ROLE VALUES('admin', 'システム管理者');

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  1,'組織1',NULL,'2022-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  1,'組織1',NULL,'2021-01-01','2022-01-05'
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  2,'下部組織-1',1,'2021-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  4,'下部組織-1-1',2,'2021-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  5,'下部組織-1-2',2,'2021-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  5,'下部組織-1-2',2,'2021-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  3,'下部組織-2',1,'2021-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  2,'組織2',NULL,'2022-01-06',NULL
);

INSERT INTO ORGANIZATION(ORGANIZATION_ID,NAME,PARENT,"START","END")
VALUES (
  6,'組織2-1',2,'2022-01-06',NULL
);

INSERT INTO "DAY"(ORGANIZATION_ID,"DAY","VALUE") 
VALUES(1, '2022-01-06',1);

INSERT INTO "DAY"(ORGANIZATION_ID,"DAY","VALUE") 
VALUES(1, '2022-01-10',1);

INSERT INTO "DAY"(ORGANIZATION_ID,"DAY","VALUE") 
VALUES(1, '2022-01-15',1);

INSERT INTO USERS(ID,NAME,ROLE,BELONG,PASSWORD,EXPIRY)
VALUES(
    'user', '山田太郎','user',1,
    '6c96c4d30bb54424d38c6b09a19dfe305db7ea800a5d9d28265d63af4d5ed767',
    CURRENT_TIMESTAMP
);
INSERT INTO USERS(ID,NAME,ROLE,BELONG,PASSWORD,EXPIRY)
VALUES(
    'manager', '管理士太郎','manager',1,
    '6c96c4d30bb54424d38c6b09a19dfe305db7ea800a5d9d28265d63af4d5ed767',
    CURRENT_TIMESTAMP
);
INSERT INTO USERS(ID,NAME,ROLE,BELONG,PASSWORD,EXPIRY)
VALUES(
    'admin', 'アドミン太郎','admin',1,
    '6c96c4d30bb54424d38c6b09a19dfe305db7ea800a5d9d28265d63af4d5ed767',
    CURRENT_TIMESTAMP
);

INSERT INTO PLACES(ID,NAME,TIMEZONE)
VALUES(
    1, '日本','Asia/Tokyo'
);

INSERT INTO places(ID,NAME,TIMEZONE)
VALUES(
    2, '中国','Asia/Shanghai'
);

INSERT INTO TODOS("VALUE") VALUES ('TODO1');
INSERT INTO TODOS("VALUE") VALUES ('TODO2');

INSERT INTO PLANS(ID,PLACES_ID,DATE) VALUES( 1, 1, '2021-10-10T12:00:00');
INSERT INTO PLANS(ID,PLACES_ID,DATE) VALUES( 2, 1, '2021-10-11T12:00:00');
INSERT INTO PLANS(ID,PLACES_ID,DATE) VALUES( 3, 2, '2021-10-10T12:00:00');

INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 1, 1, '1 = 1-1', '2021-10-10T08:00:00','2021-10-10T11:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 2, 1, '2 = 1-2', '2021-10-10T11:00:00','2021-10-10T13:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 3, 1, '3 = 1-3', '2021-10-10T14:00:00','2021-10-10T18:00:00');

INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 4, 2, '4 = 2-1', '2021-10-11T08:00:00','2021-10-11T11:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 5, 2, '5 = 2-2', '2021-10-11T11:00:00','2021-10-11T13:00:00');

INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 6, 3, '6 = 3-1', '2021-10-10T08:00:00','2021-10-10T11:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,NAME,START,"END")
    VALUES( 7, 3, '7 = 3-2', '2021-10-10T11:00:00','2021-10-10T13:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,name,START,"END")
    VALUES( 8, 3, '8 = 3-3', '2021-10-10T14:00:00','2021-10-10T18:00:00');
INSERT INTO PLAN_DETAILS (ID,PLANS_ID,name,START,"END")
    VALUES( 9, 3, '9 = 3-4', '2021-10-10T21:00:00','2021-10-11T03:00:00');
