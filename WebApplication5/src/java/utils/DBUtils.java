/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.DriverManager;
import java.sql.SQLDataException;

/**
 *
 * @author zzzdi
 */
public class DBUtils {
    private static final String DB_NAME = "Web_055_a";
    private static final String DB_USERNAME_ = "sa";
    private static final String DB_PASSWORD = "12345"; 
    public static Connection getConnection() throws ClassNotFoundException, SQLDataException{
        Connection conn = null;
        Class.forName("Com.microsoft.sqlsever.jdbc.SQLServerDriver");
        String url = "jdbc:sqlsever://localhost:1433;databaseName="+DB_NAME;
        conn = DriverManager.getConnection(url, DB_USERNAME_, DB_PASSWORD);
        return conn;
    }
    public static void main(String[] args) {
       try{
           Connection conn = getConnection();
           System.out.println(conn);
           
       }catch (ClassNotFoundException EX) {
           lOGGER.get
       }
    }

}
