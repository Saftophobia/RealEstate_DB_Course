package main;

import db.DBConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by saftophobia on 6/10/15.
 */
public class PersistanceManager {
    static Hashtable<UUID,Hashtable<Integer,String>> Buffer = new Hashtable<>();


    public static UUID beginTransaction(){
        UUID transaction_id =   UUID.randomUUID();
        LogManager.writelog(-1,transaction_id,"newtransaction",null);

        return transaction_id;
    }

    public static synchronized void commitPushDB(UUID transaction_id){
        LogManager.writelog(-1,transaction_id,"COMMIT",null);
        Collection<String> results = Buffer.get(transaction_id).values();

        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();

            for (String data : results) {
                String insertSQL = "INSERT INTO persistance_table(UID, data) VALUES ('" + transaction_id.toString() + "','" + data + "')";
                //String insertSQL = "INSERT INTO persistance_table(UID, data) VALUES ('asd','asd');";

                stmt.addBatch(insertSQL);
            }
            stmt.executeBatch();
            con.commit();
            stmt.close();
            //con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        LogManager.writelog(-1,transaction_id,"PUSHED",null);
        LogManager.deletelog(transaction_id);
        Buffer.remove(transaction_id);
    }

    public static synchronized void write(UUID transaction_id, int pageID,String Data, boolean log){
        //if uid exists, add Data to pageID, else, create new page with the Data
        if(!Buffer.containsKey(transaction_id)){
            Buffer.put(transaction_id, new Hashtable<Integer, String>());
        }
        if(log) {
            LogManager.writelog(pageID, transaction_id, "WRITE", Data);
        }
        Buffer.get(transaction_id).put(pageID, Data);
    }
}
