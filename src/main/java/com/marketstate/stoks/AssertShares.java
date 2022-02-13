package com.marketstate.stoks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import java.util.*;
import java.io.IOException;

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
    public List<String> makeSharesAssertion() throws IOException {
        List<String> assertionResult = null;
       List<Shares> sharesValues = getSharesValues();
        addSharesInDB(sharesValues);
        cleanExcessiveDBData();
        assertionResult=makeCalculations();
        return assertionResult;
    }

    //Here we get the data from website and insert it to the list as object.
    public List<Shares> getSharesValues() throws IOException {
        List<Shares> sharesData = new ArrayList<Shares>();
        Document doc = Jsoup.connect("https://www.google.com/finance/quote/AAPL:NASDAQ?sa=X&ved=2ahUKEwi2wLuBqPz1AhXotYsKHRdcCk0Q3ecFegQIEhAe&window=1Y").get();
       //Here we prepare the stock value
        Elements desktopOnly = doc.getElementsByClass("YMlKec fxKbKc");
       String linkText = desktopOnly.text().replace("$","");
       float result = Float.parseFloat(linkText);
       //Here we prepare the Stock's name value
        Elements rawstockTitle = doc.getElementsByClass("zzDege");
        String stockTitle = rawstockTitle.text();
        //Here we are creating the share object to add in the list
       Shares apple = new Shares();
        share.stockPrice$Value=result;
        share.stockName=stockTitle;
        System.out.println(result);
        System.out.println(stockTitle);
        //Here we are adding the shares to the list
        sharesData.add(apple);
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
