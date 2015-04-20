package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by saftophobia on 4/20/15.
 */
public class EntityAgent {
    private int id = -1 ;
    private String name;
    private String address;
    private String login;
    private String password;


    public static EntityAgent load(int id) {
        try {
            // Hole Verbindung
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM entityagent WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                EntityAgent ts = new EntityAgent();
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
