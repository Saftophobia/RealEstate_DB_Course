package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 ID int NOT NULL ,
 CITY varchar(50),
 POSTAL_CODE char(5),
 STREET varchar(255),
 STREET_NUMBER char(5),
 SQUARE_AREA int,
 EstateAgent_ID int,
 PRIMARY KEY (ID),
 FOREIGN KEY (EstateAgent_ID) REFERENCES EstateAgent(ID)
 ON DELETE CASCADE
 * Created by saftophobia on 4/20/15.
 */
public class Estate {
    private int id = -1;
    private String city;
    private int postalCode;
    private String street;
    private int streetNumber;
    private int squareArea;
    private int EstateAgent_ID;

    public Estate(){}

    public Estate(String city, int postalCode, String street, int streetNumber, int squareArea, int EstateAgent_ID) {
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.squareArea = squareArea;
        this.EstateAgent_ID = EstateAgent_ID;
    }


    public static Estate load(int id) {
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM Estate WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Estate ts = new Estate();
                ts.setId(id);
                ts.setCity(rs.getString("city"));
                ts.setPostalCode(rs.getInt("postal_Code"));
                ts.setStreet(rs.getString("street"));
                ts.setStreetNumber(rs.getInt("street_Number"));
                ts.setSquareArea(rs.getInt("square_Area"));
                ts.setEstateAgent_ID(rs.getInt("EstateAgent_ID"));

                rs.close();
                pstmt.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void save() {
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try {

            if (getId() == -1) {
                String insertSQL = "INSERT INTO Estate(city, postal_Code, street, street_Number, square_Area, EstateAgent_ID) VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, getCity());
                pstmt.setInt(2, getPostalCode());
                pstmt.setString(3, getStreet());
                pstmt.setInt(4, getStreetNumber());
                pstmt.setInt(5, getSquareArea());
                pstmt.setInt(6, getEstateAgent_ID());


                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE Estate SET city = ?, postal_Code = ?, street = ?, street_Number= ?, square_Area = ?, EstateAgent_ID = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setString(1, getCity());
                pstmt.setInt(2, getPostalCode());
                pstmt.setString(3, getStreet());
                pstmt.setInt(4, getStreetNumber());
                pstmt.setInt(5, getSquareArea());
                pstmt.setInt(6, getEstateAgent_ID());


                pstmt.setInt(7, getId());
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getSquareArea() {
        return squareArea;
    }

    public void setSquareArea(int squareArea) {
        this.squareArea = squareArea;
    }

    public int getEstateAgent_ID() {
        return EstateAgent_ID;
    }

    public void setEstateAgent_ID(int estateAgent_ID) {
        EstateAgent_ID = estateAgent_ID;
    }
}
