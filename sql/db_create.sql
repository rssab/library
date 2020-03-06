/* 
Creation Script for the 'librarydev' database that creates the database
and development user 'rssab' with password 'rssab'
*/

create database librarydev; 
create user rssab with encrypted password 'rssab'; 
grant all privileges on database librarydev to rssab; 