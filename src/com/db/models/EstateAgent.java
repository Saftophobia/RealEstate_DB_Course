package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 CREATE TABLE EstateAgent(
 ID int NOT NULL ,
 NAME varchar(255),
 ADDRESS varchar(255),
 LOGIN varchar(5),
 PASSWORD varchar(50),
 PRIMARY KEY (ID),
 CONSTRAINT EA_ULoginName UNIQUE (LOGIN)
 );	# MySQL returned an empty result set (i.e. zero rows).
 *
 * Created by saftophobia on 4/20/15.
 */
public class EstateAgent {
    private int id = -1 ;
    private String name;
    private String address;
    private String login;
    private String password;

    public EstateAgent() {}

    public EstateAgent(String name, String address, String login, String password) {
        this.name = name;
        this.address = address;
        this.login = login;
        this.password = password;
    }
    public void CreateHouse(String city, int postalCode, String street, int streetNumber, int squareArea, int floors, int price, boolean garden)
    {
        House h = new House(city,postalCode,street,streetNumber,squareArea,this.getId(),floors,price,garden, -1);
        h.save();
    }

    public void CreateApartment(String city, int postalCode, String street, int streetNumber, int squareArea,int floor, int rent, int rooms, boolean balcony, boolean kitchen ){
        Apartment ap = new Apartment(city,postalCode,street,streetNumber,squareArea,this.getId(),floor,rent,rooms,balcony,kitchen, -1);
        ap.save();
    }


    public static EstateAgent load(int id) {
        try {
            // Hole Verbindung
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM EstateAgent WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                EstateAgent ts = new EstateAgent();
                ts.setId(id);
                ts.setName(rs.getString("name"));
                ts.setAddress(rs.getString("address"));
                ts.setLogin(rs.getString("login"));
                ts.setPassword(rs.getString("password"));

                rs.close();
                pstmt.close();
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EstateAgent load(String username, String password) throws SQLException {

        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        // Erzeuge Anfrage
        String selectSQL = "SELECT * FROM EstateAgent WHERE login = ? AND password = ?";
        PreparedStatement pstmt = con.prepareStatement(selectSQL);
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        // Führe Anfrage aus
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            EstateAgent ts = new EstateAgent();
            ts.setId(rs.getInt("ID"));
            ts.setName(rs.getString("name"));
            ts.setAddress(rs.getString("address"));
            ts.setLogin(username);
            ts.setPassword(password);

            rs.close();
            pstmt.close();
            return ts;
        }else{
            throw new SQLException();
        }
    }



    public void save() {
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try {

            if (getId() == -1) {
                String insertSQL = "INSERT INTO EstateAgent(name, address, login, password) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, getName());
                pstmt.setString(2, getAddress());
                pstmt.setString(3, getLogin());
                pstmt.setString(4, getPassword());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE EstateAgent SET name = ?, address = ?, login = ?, password = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setString(1, getName());
                pstmt.setString(2, getAddress());
                pstmt.setString(3, getLogin());
                pstmt.setString(4, getPassword());
                pstmt.setInt(5, getId());
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

    public void setId(int Id) {
        this.id = Id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void index(){
        try{
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "Select * from EstateAgent";

            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + " Name:" + rs.getString("Name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
