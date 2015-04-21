package com.db.models;

import com.db.conn.DBConnectionManager;

import java.sql.*;

/**
 * Created by saftophobia on 4/20/15.
 */
public class TenancyContract extends Contract{

    private int tenancy_id = -1;
    private Date start_date;
    private int duration;
    private int add_costs;
    private int contract_id = -1;

    public TenancyContract(){
        super();
    }

    public TenancyContract(int contract_number, Date date, String place, int PersonEstateID, Date start_date, int duration, int add_costs, int contract_id) {
        super(contract_number, date, place, PersonEstateID);
        this.start_date = start_date;
        this.duration = duration;
        this.add_costs = add_costs;
        this.contract_id = contract_id;
    }

    public static TenancyContract load(int id){
        try {
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();

            String selectSQL = "SELECT * FROM TenancyContract WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TenancyContract ts = new TenancyContract();
                ts.setTenancy_id(id);
                ts.setStart_date(rs.getDate("installments"));
                ts.setDuration(rs.getInt("place"));
                ts.setAdd_costs(rs.getInt("add_costs"));
                ts.setContract_id(rs.getInt("contract_id"));

                //get Associated Contract and load the data
                Contract ms = Contract.load(ts.getContract_id());
                ts.setContract_number(ms.getContract_number());
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

    public static void index(){
        try{
            Connection con = DBConnectionManager.getInstance("mysql").getConnection();
            String selectSQL = "SELECT CONTRACT_NUMBER ,DATE, PLACE ,START_DATE ,DURATION ,\n" +
                    "\tADD_COSTS ,FIRST_NAME ,\tNAME ,CITY ,POSTAL_CODE ,STREET ,STREET_NUMBER ,SQUARE_AREA \n" +
                    "from Contract inner join TenancyContract on Contract_ID = Contract.ID\n" +
                    "inner join PersonEstateRelation on PersonEstateRelation.ID = PE_ID\n" +
                    "inner join Person on Person.ID = Person_ID\n" +
                    "inner join Estate on Estate.ID = PersonEstateRelation.Estate_ID";

            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Contract_number: " + rs.getInt("Contract_number") + "\t place: " + rs.getString("place") +
                        "\t Duration: " + rs.getInt("Duration") + "\t Add_Costs: " + rs.getInt("Add_Costs")+
                        "\t first_name: " + rs.getString("first_name") + "\t city: " + rs.getString("city"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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

                String insertSQL = "INSERT INTO TenancyContract(start_date, duration, add_costs, Contract_ID) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setDate(1, getStart_date());
                pstmt.setInt(2, getDuration());
                pstmt.setInt(3, getAdd_costs());
                pstmt.setInt(4, getContract_id());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setId(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                String updateSQL = "UPDATE Contract SET start_date = ?, duration = ?, add_costs = ? ,contract_ID = ? WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                pstmt.setDate(1, getStart_date());
                pstmt.setInt(2, getDuration());
                pstmt.setInt(3, getAdd_costs());
                pstmt.setInt(4, getContract_id());
                pstmt.setInt(5, getId());
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getTenancy_id() {
        return tenancy_id;
    }

    public void setTenancy_id(int tenancy_id) {
        this.tenancy_id = tenancy_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAdd_costs() {
        return add_costs;
    }

    public void setAdd_costs(int add_costs) {
        this.add_costs = add_costs;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
}
