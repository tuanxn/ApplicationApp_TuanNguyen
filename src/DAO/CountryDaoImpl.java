/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
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
public class CountryDaoImpl {
    
    public static Country getCountry(int countryId) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM country WHERE countryId  = '" + countryId + "'";
        Query.makeQuery(sqlStatement);
           Country countryResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                String country=result.getString("country");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                countryResult= new Country(countryId, country, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return countryResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
        ObservableList<Country> allCountries=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from country";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int countryId=result.getInt("countryId");
                String country=result.getString("country");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Country countryResult= new Country(countryId, country, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allCountries.add(countryResult);
            }
        DBConnection.closeConnection();
        return allCountries;
    }
    
    public static void addCountry(String country, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // When we add a country, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new country
            String sqlStatement="INSERT INTO country(country, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + country + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateCountry(int countryId, String country, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create UPDATE statement to update country
            String sqlStatement="UPDATE country " +
                                "SET " +
                                "country = '" + country + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE countryId = '" + countryId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteCountry(int countryId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete country
            String sqlStatement="DELETE FROM country " +
                                "WHERE countryId = '" + countryId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }
}
