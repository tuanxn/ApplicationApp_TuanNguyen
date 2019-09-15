/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author tuanxn
 */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        query = q;
        
        try {
            // Create Statement object
            stmt = DBConnection.conn.createStatement();
            
            // Determine query execution
            if(query.toLowerCase().startsWith("select")) {
                result = stmt.executeQuery(query);
            }else {
                stmt.executeUpdate(query);
            }
            
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static ResultSet getResult() {
        return result;
    }
}


