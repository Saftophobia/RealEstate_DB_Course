Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,
SQUARE_AREA ,EstateAgent_ID,FLOORS ,PRICE ,GARDEN 
from Estate inner join House
where Estate.ID=House.Estate_ID


