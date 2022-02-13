package com.marketstate.stoks;

import java.sql.*;
import java.util.*;

public class AssertShares {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/marketssearch";
    private static final String user = "";
    private static final String password = "";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    //Main method in AssertShares class.
    public List<String> makeSharesAssertion() {
        List<String> assertionResult = null;
       List<Shares> sharesValues = getSharesValues();
        addSharesInDB(sharesValues);
        cleanExcessiveDBData();
        assertionResult=makeCalculations();
        return assertionResult;
    }

    //Here we get the data from website and insert it to the list as object.
    public List<Shares> getSharesValues() {
        List<Shares> sharesData = new ArrayList<Shares>();
        return sharesData;
    }

    //Here we are inserting share's data in the database
    public static void addSharesInDB(List<Shares> sharesData) {
        String query = "INSERT INTO shares (stock, datetime) VALUES('cdewfef', CURRENT_TIMESTAMP())";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            stmt.executeUpdate(query);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
    public void cleanExcessiveDBData(){

    }
    public List<String> makeCalculations() {
        List<String> calculationsResult = null;
        return calculationsResult;
    }
}
