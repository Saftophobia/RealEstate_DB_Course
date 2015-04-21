package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 ID int NOT NULL,
 Person_ID int NOT NULL,
 Estate_ID int NOT NULL,
 #Contract_ID int NOT NULL,
 PRIMARY KEY (ID),
 FOREIGN KEY (Person_ID) REFERENCES Person(ID)
 ON DELETE CASCADE,
 FOREIGN KEY (Estate_ID) REFERENCES Estate(ID)
 ON DELETE CASCADE
 * Created by saftophobia on 4/21/15.
 */
public class PersonEstateRelation {
    private int id = -1 ;
    private int Person_ID;
    private int  Estate_ID;

    public PersonEstateRelation() {}

    public PersonEstateRelation(int estate_ID, int person_ID) {
        Estate_ID = estate_ID;
        Person_ID = person_ID;
    }

    public static PersonEstateRelation load(int id) {
        try {
            // Hole Verbindung
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM PersonEstateRelation WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PersonEstateRelation ts = new PersonEstateRelation();
                ts.setId(id);
                ts.setPerson_ID(rs.getInt("Person_ID"));
                ts.setEstate_ID(rs.getInt("Estate_ID"));

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
                String insertSQL = "INSERT INTO PersonEstateRelation(Person_ID, Estate_ID) VALUES (?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getPerson_ID());
                pstmt.setInt(2, getEstate_ID());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
                return true;
            } else {
                String updateSQL = "UPDATE PersonEstateRelation SET Person_ID = ?, Estate_ID = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getPerson_ID());
                pstmt.setInt(2, getEstate_ID());

                pstmt.setInt(3, getId());
                pstmt.executeUpdate();

                pstmt.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_ID() {
        return Person_ID;
    }

    public void setPerson_ID(int person_ID) {
        Person_ID = person_ID;
    }

    public int getEstate_ID() {
        return Estate_ID;
    }

    public void setEstate_ID(int estate_ID) {
        Estate_ID = estate_ID;
    }
}
