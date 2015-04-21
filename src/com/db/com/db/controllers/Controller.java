package com.db.com.db.controllers;

import com.db.conn.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by saftophobia on 4/21/15.
 */
public class Controller {

    public static boolean delete(String TableName, int id){
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try{
            String deleteSQL = "DELETE FROM " + TableName + " WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteSQL);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
            return true;
        }catch(SQLException e)
        {
            return false;
        }

    }

}
