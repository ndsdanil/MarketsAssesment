package com.marketstate.stoks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.transform.Result;
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
        assertionResult=makeCalculations(sharesValues);
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
           //Here we get information to convert $ in RUB
            Document docR = Jsoup.connect(Resources.rub).get();
            Element rubOnly = docR.getElementsByClass("YMlKec fxKbKc").first();
            String linkTextR = rubOnly.text().replace("$","").replace(",","");
            float resultR = Float.parseFloat(linkTextR);
           //Here we prepare the Stock's name value
            Elements rawstockTitle = doc.getElementsByClass("zzDege");
            String stockTitle = rawstockTitle.text();
            //Here we are creating the share object to add in the list
            Shares share = new Shares();
            share.stockPrice$Value=result;
            share.stockPriceRUBValue=result/resultR;
            share.stockName=stockTitle;
            System.out.print(stockTitle);
            System.out.println(" " + result);
            System.out.println(share.stockPriceRUBValue);
            System.out.println(resultR);

            //Here we are adding the shares to the list
            sharesData.add(share);
        }

        return sharesData;
    }

    //Here we are inserting share's data in the database
    public static void addSharesInDB(List<Shares> sharesData) {





            for(Shares share:sharesData) {
                try {

                    String query = String.format("INSERT INTO shares (stock, datetime, stockPrice$, stockPriceRUB) VALUES('%s' , CURRENT_TIMESTAMP(), '%s' , '%s')", share.stockName, share.stockPrice$Value, share.stockPriceRUBValue);
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
    public List<String> makeCalculations(List<Shares> sharesData) {
        List<String> calculationsResult = new ArrayList<String>();
        List<Shares> preparedDataForMath = new ArrayList<Shares>();
        for (Shares share:sharesData) {

//        Here we are getting the data from the DB
            try {

                String queryget = String.format("SELECT stock, datetime, stockPrice$, stockPriceRUB FROM shares WHERE stock ='" +share.stockName+ "' AND DATE(datetime)<= CURDATE() AND DATE(datetime)>= DATE(NOW()-INTERVAL 5 DAY)");
                // opening database connection to MySQL server
                con = DriverManager.getConnection(url, user, password);

                // getting Statement object to execute query
                stmt = con.createStatement();
                // executing SELECT query
                ResultSet rs = stmt.executeQuery(queryget);
                Shares dataForMath = new Shares();
                dataForMath.stockName = share.stockName;
                while (rs.next()) {
                    dataForMath.listForMath$.add(rs.getFloat("stockPrice$"));
                    dataForMath.listForMathRUB.add(rs.getFloat("stockPriceRUB"));
                    //Display values
                    System.out.print(rs.getString("stock"));
                    System.out.print(" StockPrice$ " + rs.getFloat("stockPrice$"));
                    System.out.println(" StockPriceRUB " + rs.getFloat("stockPriceRUB"));

                }
                preparedDataForMath.add(dataForMath);
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

        //Calculation of aproximate value
        for (Shares share: preparedDataForMath) {
            float result = 0;
          for (Float fl : share.listForMath$){
              result+=fl;

          }
          result = result/share.listForMath$.size();
            System.out.println(result);

          if (share.listForMath$.get(share.listForMath$.size()-1)<=result*((float)1-(float)10/100)){
              String a= (share.stockName +"Dropped more than 10%! Current price is: " + share.listForMath$.get(share.listForMath$.size()-1)+ "$. " + "Average price for last 5 days is: "+ result);
              calculationsResult.add(a);
          }

        }
        return calculationsResult;
    }
}
