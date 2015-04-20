package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 * Created by saftophobia on 4/20/15.
 */
public class Appartment extends Estate{

    private int appartment_id = -1;
    private int floor;
    private int rent;
    private int rooms;
    private boolean balcony;
    private boolean kitchen;
    private int Estate_ID;

    public Appartment() { super();}

    public static Appartment load(int id){
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM House WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Appartment ts = new Appartment();
                ts.setAppartment_id(id);
                ts.setFloor(rs.getInt("floor"));
                ts.setRent(rs.getInt("price"));
                ts.setRooms(rs.getInt("garden"));
                ts.setBalcony(rs.getBoolean("garden"));
                ts.setKitchen(rs.getBoolean("garden"));

                ts.setEstate_ID(rs.getInt("estate_id"));

                //get Associated Contract and load the data
                Estate ms = Estate.load(ts.getEstate_ID());
                ts.setCity(ms.getCity());
                ts.setPostalCode(ms.getPostalCode());
                ts.setStreet(ms.getStreet());
                ts.setStreetNumber(ms.getStreetNumber());
                ts.setSquareArea(ms.getSquareArea());

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
    public void save(){
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try {

            if (getId() == -1) {

                //saved new contract
                Estate c= new Estate(this.getCity(),this.getPostalCode(),this.getStreet(), this.getStreetNumber(), this.getSquareArea());
                this.setEstate_ID(c.getId());
                c.save();

                String insertSQL = "INSERT INTO Appartment(floor, rent, rooms, balcony, kitchen, Estate_id) VALUES ( ?, ?, ? , ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getRent());
                pstmt.setInt(3, getRooms());
                pstmt.setBoolean(1, isBalcony());
                pstmt.setBoolean(2, isKitchen());

                pstmt.setInt(4, getEstate_ID());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE Appartment SET floor = ?, rent = ?, rooms = ? ,balcony = ?, kitchen =?, Estate_id = ?  WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getFloor());
                pstmt.setInt(2, getRent());
                pstmt.setInt(3, getRooms());
                pstmt.setBoolean(4,isBalcony());
                pstmt.setBoolean(5,isKitchen());
                pstmt.setInt(6, getEstate_ID());

                pstmt.setInt(7, getId());
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getAppartment_id() {
        return appartment_id;
    }

    public void setAppartment_id(int appartment_id) {
        this.appartment_id = appartment_id;
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
