/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Address;
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
public class AddressDaoImpl {
    
    public static Address getAddress(String address, String address2, int cityId, String postalCode, String phone) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM address WHERE address = '" + address + "' AND address2 = '" + address2 + "' AND cityId = '" + cityId + "'" +
                            "AND postalCode = '"+ postalCode +"' AND phone = '" + phone +"'";
        Query.makeQuery(sqlStatement);
           Address addressResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                int addressId=result.getInt("addressId"); 
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                addressResult = new Address(addressId, address, address2, cityId, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return addressResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<Address> getAllAddresses() throws SQLException, Exception{
        ObservableList<Address> allAddresses=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from address";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int addressId=result.getInt("addressId");
                String address=result.getString("address");
                String address2=result.getString("address2");
                int cityId=result.getInt("cityId");
                String postalCode=result.getString("postalCode");
                String phone=result.getString("phone");        
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Address addressResult= new Address(addressId, address, address2, cityId, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allAddresses.add(addressResult);
            }
        DBConnection.closeConnection();
        return allAddresses;
    }
    
    public static void addAddress(String address, String address2, int cityId, String postalCode, String phone, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // When we add a city, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new address
            String sqlStatement="INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + address + "', " +
                                "'" + address2 + "', " +
                                "'" + cityId + "', " +
                                "'" + postalCode + "', " +
                                "'" + phone + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateAddress(int addressId, String address, String address2, int cityId, String postalCode, String phone, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create UPDATE statement to update address
            String sqlStatement="UPDATE address " +
                                "SET " +
                                "address = '" + address + "', " +
                                "address2 = '" + address2 + "', " +
                                "cityId = '" + cityId + "', " +
                                "postalCode = '" + postalCode + "', " +
                                "phone = '" + phone + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE addressId = '" + addressId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteAddress(int addressId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete address
            String sqlStatement="DELETE FROM address " +
                                "WHERE addressId = '" + addressId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }        
    
}
