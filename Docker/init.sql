create database cars_test;
\c cars_test;
create table cars(id serial primary key, number text unique, brand text, model text, color text, year int, avtocod_link text, autoru_link text, active bool default true, created bigint);
-- create database cars_db;
\c cars_db;
create table cars(id serial primary key, number text unique, brand text, model text, color text, year int, avtocod_link text, autoru_link text, active bool default true, created bigint);


