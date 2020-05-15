/* 
Initialization script that initializes the 'librarydev' database 
with proper tables and links for v0.3 of the Library Server Data Model.  
*/ 

-- Clear existing tables if they exist.
drop table if exists book_authors;
drop table if exists author;
drop table if exists book_tags;
drop table if exists checkout;
drop table if exists copy;
drop table if exists book;
drop table if exists library_account;
drop table if exists role;
drop table if exists shelf;
drop table if exists tag;
drop table if exists type;

-- Create new tables.
create table if not exists author
(
	id bigint generated by default as identity
		constraint author_pkey
			primary key,
	first_name varchar(255),
	last_name varchar(255)
);

alter table author owner to rssab;

create table if not exists role
(
	id bigint generated by default as identity
		constraint role_pkey
			primary key,
	name varchar(32)
);

alter table role owner to rssab;

create table if not exists library_account
(
	id bigint generated by default as identity
		constraint library_account_pkey
			primary key,
	first_name varchar(255),
	last_name varchar(255),
	nuid varchar(8)
		constraint uk_7jlvuro05hebu2heq8ib3685c
			unique,
	pin varchar(60),
	role_id bigint not null
		constraint fkkuw3c0436e5h4xa8umho0poo7
			references role
);

alter table library_account owner to rssab;

create table if not exists shelf
(
	id bigint generated by default as identity
		constraint shelf_pkey
			primary key,
	number integer
);

alter table shelf owner to rssab;

create table if not exists tag
(
	id bigint generated by default as identity
		constraint tag_pkey
			primary key,
	name varchar(255)
);

alter table tag owner to rssab;

create table if not exists type
(
	id bigint generated by default as identity
		constraint type_pkey
			primary key,
	name varchar(64)
);

alter table type owner to rssab;

create table if not exists book
(
	id bigint generated by default as identity
		constraint book_pkey
			primary key,
	cover_image oid,
	cover_image_mime_type varchar(255),
	edition integer,
	isbn varchar(64),
	publish_date date,
	subtitle varchar(255),
	title varchar(255),
	type_id bigint
		constraint fk5s66k3ve9t4i1r9c1ebd37j3p
			references type
);

alter table book owner to rssab;

create table if not exists book_authors
(
	book_id bigint not null
		constraint fks4xm7q8i3uxvaiswj1c35nnxw
			references book,
	authors_id bigint not null
		constraint fk551i3sllw1wj7ex6nir16blsm
			references author
);

alter table book_authors owner to rssab;

create table if not exists book_tags
(
	books_id bigint not null
		constraint fkmolnjthi6racnguu0nhrwx5iw
			references book,
	tags_id bigint not null
		constraint fksky6wumpk8q486i2lecduct0d
			references tag
);

alter table book_tags owner to rssab;

create table if not exists copy
(
	id bigint generated by default as identity
		constraint copy_pkey
			primary key,
	acquisition_date date,
	barcode varchar(255),
	book_id bigint not null
		constraint fkof5k7k6c41i06j6fj3slgsmam
			references book,
	location_id bigint
		constraint fk1umjtww7oa1ldor0oq8m256u7
			references shelf
);

alter table copy owner to rssab;

create table if not exists checkout
(
	id bigint generated by default as identity
		constraint checkout_pkey
			primary key,
	checkin_date timestamp,
	checkout_date timestamp,
	due_date timestamp,
	copy_id bigint
		constraint fkgqhcghmpj5esdpslxsnc249dy
			references copy,
	recipient_id bigint
		constraint fk7bw54mcuxejcjsnaiox14olfd
			references library_account
);

alter table checkout owner to rssab;

