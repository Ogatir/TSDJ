package com.tsdj.server;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    public static ResultSet Execute_SELECT(String query)throws SQLException, NamingException  {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/TSDJ");
        Connection c = ds.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query) ;
        return rs;
    }

    public static int Execute_INSERT(String query)throws SQLException, NamingException  {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/TSDJ");
        Connection c = ds.getConnection();
        Statement stmt = c.createStatement();
        return stmt.executeUpdate(query);
    }

}
