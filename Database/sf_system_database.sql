create database if not exists sf_system_database;
use sf_system_database;

create table Users
(
	UserID varchar(62) not null unique,
    FirstName varchar(50) not null,
    LastName varchar(50) not null,
    Password char(32) not null,
    primary key(UserID)
);

create table Products
(
	ProductID int not null unique auto_increment,
    UserID varchar(62) not null,
    ProductName varchar(95) not null,
    foreign key(UserID) references Users(UserID) on update cascade on delete cascade,
    primary key(ProductID)
);