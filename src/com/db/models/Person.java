package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 CREATE TABLE Person(
 ID int NOT NULL AUTO_INCREMENT,
 FIRST_NAME varchar(50),
 NAME varchar(255),
 ADDRESS varchar(255),
 PRIMARY KEY (ID));
 * Created by saftophobia on 4/13/15.
 */
public class Person {
    private int id = -1 ;
    private String FIRST_NAME;
    private String Name;
    private String Address;


    public Person() {super();}

    public Person(String FIRST_NAME, String name, String address) {
        this.FIRST_NAME = FIRST_NAME;
        Name = name;
        Address = address;
    }

    public static Person load(int id) {
        try {
            // Hole Verbindung
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM Person WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Person ts = new Person();
                ts.setId(id);
                ts.setFirstName(rs.getString("FIRST_NAME"));
                ts.setName(rs.getString("name"));
                ts.setAddress(rs.getString("address"));

                rs.close();
                pstmt.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }






    public boolean save() {
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try {

            if (getId() == -1) {
                String insertSQL = "INSERT INTO Person(FIRST_NAME, Name, Address) VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, getFirstName());
                pstmt.setString(2, getName());
                pstmt.setString(3, getAddress());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
                return true;
            } else {
                String updateSQL = "UPDATE Person SET First_name = ?, Name = ?, Address = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setString(1, getFirstName());
                pstmt.setString(2, getName());
                pstmt.setString(3, getAddress());

                pstmt.setInt(4, getId());
                pstmt.executeUpdate();

                pstmt.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //setters and getters
    public String getFirstName() {
        return FIRST_NAME;
    }

    public void setFirstName(String firstName) {
        FIRST_NAME = firstName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
