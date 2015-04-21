package com.db.models;

import com.db.com.db.controllers.Controller;
import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 ID int NOT NULL ,
 FLOOR int,
 RENT int,
 ROOMS int,
 BALCONY boolean,
 KITCHEN boolean,
 Estate_ID int,
 PRIMARY KEY (ID),
 FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
 ON DELETE CASCADE
 * Created by saftophobia on 4/20/15.
 */
public class Apartment extends Estate{

    private int apartment_id = -1;
    private int floor;
    private int rent;
    private int rooms;
    private boolean balcony;
    private boolean kitchen;
    private int Estate_ID;

    public Apartment() { super();}

    public Apartment(String city, int postalCode, String street, int streetNumber, int squareArea, int EstateAgent_ID, int floor, int rent, int rooms, boolean balcony, boolean kitchen, int estate_ID) {
        super(city, postalCode, street, streetNumber, squareArea, EstateAgent_ID);
        this.floor = floor;
        this.rent = rent;
        this.rooms = rooms;
        this.balcony = balcony;
        this.kitchen = kitchen;
        Estate_ID = estate_ID;
    }

    public static void index(){
        try{
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "Select Estate.ID,CITY ,POSTAL_CODE ,STREET,STREET_NUMBER,\n" +
                    "SQUARE_AREA ,FLOOR,RENT,ROOMS,BALCONY,KITCHEN\n" +
                    "from Estate inner join Apartment\n" +
                    "where Estate.ID=Apartment.Estate_ID";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + "\t City: " + rs.getString("City") + "\t Postal Code: " + rs.getInt("postal_code") + "\t Rent: " + rs.getInt("rent"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static int returnID(int parent_id)
    {
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "SELECT * FROM Apartment WHERE estate_id = ?";
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


    public static Apartment load(int id){
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM Apartment WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Apartment ts = new Apartment();
                ts.setApartment_id(id);
                ts.setFloor(rs.getInt("floor"));
                ts.setRent(rs.getInt("rent"));
                ts.setRooms(rs.getInt("rooms"));
                ts.setBalcony(rs.getBoolean("balcony"));
                ts.setKitchen(rs.getBoolean("kitchen"));

                ts.setEstate_ID(rs.getInt("estate_id"));

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

            if (getId() == -1) {

                //saved new contract
                c = new Estate(this.getCity(),this.getPostalCode(),this.getStreet(), this.getStreetNumber(), this.getSquareArea(),this.getEstateAgent_ID());
                saved = c.save();
                this.setEstate_ID(c.getId());

                String insertSQL = "INSERT INTO Apartment(floor, rent, rooms, balcony, kitchen, Estate_id) VALUES ( ?, ?, ? , ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getRent());
                pstmt.setInt(3, getRooms());
                pstmt.setBoolean(4, isBalcony());
                pstmt.setBoolean(5, isKitchen());

                pstmt.setInt(6, getEstate_ID());
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

                String updateSQL = "UPDATE Apartment SET floor = ?, rent = ?, rooms = ? ,balcony = ?, kitchen =?, Estate_id = ?  WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getRent());
                pstmt.setInt(3, getRooms());
                pstmt.setBoolean(4, isBalcony());
                pstmt.setBoolean(5,isKitchen());
                pstmt.setInt(6, getEstate_ID());

                pstmt.setInt(7, getApartment_id());
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

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public int getEstate_ID() {
        return Estate_ID;
    }

    public void setEstate_ID(int estate_ID) {
        Estate_ID = estate_ID;
    }
}
