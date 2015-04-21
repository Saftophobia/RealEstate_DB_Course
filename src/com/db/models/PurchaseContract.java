package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 CREATE TABLE PurchaseContract(
 ID int NOT NULL AUTO_INCREMENT,
 INSTALLMENTS int,
 INTEREST_RATE float,
 Contract_ID int,
 PRIMARY KEY (ID),
 FOREIGN KEY (Contract_ID) REFERENCES Contract(ID)
 * Created by saftophobia on 4/20/15.
 */
public class PurchaseContract extends Contract {

    private int purchase_id = -1;
    private int installments;
    private float interestRate;
    private int contract_id = -1;

    public PurchaseContract(){
        super();
    }

    public PurchaseContract(int contract_number, Date date, String place, int PersonEstateID, int installments, float interestRate, int contract_id) {
        super(contract_number, date, place, PersonEstateID);
        this.installments = installments;
        this.interestRate = interestRate;
        this.contract_id = contract_id;
    }


    public static void index(){
        try{
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "SELECT CONTRACT_NUMBER ,DATE, PLACE ,INSTALLMENTS ,INTEREST_RATE ,FIRST_NAME ,\n" +
                    "\tNAME ,CITY ,POSTAL_CODE ,STREET ,STREET_NUMBER ,SQUARE_AREA \n" +
                    "from Contract inner join PurchaseContract on Contract_ID = Contract.ID\n" +
                    "inner join PersonEstateRelation on PersonEstateRelation.ID = PE_ID\n" +
                    "inner join Person on Person.ID = Person_ID\n" +
                    "inner join Estate on Estate.ID = PersonEstateRelation.Estate_ID";

            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Contract_number: " + rs.getInt("Contract_number") + "\t place: " + rs.getString("place") +
                        "\t installments: " + rs.getInt("installments") + "\t interest_rate: " + rs.getFloat("interest_rate")+
                        "\t first_name: " + rs.getString("first_name") + "\t city: " + rs.getString("city"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static PurchaseContract load(int id){
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM PurchaseContract WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PurchaseContract ts = new PurchaseContract();
                ts.setPurchase_id(id);
                ts.setInstallments(rs.getInt("installments"));
                ts.setInterestRate(rs.getFloat("interest_rate"));
                ts.setContract_id(rs.getInt("contract_id"));

                //get Associated Contract and load the data
                Contract ms = Contract.load(ts.getContract_id());
                ts.setContract_id(ms.getContract_number());
                ts.setDate(ms.getDate());
                ts.setPlace(ms.getPlace());
                ts.setPersonEstateID(ms.getPersonEstateID());

                rs.close();
                pstmt.close();

                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void save(){
        // Hole Verbindung
        Connection con = DBConnectionManager.getInstance("mysql").getConnection();

        try {

            if (getId() == -1) {

                //saved new contract
                Contract c= new Contract(this.getContract_number(),this.getDate(),this.getPlace(),this.getPersonEstateID());
                c.save();
                this.setContract_id(c.getId());

                String insertSQL = "INSERT INTO PurchaseContract(installments, interest_Rate, Contract_ID) VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, getInstallments());
                pstmt.setFloat(2, getInterestRate());
                pstmt.setInt(3, getContract_id());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE Contract SET installments = ?, interest_rate = ?, contract_ID = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setInt(1, getInstallments());
                pstmt.setFloat(2, getInterestRate());
                pstmt.setInt(3, getContract_id());
                pstmt.setInt(4, getId());
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
}
