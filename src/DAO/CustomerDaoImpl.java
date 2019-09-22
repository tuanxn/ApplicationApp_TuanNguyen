/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static Utilities.TimeFiles.stringToCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author tnguy
 */
public class CustomerDaoImpl {
    
    static boolean act;
    
    public static Customer getCustomer(int customerId) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM customer WHERE customerId  = '" + customerId+ "'";
        Query.makeQuery(sqlStatement);
           Customer customerResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                String customerName=result.getString("customerName");
                int addressId=result.getInt("addressId");
                int active=result.getInt("active"); 
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                customerResult= new Customer(customerId, customerName, addressId, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return customerResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from customer";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int customerId=result.getInt("customerId");
                String customerName=result.getString("customerName");
                int addressId=result.getInt("addressId");
                int active=result.getInt("active"); 
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Customer customerResult= new Customer(customerId, customerName, addressId, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                customerResult.setCustomerPhone();
                customerResult.setCustomerAddress();
                customerResult.setCustomerAddress2();
                customerResult.setCityCode();
                customerResult.setCustomerCity();
                customerResult.setCountryCode();
                customerResult.setCustomerCountry();
                customerResult.setCustomerPostal();
                allCustomers.add(customerResult);
                
            }
        DBConnection.closeConnection();
        return allCustomers;
    }
    
    public static void addCustomer(String customerName, int addressId, boolean active, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
        
            // Active column only takes 1 or 0 so we convert the boolean value
            int activeInt;
            if(active) {
                activeInt = 1;
            }else {
                activeInt = 0;
            }
            
            // When we add a customer, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new customer
            String sqlStatement="INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + customerName + "', " +
                                "'" + addressId + "', " +
                                "'" + activeInt + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateCustomer(int customerId, String customerName, int addressId, boolean active, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
        
            // Active column only takes 1 or 0 so we convert the boolean value
            int activeInt;
            if(active) {
                activeInt = 1;
            }else {
                activeInt = 0;
            }
            
            // Create UPDATE statement to update user
            String sqlStatement="UPDATE customer " +
                                "SET " +
                                "customerName = '" + customerName + "', " +
                                "addressId = '" + addressId + "', " +
                                "active = '" + activeInt + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE customerId = '" + customerId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteCustomer(int customerId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete customer
            String sqlStatement="DELETE FROM customer " +
                                "WHERE customerId = '" + customerId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }  
    
}
