INSERT INTO EstateAgent(NAME ,ADDRESS , LOGIN ,PASSWORD )
values ('Ahmed Safty', 'Gotham', 'Safty', 'hackable');

INSERT INTO EstateAgent(NAME ,ADDRESS , LOGIN ,PASSWORD )
values ('Ali Saleh', 'Kripton', 'Ali', 'crackable');

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Hamburg','11242','Hamburg Main street','16','215',1);

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Berlin','11242','Berlin Main street','16','215',2);

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Stutgart','11242','Stutgart Main street','16','215',1);

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Hamburg','1112','Hamburg Main street','18','218',2);

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Achen','1112','Achen Main street','18','218',2);

INSERT INTO Estate(CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,SQUARE_AREA ,EstateAgent_ID )
values ('Munich','1112','Munich Main street','18','218',1);

INSERT INTO House (FLOORS ,PRICE ,GARDEN ,Estate_ID )
values (2,750000,TRUE,1);

INSERT INTO House (FLOORS ,PRICE ,GARDEN ,Estate_ID )
values (4,6500,TRUE,2);

INSERT INTO House (FLOORS ,PRICE ,GARDEN ,Estate_ID )
values (3,850000,TRUE,3);

INSERT INTO Apartment (FLOOR,RENT,ROOMS,BALCONY,KITCHEN, Estate_ID)
VALUES (12,70,5,FALSE,TRUE,4);

INSERT INTO Apartment (FLOOR,RENT,ROOMS,BALCONY,KITCHEN, Estate_ID)
VALUES (9,7500,5,FALSE,TRUE,5);

INSERT INTO Apartment (FLOOR,RENT,ROOMS,BALCONY,KITCHEN, Estate_ID)
VALUES (20,7510,5,FALSE,TRUE,6);

INSERT INTO Person (FIRST_NAME ,NAME ,ADDRESS)
Values ('Ali','Saleh','Borgfelder st, 16');

INSERT INTO Person (FIRST_NAME ,NAME ,ADDRESS)
Values ('Ahmed','Saad','Borgfelder st, 16');

INSERT INTO PersonEstateRelation (Person_ID ,Estate_ID )
VALUES (1,1);

INSERT INTO PersonEstateRelation(Person_ID ,Estate_ID )
VALUES (2,4);

INSERT INTO Contract (CONTRACT_NUMBER,DATE ,PLACE ,PE_ID)
VALUES (1231,NOW(),'ST petrisburg',1);

INSERT INTO Contract(CONTRACT_NUMBER,DATE ,PLACE ,PE_ID)
VALUES (1232,NOW(),'ST petrisburg',2);

INSERT INTO PurchaseContract (INSTALLMENTS, INTEREST_RATE , Contract_ID)
VALUES(1,12,1);

INSERT INTO TenancyContract(START_DATE,DURATION ,ADD_COSTS,Contract_ID )
VALUES (NOW(),2,20,2);