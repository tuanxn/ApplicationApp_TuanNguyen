/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
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
public class AppointmentDaoImpl {

    public static Appointment getAppointment(int appointmentId) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM appointment WHERE appointmentId = '" + appointmentId + "'";
        Query.makeQuery(sqlStatement);
           Appointment appointmentResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                int customerId=result.getInt("customerId");
                int userId=result.getInt("userId");
                String title=result.getString("title");
                String description=result.getString("description");
                String location=result.getString("location");
                String contact=result.getString("contact");
                String type=result.getString("type");
                String url=result.getString("url");
                String start=result.getString("start");
                String end=result.getString("end");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar startCalendar=stringToCalendar(start);
                Calendar endCalendar=stringToCalendar(end);
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                appointmentResult = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return appointmentResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from appointment";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int appointmentId=result.getInt("appointmentId");
                int customerId=result.getInt("customerId");
                int userId=result.getInt("userId");
                String title=result.getString("title");
                String description=result.getString("description");
                String location=result.getString("location");
                String contact=result.getString("contact");
                String type=result.getString("type");
                String url=result.getString("url");
                String start=result.getString("start");
                String end=result.getString("end");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar startCalendar=stringToCalendar(start);
                Calendar endCalendar=stringToCalendar(end);
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Appointment appointmentResult = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                System.out.println("test");   
                appointmentResult.setCustomerName(customerId);
                System.out.println("test");                   
                appointmentResult.setUserName(userId);
                System.out.println("test");                   
                appointmentResult.setStartString(startCalendar);
                System.out.println("test");                   
                appointmentResult.setEndString(endCalendar);
                allAppointments.add(appointmentResult);
            }
        DBConnection.closeConnection();
        return allAppointments;
    }
    
    public static ObservableList<Appointment> getUserAppointments(int userId) throws SQLException, Exception{
        ObservableList<Appointment> allUserAppointments=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="SELECT * from appointment WHERE userId = '" + userId + "'";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int appointmentId=result.getInt("appointmentId");
                int customerId=result.getInt("customerId");
                String title=result.getString("title");
                String description=result.getString("description");
                String location=result.getString("location");
                String contact=result.getString("contact");
                String type=result.getString("type");
                String url=result.getString("url");
                String start=result.getString("start");
                String end=result.getString("end");
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar startCalendar=stringToCalendar(start);
                Calendar endCalendar=stringToCalendar(end);
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Appointment appointmentResult = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                appointmentResult.setCustomerName(customerId);
                appointmentResult.setUserName(userId);
                appointmentResult.setStartString(startCalendar);
                appointmentResult.setEndString(endCalendar);
                allUserAppointments.add(appointmentResult);
            }
        DBConnection.closeConnection();
        return allUserAppointments;
    }    
    
    public static void addAppointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, Calendar start, Calendar end, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Convert start time to GMT
            String gmtStart = Utilities.TimeFiles.ConvertToGMT(start);
            
            // Convert end time to GMT
            String gmtEnd = Utilities.TimeFiles.ConvertToGMT(end);
        
            // When we add a city, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new appointment
            String sqlStatement="INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + customerId + "', " +
                                "'" + userId + "', " +
                                "'" + title + "', " +
                                "'" + description + "', " +
                                "'" + location + "', " +
                                "'" + contact + "', " +
                                "'" + type + "', " +
                                "'" + url + "', " +
                                "'" + gmtStart + "', " +
                                "'" + gmtEnd + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateAppointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, Calendar start, Calendar end, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
        
            // Convert start time to GMT
            String gmtStart = Utilities.TimeFiles.ConvertToGMT(start);
            
            // Convert end time to GMT
            String gmtEnd = Utilities.TimeFiles.ConvertToGMT(end);
            
            // Create UPDATE statement to update appointment
            String sqlStatement="UPDATE appointment " +
                                "SET " +
                                "customerId = '" + customerId + "', " +
                                "userId = '" + userId + "', " +
                                "title = '" + title + "', " +
                                "description = '" + description + "', " +
                                "location = '" + location + "', " +
                                "contact = '" + contact + "', " +
                                "type = '" + type + "', " +
                                "url = '" + url + "', " +
                                "start = '" + gmtStart + "', " +
                                "end = '" + gmtEnd + "', " +
                                "createdBy = '" + createdBy + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE appointmentId = '" + appointmentId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteAppointment(int appointmentId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete appointment
            String sqlStatement="DELETE FROM appointment " +
                                "WHERE appointmentId = '" + appointmentId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }        
    
}
