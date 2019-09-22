/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.City;
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
public class CityDaoImpl {
    
    public static City getCity(String city, int countryId) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM city WHERE city = '" + city + "' AND countryId = '" + countryId +"'";
        Query.makeQuery(sqlStatement);
           City cityResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                int cityId=result.getInt("cityId");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                cityResult= new City(cityId, city, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return cityResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<City> getAllCities() throws SQLException, Exception{
        ObservableList<City> allCities=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from city";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int cityId=result.getInt("cityId");
                String city = result.getString("city");
                int countryId=result.getInt("countryId");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                City cityResult= new City(cityId, city, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allCities.add(cityResult);
            }
        DBConnection.closeConnection();
        return allCities;
    }
    
    public static void addCity(String city, int countryId, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // When we add a city, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new city
            String sqlStatement="INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + city + "', " +
                                "'" + countryId + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateCity(int cityId, String city, int countryId, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create UPDATE statement to update city
            String sqlStatement="UPDATE city " +
                                "SET " +
                                "city = '" + city + "', " +
                                "countryId = '" + countryId + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE cityId = '" + cityId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteCity(int cityId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete city
            String sqlStatement="DELETE FROM city " +
                                "WHERE cityId = '" + cityId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }    
    
}
