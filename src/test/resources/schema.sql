create table role(
    id varchar(16) primary key,
    name varchar(128)
);

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

create table places (
    id SERIAL primary key,
    name varchar(128),
    timezone varchar(32)
);

create table plans (
  id Serial primary key,
  places_id Serial,
  date timestamp without time zone,
  foreign key(places_id) references places(id)
);

create table plan_details (
  id Serial primary key,
  plans_id Serial,
  start timestamp without time zone,
  end timestamp without time zone,
  foreign key(plans_id) references plans(id)
);

/*
create table results {
  id Serial primary key,
  plans_id serial,
  foreign key(plans_id) references plans(id)
}

create table result_details {
  id Serial primary key,
  plan_details_id Serial,
  resules_id Serial,
  status integer,
  foreign key(results_id) references results(id),
  foreign key(plan_details_id) references plan_details(id)
}
*/
