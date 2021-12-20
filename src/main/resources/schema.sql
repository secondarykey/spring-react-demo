drop table users if exists;
drop table role if exists;

drop table todos if exists;
drop table times if exists;
drop table plan_details if exists;
drop table plans if exists;
drop table places if exists;

create table role(
    id varchar(16) primary key,
    name varchar(128)
);

create table users(
    id varchar(255) primary key,
    name varchar(255),
    password varchar(255),
    role varchar(16),
    expiry timestamp with time zone,
    foreign key(role) references role(id)
);

create table todos(
    id SERIAL primary key,
    "value" varchar(32)
);

create table times(
    id SERIAL primary key,
    "value" varchar(32),
    "date" date,
    "time" time,
    date_without timestamp without time zone,
    offset_with timestamp with time zone
);

create table places (
    id SERIAL primary key,
    name varchar(128),
    timezone varchar(32)
);

create table plans (
  id Serial primary key,
  places_id integer,
  date timestamp without time zone,
  foreign key(places_id) references places(id)
);

create table plan_details (
  id Serial primary key,
  plans_id integer,
  name varchar(128),
  start timestamp without time zone,
  "end" timestamp without time zone,
  foreign key(plans_id) references plans(id)
);

