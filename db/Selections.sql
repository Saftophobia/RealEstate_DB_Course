Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,
SQUARE_AREA ,FLOORS ,PRICE ,GARDEN 
from Estate inner join House
where Estate.ID=House.Estate_ID
And EstateAgent_ID = ?


Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,
SQUARE_AREA ,FLOOR,RENT,ROOMS,BALCONY,KITCHEN
from Estate inner join Apartment
where Estate.ID=Apartment.Estate_ID
And EstateAgent_ID = ?

SELECT CONTRACT_NUMBER ,DATE, PLACE ,INSTALLMENTS ,INTEREST_RATE ,FIRST_NAME ,
	NAME ,CITY ,POSTAL_CODE ,STREET ,STREET_NUMBER ,SQUARE_AREA 
from Contract inner join PurchaseContract on Contract_ID = Contract.ID
inner join PersonEstateRelation on PersonEstateRelation.ID = PE_ID
inner join Person on Person.ID = Person_ID
inner join Estate on Estate.ID = PersonEstateRelation.Estate_ID
And EstateAgent_ID = ?

SELECT CONTRACT_NUMBER ,DATE, PLACE ,START_DATE ,DURATION ,
	ADD_COSTS ,FIRST_NAME ,	NAME ,CITY ,POSTAL_CODE ,STREET ,STREET_NUMBER ,SQUARE_AREA 
from Contract inner join TenancyContract on Contract_ID = Contract.ID
inner join PersonEstateRelation on PersonEstateRelation.ID = PE_ID
inner join Person on Person.ID = Person_ID
inner join Estate on Estate.ID = PersonEstateRelation.Estate_ID
And EstateAgent_ID = ?
