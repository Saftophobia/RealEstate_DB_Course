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
}
