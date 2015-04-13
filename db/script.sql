drop database RealStates;

create database RealStates;

use RealStates;

CREATE TABLE EstateAgent(
	ID int NOT NULL AUTO_INCREMENT,
	NAME varchar(255),
	ADDRESS varchar(255),
	LOGIN varchar(5),
	PASSWORD varchar(50),
	PRIMARY KEY (ID)
	);	

CREATE TABLE Estate(
	ID int NOT NULL AUTO_INCREMENT,
	CITY varchar(50),
	POSTAL_CODE char(5),
	STREET varchar(255),
	STREET_NUMBER char(5),
	SQUARE_AREA int,
	EstateAgent_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (EstateAgent_ID) REFERENCES EstateAgent(ID)
	);

CREATE TABLE House(
	ID int NOT NULL AUTO_INCREMENT,
	FLOORS int,
	PRICE int,
	GARDEN boolean,
	PRIMARY KEY (ID)
);

CREATE TABLE Apartment(
	ID int NOT NULL AUTO_INCREMENT,
	FLOOR int,
	RENT int,
	ROOMS int,
	BALCONY boolean,
	KITCHEN boolean,
	PRIMARY KEY (ID)
);

CREATE TABLE Person(
	ID int NOT NULL AUTO_INCREMENT,
	FIRST_NAME varchar(50),
	NAME varchar(255),
	ADDRESS varchar(255),
	PRIMARY KEY (ID)
);


CREATE TABLE PurchaseContract(
	ID int NOT NULL AUTO_INCREMENT,
	INSTALLEMENTS int,
	INTEREST_RATE float,
	PRIMARY KEY (ID)
);


CREATE TABLE TenancyContract(
	ID int NOT NULL AUTO_INCREMENT,
	START_DATE DATETIME,
	DURATION int,
	ADD_COSTS int,
	PRIMARY KEY (ID)
);

CREATE TABLE Contract(
	ID int NOT NULL AUTO_INCREMENT,
	CONTRACT_NUMBER int,
	DATE DATETIME ,
	PLACE varchar(255),
	PRIMARY KEY (ID)
);




