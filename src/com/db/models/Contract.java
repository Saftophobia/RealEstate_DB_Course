package com.db.models;


import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 *CREATE TABLE Contract(
 *ID int NOT NULL AUTO_INCREMENT,
 *CONTRACT_NUMBER int,
 *DATE DATETIME ,
 *PLACE varchar(255),
 *PRIMARY KEY (ID));
 * Created by saftophobia on 4/20/15.
 */
public class Contract {
    private int id = -1 ;
    private int contract_number;
    private Date date;
    private String place;
    private int PersonEstateID;

    public Contract(){}

    public Contract(int contract_number, Date date, String place, int PersonEstateID){
        this.contract_number = contract_number;
        this.date = date;
        this.place = place;
        this.PersonEstateID = PersonEstateID;
    }

    public static Contract load(int id) {
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM Contract WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Contract ts = new Contract();
                ts.setId(id);
                ts.setContract_number(rs.getInt("contract_number"));
                ts.setDate(rs.getDate("date"));
                ts.setPlace(rs.getString("place"));

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
                String insertSQL = "INSERT INTO Contract(contract_number, date, place, PE_ID) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getContract_number());
                pstmt.setDate(2, getDate());
                pstmt.setString(3, getPlace());
                pstmt.setInt(4, getPersonEstateID());

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE Contract SET contract_number = ?, date = ?, place = ?, PE_ID = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getContract_number());
                pstmt.setDate(2, getDate());
                pstmt.setString(3, getPlace());
                pstmt.setInt(4, getPersonEstateID());


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

    public void setId(int id) {
        this.id = id;
    }




    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getContract_number() {
        return contract_number;
    }

    public void setContract_number(int contract_number) {
        this.contract_number = contract_number;
    }

    public int getPersonEstateID() {
        return PersonEstateID;
    }

    public void setPersonEstateID(int personEstateID) {
        PersonEstateID = personEstateID;
    }
}
