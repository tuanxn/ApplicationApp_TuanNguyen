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

/**
 *
 * @author tnguy
 */
public class UserDaoImpl {
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
             //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                User userResult= new User(userid, userNameG, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allUsers.add(userResult);
                
            }
             DBConnection.closeConnection();
        return allUsers;
    } 
}
