INSERT INTO EstateAgent
values (1,'Ahmed Safty', 'Stellingon', 'Safty', 'hackable');

INSERT INTO Estate
values (1,'Hamburg','11242','Hamburg Main street','16','215',1);

INSERT INTO Estate
values (2,'Hamburg','1112','Hamburg Main street','18','218',1);

INSERT INTO House
values (1,2,750000,TRUE,1);

INSERT INTO Apartment
VALUES (1,12,750,5,FALSE,TRUE,2);

INSERT INTO Person
Values (1,'Ali','Saleh','Borgfelder st, 16');

INSERT INTO Person
Values (2,'Ahmed','Saad','Borgfelder st, 16');

INSERT INTO PersonEstateRelation
VALUES (1,1,1);

INSERT INTO PersonEstateRelation
VALUES (2,2,2);

INSERT INTO Contract
VALUES (1,1231,NOW(),'ST petrisburg',1);

INSERT INTO Contract
VALUES (2,1232,NOW(),'ST petrisburg',2);

INSERT INTO PurchaseContract
VALUES(1,1,12,1);

INSERT INTO TenancyContract
VALUES (1,NOW(),2,20,2);
