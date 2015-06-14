package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by saftophobia on 6/10/15.
 */
public class DBConnectionManager {
    // instance of Driver Manager
    private static DBConnectionManager _instance = null;

    // DB2 connection
    private Connection _con;

    /**
     * Erzeugt eine Datenbank-Verbindung
     */
    private DBConnectionManager(String db) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("blah");
            _con = DriverManager.getConnection("jdbc:mysql://localhost/persistance_db", "root", "root");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Liefert Instanz des Managers
     *
     * @return DB2ConnectionManager
     */
    public static DBConnectionManager getInstance(String db) {
        if (_instance == null) {
            _instance = new DBConnectionManager(db);
        }
        return _instance;
    }

    /**
     * Liefert eine Verbindung zur DB2 zurC<ck
     *
     * @return Connection
     */
    public Connection getConnection() {
        return _con;
    }

}
