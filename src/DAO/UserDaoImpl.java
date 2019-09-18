/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
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
public class UserDaoImpl{
    
    static boolean act;
    
    public static User getUser(String userName) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM user WHERE userName  = '" + userName+ "'";
        Query.makeQuery(sqlStatement);
           User userResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                int userid=result.getInt("userid");
                String userNameG=result.getString("userName");
                String password=result.getString("password");
                int active=result.getInt("active"); 
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                userResult= new User(userid, userName, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return userResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }
    
    public static User getUser(int userId) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sqlStatement="select * FROM user WHERE userId  = '" + userId+ "'";
        Query.makeQuery(sqlStatement);
           User userResult;
           ResultSet result=Query.getResult();
           while(result.next()){
  
                String userName=result.getString("userName");
                String password=result.getString("password");
                int active=result.getInt("active"); 
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");              
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                userResult= new User(userId, userName, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return userResult;
                
           }
             DBConnection.closeConnection();
        return null;
    }    
    
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers=FXCollections.observableArrayList();    
        DBConnection.makeConnection();
            String sqlStatement="select * from user";          
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int userid=result.getInt("userid");
                String userNameG=result.getString("userName");
                String password=result.getString("password");
                int active=result.getInt("active");
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createdBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdateBy");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                User userResult= new User(userid, userNameG, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allUsers.add(userResult);
                
            }
        DBConnection.closeConnection();
        return allUsers;
    }
    
    public static void addUser(String userName, String password, boolean active, String createdBy, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
        
            // Active column only takes 1 or 0 so we convert the boolean value
            int activeInt;
            if(active) {
                activeInt = 1;
            }else {
                activeInt = 0;
            }
            
            // When we add a user, we will record the current time in GMT
            Calendar gmtCalendar = Calendar.getInstance(Locale.getDefault());
            String gmtTimeNow = Utilities.TimeFiles.ConvertToGMT(gmtCalendar);
            
            // Create INSERT statement to add new user
            String sqlStatement="INSERT INTO user(userName, password, active, createDate, createdBy, lastUpdateBy)" +
                                "VALUES(" +
                                "'" + userName + "', " +
                                "'" + password + "', " +
                                "'" + activeInt + "', " +
                                "'" + gmtTimeNow + "', " +
                                "'" + createdBy + "', " +
                                "'" + lastUpdateBy + "')";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }    

    public static void updateUser(int userId, String userName, String password, boolean active, String lastUpdateBy) throws SQLException, Exception{
        DBConnection.makeConnection();
        
            // Active column only takes 1 or 0 so we convert the boolean value
            int activeInt;
            if(active) {
                activeInt = 1;
            }else {
                activeInt = 0;
            }
            
            // Create UPDATE statement to update user
            String sqlStatement="UPDATE user " +
                                "SET " +
                                "userName = '" + userName + "', " +
                                "password = '" + password + "', " +
                                "active = '" + activeInt + "', " +
                                "lastUpdateBy = '" + lastUpdateBy + "' " +
                                "WHERE userId = '" + userId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();
    }
    
    public static void deleteUser(int userId) throws SQLException, Exception{
        DBConnection.makeConnection();
            
            // Create DELETE statement to delete user
            String sqlStatement="DELETE FROM user " +
                                "WHERE userId = '" + userId + "'";          
            Query.makeQuery(sqlStatement);
        DBConnection.closeConnection();        
    }
    
}
