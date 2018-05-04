package CDao;

import ValDao.cdw_sapp_branch;
import ValDao.cdw_sapp_creditcard;
import ValDao.cdw_sapp_customer;
import ValDao.queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataStorage extends queries {


    private PreparedStatement query_GetCusDetails;
    private PreparedStatement query_TransZip;
    private PreparedStatement query_DisplayNumTotal;
    private PreparedStatement query_Get_Monthlybill;
    private PreparedStatement query_DisplayCustTransaction;
    private PreparedStatement query_Transtate;
    public PreparedStatement query_mod;
    private PreparedStatement stopu;
    private PreparedStatement staru;

    public Connection conn;


    public boolean open()  {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONNECTION_STRING, URL1, pass);


            query_GetCusDetails = conn.prepareStatement(QUERY_GET_CUST_DETAILS_PREP);
            query_TransZip = conn.prepareStatement(QUERY_TRANSACTION_ZIP_PREP);
            query_DisplayNumTotal = conn.prepareStatement(QUERY_DISPLAYNUMTOTALBY_TYPE_PREP);
            query_Get_Monthlybill = conn.prepareStatement(QUERY_GET_MONTHLYBILL_PREP);
            query_DisplayCustTransaction = conn.prepareStatement(QUERY_DISPLAYCUSTTRANSACTIONBY_DATE_PREP);
            query_Transtate = conn.prepareStatement(QUERY_DISPLAYNUMTOTALBY_STATE_PREP);



            stopu = conn.prepareStatement(stopupdate);
            staru = conn.prepareStatement(startupdate);


            System.out.println(" Connecting to " + DB_NAME + " Database....Connected!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error with connection: " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }



    public void Query_modify(String v, String first, long second){
        try{
            conn.setAutoCommit(false);
            query_mod = conn.prepareStatement(modify1 + v + modify2 );


            query_mod.setString(1,first);
            query_mod.setLong(2,second);

            stopu.executeQuery();
            System.out.println(query_mod);
            query_mod.executeUpdate();
            staru.executeQuery();

            System.out.println("Update! Check existing account query to see updated recorded");
            conn.commit();

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<cdw_sapp_customer> query_GetCusDetails(String title) {

        try {
            query_GetCusDetails.setString(1, title);
            ResultSet results = query_GetCusDetails.executeQuery();

            List<cdw_sapp_customer> custDetails = new ArrayList<cdw_sapp_customer>();

            while (results.next()) {
                cdw_sapp_customer cdw_sapp_customer = new cdw_sapp_customer();
                cdw_sapp_customer.setFIRST_NAME(results.getString(1));
                cdw_sapp_customer.setMIDDLE_NAME(results.getString(2));
                cdw_sapp_customer.setLAST_NAME(results.getString(3));
                cdw_sapp_customer.setSSN(results.getString(4));
                cdw_sapp_customer.setCREDIT_CARD_NO(results.getString(5));
                cdw_sapp_customer.setAPT_NO(results.getString(6));
                cdw_sapp_customer.setSTREET_NAME(results.getString(7));
                cdw_sapp_customer.setCUST_CITY(results.getString(8));
                cdw_sapp_customer.setCUST_STATE(results.getString(9));
                cdw_sapp_customer.setCUST_COUNTRY(results.getString(10));
                cdw_sapp_customer.setCUST_ZIP(results.getString(11));
                cdw_sapp_customer.setCUST_PHONE(results.getInt(12));
                cdw_sapp_customer.setCUST_EMAIL(results.getString(13));

                custDetails.add(cdw_sapp_customer);
            }
            return custDetails;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    public List<cdw_sapp_creditcard> query_DisplayNumTotal(String title3) {
        try {
            query_DisplayNumTotal.setString(1, title3);
            ResultSet results = query_DisplayNumTotal.executeQuery();

            List<cdw_sapp_creditcard> transTotal = new ArrayList<cdw_sapp_creditcard>();
            while (results.next()) {
                cdw_sapp_creditcard cdw_sapp_creditcard = new cdw_sapp_creditcard();
                cdw_sapp_creditcard.setTRANSCATION_VALUE(results.getString(2));
                cdw_sapp_creditcard.setCount(results.getString(1));
                transTotal.add(cdw_sapp_creditcard);
            }
            return transTotal;
        } catch (SQLException e) {
            System.out.println("The sum of all transcations is 0 ");
            e.printStackTrace();
        }


        return null;
    }

    public void query_DISPLAY_BYSTATE(String state, List<cdw_sapp_creditcard> TransState, List<cdw_sapp_branch> TransBranch) {
        try {
            query_Transtate.setString(1, state);
            ResultSet results = query_Transtate.executeQuery();

            System.out.println(query_Transtate);
            if(results.next()==false) {
                System.out.println("There is no State by this name");
                return;
            }
            while (results.next()) {
                cdw_sapp_creditcard cdw_sapp_creditcard = new cdw_sapp_creditcard();
                cdw_sapp_branch cdw_sapp_branch = new cdw_sapp_branch();
                cdw_sapp_branch cdw_sapp_branch1 = new cdw_sapp_branch();
                cdw_sapp_creditcard.setTRANSCATION_VALUE(results.getString(2));
                cdw_sapp_creditcard.setCount(results.getString(1));
//                cdw_sapp_branch.setBRANCH_CITY(results.getString(1));
                TransState.add(cdw_sapp_creditcard);
                TransBranch.add(cdw_sapp_branch);

            }

        } catch (SQLException e) {
            System.out.println("Error in display by transaction type: " + e.getMessage());
        }
    }


    public void query_TransZip(int title2, int month, int year, List<cdw_sapp_creditcard> TransZip, List<cdw_sapp_customer> TransZipcust) {
        try {
            query_TransZip.setInt(1, title2);
            query_TransZip.setInt(2, month);
            query_TransZip.setInt(3, year);
            ResultSet results = query_TransZip.executeQuery();

            while (results.next()) {
                cdw_sapp_creditcard cdw_sapp_creditcard = new cdw_sapp_creditcard();
                cdw_sapp_customer cdw_sapp_customer = new cdw_sapp_customer();
                cdw_sapp_creditcard.setTRANSACTION_ID(results.getInt(1));
                cdw_sapp_creditcard.setDAY(results.getInt(2));
                cdw_sapp_creditcard.setMONTH(results.getInt(3));
                cdw_sapp_creditcard.setYEAR(results.getInt(4));
                cdw_sapp_creditcard.setCREDIT_CARD_NO(results.getString(5));
                cdw_sapp_creditcard.setCUST_SSN(results.getInt(6));
                cdw_sapp_creditcard.setBRANCH_CODE(results.getInt(7));
                cdw_sapp_creditcard.setTRANSACTION_TYPE(results.getString(8));
                cdw_sapp_creditcard.setTRANSCATION_VALUE(results.getString(9));
                cdw_sapp_customer.setFIRST_NAME(results.getString(10));
                cdw_sapp_customer.setMIDDLE_NAME(results.getString(11));
                cdw_sapp_customer.setLAST_NAME(results.getString(12));
                cdw_sapp_customer.setCREDIT_CARD_NO(results.getString(13));
                cdw_sapp_customer.setAPT_NO(results.getString(14));
                cdw_sapp_customer.setSTREET_NAME(results.getString(15));
                cdw_sapp_customer.setCUST_CITY(results.getString(16));
                cdw_sapp_customer.setCUST_STATE(results.getString(17));
                cdw_sapp_customer.setCUST_COUNTRY(results.getString(18));
                cdw_sapp_customer.setCUST_ZIP(results.getString(19));
                cdw_sapp_customer.setCUST_PHONE(results.getInt(20));
                cdw_sapp_customer.setCUST_EMAIL(results.getString(21));

                TransZip.add(cdw_sapp_creditcard);
                TransZipcust.add(cdw_sapp_customer);
            }


        } catch (SQLException e) {
            System.out.println("Query for Zip code fails " + e.getMessage());
            e.printStackTrace();
        }

    }


    public List<cdw_sapp_creditcard> query_DisplayCustTransDate(String ssn, int year11, int year12, int month11, int month12, int day11, int day12) {
        try {
            query_DisplayCustTransaction.setString(1, ssn);
            query_DisplayCustTransaction.setInt(2, year11);
            query_DisplayCustTransaction.setInt(3, year12);
            query_DisplayCustTransaction.setInt(4, month11);
            query_DisplayCustTransaction.setInt(5, month12);
            query_DisplayCustTransaction.setInt(6, day11);
            query_DisplayCustTransaction.setInt(7, day12);
            ResultSet results = query_DisplayCustTransaction.executeQuery();
            List<cdw_sapp_creditcard> displayCustTran = new ArrayList<cdw_sapp_creditcard>();
            while (results.next()) {
                cdw_sapp_creditcard cdw_sapp_creditcard = new cdw_sapp_creditcard();
                cdw_sapp_creditcard.setTRANSACTION_ID(results.getInt(1));
                cdw_sapp_creditcard.setDAY(results.getInt(2));
                cdw_sapp_creditcard.setMONTH(results.getInt(3));
                cdw_sapp_creditcard.setYEAR(results.getInt(4));
                cdw_sapp_creditcard.setCREDIT_CARD_NO(results.getString(5));
                cdw_sapp_creditcard.setCUST_SSN(results.getInt(6));
                cdw_sapp_creditcard.setBRANCH_CODE(results.getInt(7));
                cdw_sapp_creditcard.setTRANSACTION_TYPE(results.getString(8));
                cdw_sapp_creditcard.setTRANSCATION_VALUE(results.getString(9));

                displayCustTran.add(cdw_sapp_creditcard);
            }
            return displayCustTran;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<cdw_sapp_creditcard> query_SumTransValueCard(String cerd, String year, String month) {
        List<cdw_sapp_creditcard> monthTotal = new ArrayList<cdw_sapp_creditcard>();
        try {
            query_Get_Monthlybill.setString(1, cerd);
            query_Get_Monthlybill.setString(2, month);
            query_Get_Monthlybill.setString(3, year);
            ResultSet results = query_Get_Monthlybill.executeQuery();
            System.out.println(query_Get_Monthlybill);

            while (results.next()) {
                cdw_sapp_creditcard cdw_sapp_creditcard = new cdw_sapp_creditcard();
                cdw_sapp_creditcard.setTRANSCATION_VALUE(results.getString(1));
                System.out.println(cdw_sapp_creditcard.getTRANSCATION_VALUE());
                monthTotal.add(cdw_sapp_creditcard);
            }
            return monthTotal;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return monthTotal;
    }


}



