drop database RealStates;

create database RealStates;

use RealStates;# MySQL returned an empty result set (i.e. zero rows).


CREATE TABLE EstateAgent(
	ID int NOT NULL AUTO_INCREMENT,
	NAME varchar(255),
	ADDRESS varchar(255),
	LOGIN varchar(5),
	PASSWORD varchar(50),
	PRIMARY KEY (ID),
	CONSTRAINT EA_ULoginName UNIQUE (LOGIN)
	);	# MySQL returned an empty result set (i.e. zero rows).

CREATE TABLE Estate(
	ID int NOT NULL  AUTO_INCREMENT,
	CITY varchar(50),
	POSTAL_CODE char(5),
	STREET varchar(255),
	STREET_NUMBER char(5),
	SQUARE_AREA int,
	EstateAgent_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (EstateAgent_ID) REFERENCES EstateAgent(ID)
	ON DELETE CASCADE
	);# MySQL returned an empty result set (i.e. zero rows).

CREATE TABLE House(
	ID int NOT NULL  AUTO_INCREMENT,
	FLOORS int,
	PRICE int,
	GARDEN boolean,
	Estate_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
	ON DELETE CASCADE
);# MySQL returned an empty result set (i.e. zero rows).


CREATE TABLE Apartment(
	ID int NOT NULL  AUTO_INCREMENT,
	FLOOR int,
	RENT int,
	ROOMS int,
	BALCONY boolean,
	KITCHEN boolean,
    Estate_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
	ON DELETE CASCADE
);# MySQL returned an empty result set (i.e. zero rows).


CREATE TABLE Person(
	ID int NOT NULL  AUTO_INCREMENT,
	FIRST_NAME varchar(50),
	NAME varchar(255),
	ADDRESS varchar(255),
	PRIMARY KEY (ID)
);# MySQL returned an empty result set (i.e. zero rows).

#CREATE TABLE PersonEstateContractRelation(
CREATE TABLE PersonEstateRelation(

	ID int NOT NULL AUTO_INCREMENT,
	Person_ID int NOT NULL,
	Estate_ID int NOT NULL,
	#Contract_ID int NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (Person_ID) REFERENCES Person(ID)
	ON DELETE CASCADE,
	FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
	ON DELETE CASCADE,
	CONSTRAINT PE_Estate UNIQUE (Estate_ID)
	#FOREIGN KEY (Contract_ID) REFERENCES Contract(ID)
);

CREATE TABLE Contract(

	ID int NOT NULL  AUTO_INCREMENT,
	CONTRACT_NUMBER int,
	DATE DATETIME,
	PLACE varchar(255),
	PE_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (PE_ID) REFERENCES PersonEstateRelation(ID)	ON DELETE CASCADE
);# MySQL returned an empty result set (i.e. zero rows).

CREATE TABLE PurchaseContract(
	ID int NOT NULL  AUTO_INCREMENT,
	INSTALLMENTS int,
	INTEREST_RATE float,
    Contract_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (Contract_ID) REFERENCES Contract(ID) ON DELETE CASCADE
);# MySQL returned an empty result set (i.e. zero rows).

CREATE TABLE TenancyContract(
	ID int NOT NULL  AUTO_INCREMENT,
	START_DATE DATETIME,
	DURATION int,
	ADD_COSTS int,
    Contract_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (Contract_ID) REFERENCES Contract(ID)
	ON DELETE CASCADE
);# MySQL returned an empty result set (i.e. zero rows).

#DELIMITER $$
#CREATE TRIGGER contract_delete AFTER DELETE on PersonEstateContractRelation
#FOR EACH ROW BEGIN
#DELETE FROM Contract
#WHERE Contract.ID = OLD.Contract_ID;
#end$$
#DELIMITER ;