package com.marketstate.stoks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        AssertShares assertShares = new AssertShares();
       List<String> result = assertShares.makeSharesAssertion();
       if(!result.isEmpty()){System.out.println(result);}

    }

}