/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author tuanxn
 */
public class TimeFiles {
    // Reads in string time values from the Tables in GMT and creates a calendar object in local time
    public static Calendar stringToCalendar(String strDate) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
       Date date = sdf. parse(strDate);
       Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
   } 
    
   // Reads in string time values from the application in local time and creates calendar object in local time
   public static Calendar localStringToCalendar(String strDate) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = sdf. parse(strDate);
       Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
   } 
    
   public static String ConvertToGMT(Calendar time) throws ParseException{
       SimpleDateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
       return gmtFormat.format(time.getTime());
   }
   
   public static String ConvertToLocalTime(Calendar time) throws ParseException {
       SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       localFormat.setTimeZone(TimeZone.getDefault());
       return localFormat.format(time.getTime());
   }
   
   public static String ConvertToLocalTimeHours(Calendar time) throws ParseException {
       SimpleDateFormat localFormat = new SimpleDateFormat("HH");
       localFormat.setTimeZone(TimeZone.getDefault());
       return localFormat.format(time.getTime());
   }
    
   public static String ConvertToLocalTimeMinutes(Calendar time) throws ParseException {
       SimpleDateFormat localFormat = new SimpleDateFormat("mm");
       localFormat.setTimeZone(TimeZone.getDefault());
       return localFormat.format(time.getTime());
   }
    
   // Method to format the appointment times for easier viewing in tables
   public static String FormatForAppointmentTable(Calendar appointmentTime) throws ParseException{
       SimpleDateFormat appointmentLocalFormat = new SimpleDateFormat("MM/dd/yyyy  HH:mm");
       appointmentLocalFormat.setTimeZone(TimeZone.getDefault());
       return appointmentLocalFormat.format(appointmentTime.getTime());
   }
   
}
