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
        //Here we extract the price and title for every share from getSharesWebResources and set it into the sharesdata List.
        for(String shareEmail: Resources.getSharesWebResourcesList()){
            Document doc = Jsoup.connect(shareEmail).get();
            Element desktopOnly = doc.getElementsByClass("YMlKec fxKbKc").first();
            String linkText = desktopOnly.text().replace("$","").replace(",","");
           float result = Float.parseFloat(linkText);
           //Here we prepare the Stock's name value
            Elements rawstockTitle = doc.getElementsByClass("zzDege");
            String stockTitle = rawstockTitle.text();
            //Here we are creating the share object to add in the list
            Shares share = new Shares();
            share.stockPrice$Value=result;
            share.stockName=stockTitle;
            System.out.print(stockTitle);
            System.out.println(" " + result);

            //Here we are adding the shares to the list
            sharesData.add(share);
        }

        return sharesData;
    }

    //Here we are inserting share's data in the database
    public static void addSharesInDB(List<Shares> sharesData) {





            for(Shares share:sharesData) {
                try {

                    String query = String.format("INSERT INTO shares (stock, datetime, stockPrice$) VALUES('%s' , CURRENT_TIMESTAMP(), '%s')", share.stockName, share.stockPrice$Value);
                    // opening database connection to MySQL server
                    con = DriverManager.getConnection(url, user, password);

                    // getting Statement object to execute query
                    stmt = con.createStatement();
                    // executing SELECT query
                    stmt.executeUpdate(query);
                }
                catch (SQLException sqlEx) {
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
    }
    public void cleanExcessiveDBData(){

    }
    public List<String> makeCalculations() {
        List<String> calculationsResult = null;
        return calculationsResult;
    }
}
