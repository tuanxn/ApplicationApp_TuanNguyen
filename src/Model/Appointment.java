/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utilities.TimeFiles;
import java.util.Calendar;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;

/**
 *
 * @author tnguy
 */
public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String startString;
    private Calendar start;
    private String endString;
    private Calendar end;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    private String customerName;
    private String userName;
    
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, Calendar start, Calendar end, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getStartString() {
        return startString;
    }
    
    public void setStartString(Calendar appointmentStart) throws Exception{
        this.startString = TimeFiles.FormatForAppointmentTable(appointmentStart);
    }    
    
    public Calendar getStart() {
        return start;
    }
    
    public void setStart(Calendar start) {
        this.start = start;
    }
    
    public String getEndString() {
        return endString;
    }
    
    public void setEndString(Calendar appointmentEnd) throws Exception{
        this.endString = TimeFiles.FormatForAppointmentTable(appointmentEnd);
    }          
    
    public Calendar getEnd() {
        return end;
    }
    
    public void setEnd(Calendar end) {
        this.end = end;
    }
    
    public Calendar getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Calendar getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
//    public void setCustomerName(int customerId) throws Exception{
//        Customer customer=DAO.CustomerDaoImpl.getCustomer(customerId);
//        if(customer != null) {
//            this.customerName = customer.getCustomerName();
//        }
//    }   
    
    public void setCustomerName(int customerId) throws Exception{
        for(Customer customer: CustomerList) {
            if(customer.getCustomerId()==customerId) {
                this.customerName = customer.getCustomerName();
            }
        }
    }       
    
    public String getUserName() {
        return userName;
    }    
    
    public void setUserName(int userId) throws Exception{
        for(User user: UserList) {
            if(user.getUserId()==userId) {
                this.userName = user.getUserName();
            }
        }
    }      
    
}
