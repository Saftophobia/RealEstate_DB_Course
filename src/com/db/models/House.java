package com.db.models;

import com.db.com.db.controllers.Controller;
import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 ID int NOT NULL ,
 FLOORS int,
 PRICE int,
 GARDEN boolean,
 Estate_ID int,
 PRIMARY KEY (ID),
 FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
 ON DELETE CASCADE
* Created by saftophobia on 4/20/15.
*/
public class House extends Estate {

    private int house_id = -1;
    private int floors;
    private int price;
    private boolean garden;
    private int Estate_ID;

    public House(){ super();}

    public House(String city, int postalCode, String street, int streetNumber, int squareArea, int EstateAgent_ID, int floors, int price, boolean garden, int estate_ID) {
        super(city, postalCode, street, streetNumber, squareArea, EstateAgent_ID);
        this.floors = floors;
        this.price = price;
        this.garden = garden;
        Estate_ID = estate_ID;
    }

    public static void index(){
        try{
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,\n" +
                    "SQUARE_AREA ,FLOORS ,PRICE ,GARDEN \n" +
                    "from Estate inner join House\n" +
                    "where Estate.ID=House.Estate_ID\n";

            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + "\t City: " + rs.getString("City") + "\t Postal Code: " + rs.getInt("postal_code") + "\t Price: " + rs.getInt("price"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static int returnID(int parent_id)
    {
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "SELECT * FROM House WHERE estate_id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, parent_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static House load(int id){
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM House WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                House ts = new House();
                ts.setHouse_id(id);
                ts.setFloors(rs.getInt("floors"));
                ts.setPrice(rs.getInt("price"));
                ts.setGarden(rs.getBoolean("garden"));
                ts.setEstate_ID(rs.getInt("Estate_ID"));

                //get Associated Contract and load the data
                Estate ms = Estate.load(ts.getEstate_ID());
                ts.setId(ms.getId());
                ts.setCity(ms.getCity());
                ts.setPostalCode(ms.getPostalCode());
                ts.setStreet(ms.getStreet());
                ts.setStreetNumber(ms.getStreetNumber());
                ts.setSquareArea(ms.getSquareArea());
                ts.setEstateAgent_ID(ms.getEstateAgent_ID());

                rs.close();
                pstmt.close();

                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean save(){
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();
        boolean saved = false;
        Estate c = null;
        try {

            if (getHouse_id() == -1) {

                //saved new contract
                c= new Estate(this.getCity(),this.getPostalCode(),this.getStreet(), this.getStreetNumber(), this.getSquareArea(), this.getEstateAgent_ID());
                saved = c.save();
                this.setEstate_ID(c.getId());


                String insertSQL = "INSERT INTO House(floors, price, garden, Estate_ID) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getFloors());
                pstmt.setInt(2, getPrice());
                pstmt.setBoolean(3, isGarden());

                System.out.println(getEstate_ID());

                pstmt.setInt(4, getEstate_ID());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                c = Estate.load(getEstate_ID());
                super.save();

                String updateSQL = "UPDATE House SET floors = ?, price = ?, garden = ? ,estate_id = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getFloors());
                pstmt.setInt(2, getPrice());
                pstmt.setBoolean(3, isGarden());
                pstmt.setInt(4, getEstate_ID());
                pstmt.setInt(5, getHouse_id());
                pstmt.executeUpdate();


                pstmt.close();
            }
        } catch (SQLException e) {
            if (saved) {
                Controller.delete("Estate", c.getId());
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }











    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public int getEstate_ID() {
        return Estate_ID;
    }

    public void setEstate_ID(int estate_ID) {
        Estate_ID = estate_ID;
    }
}
