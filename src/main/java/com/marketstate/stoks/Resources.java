package com.marketstate.stoks;

import java.util.*;

public class Resources {
   static ArrayList<String> webPages = new ArrayList<String>();
    //wab pages for every share
   private static String apple = "https://www.google.com/finance/quote/AAPL:NASDAQ?sa=X&ved=2ahUKEwi2wLuBqPz1AhXotYsKHRdcCk0Q3ecFegQIEhAe&window=1Y";
   private static String Microsoft ="https://www.google.com/finance/quote/MSFT:NASDAQ";
   private static String Tesla ="https://www.google.com/finance/quote/TSLA:NASDAQ";
   private static String jnj ="https://www.google.com/finance/quote/JNJ:NYSE";
   private static String google ="https://www.google.com/finance/quote/GOOG:NASDAQ";
   private static String amazon ="https://www.google.com/finance/quote/AMZN:NASDAQ";
   private static String nike ="https://www.google.com/finance/quote/NKE:NYSE";

    public static ArrayList<String> getSharesWebResourcesList() {
        webPages.add(apple);
        webPages.add(Microsoft);
        webPages.add(Tesla);
        webPages.add(jnj);
        webPages.add(google);
        webPages.add(amazon);
        webPages.add(nike);


    return webPages;
    }


}
