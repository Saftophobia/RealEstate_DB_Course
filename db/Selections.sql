Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,
SQUARE_AREA ,FLOORS ,PRICE ,GARDEN 
from Estate inner join House
where Estate.ID=House.Estate_ID


Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,
SQUARE_AREA ,FLOOR,RENT,ROOMS,BALCONY,KITCHEN
from Estate inner join House
where Estate.ID=House.Estate_ID


