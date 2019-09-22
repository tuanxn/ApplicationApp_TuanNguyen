/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import Utilities.TimeFiles;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class AppointmentAddEditController implements Initializable {

    @FXML
    private Label appointmentScreenType;
    @FXML
    private TextField appointmentId;
    @FXML
    private TextField appointmentTitle;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentContact;
    @FXML
    private TextField appointmentType;
    @FXML
    private TextField appointmentUrl;
    @FXML
    private ComboBox<String> appointmentCustomer;
    @FXML
    private ComboBox<String> appointmentConsultant;
    @FXML
    private DatePicker appointmentDate;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> startMin;
    @FXML
    private ComboBox<String> endMin;
    
    Appointment modifiedAppointment;    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> hourList = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                                                                            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24");
        ObservableList<String> minList= FXCollections.observableArrayList("00", "15", "30", "45");
        ObservableList<String> consultantList = FXCollections.observableArrayList();
        ObservableList<String> customerList = FXCollections.observableArrayList();
        
        for(User consultant: UserList) {
            consultantList.add(consultant.getUserName());
        }
        
        for(Customer customer: CustomerList) {
            customerList.add(customer.getCustomerName());
        }
        
        startHour.setItems(hourList);
        endHour.setItems(hourList);
        startMin.setItems(minList);
        endMin.setItems(minList);
        appointmentCustomer.setItems(customerList);
        appointmentConsultant.setItems(consultantList);
  
        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        appointmentDate.setDayCellFactory(dayCellFactory);        
 
    }    

    @FXML
    private void saveAppointment(ActionEvent event) {
        
        String errorMessage = "";
        boolean isError = false;
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        Calendar start = null;
        Calendar end = null;
        
        try {     
            String saveUser = appointmentConsultant.getValue();
            if(saveUser == null) {
                errorMessage += "Consultant not set \n";
                isError = true;
            }else {
                // If consultant provided, create list of current appointments for that consultant so we can check overlaps
                for(Appointment appointment: AppointmentList) {
                    if(saveUser == appointment.getUserName()) {
                        userAppointments.add(appointment);
                    }
                }
            }
            String saveCustomer = appointmentCustomer.getValue();
            if(saveCustomer == null) {
                errorMessage += "Customer not set \n";
                isError = true;
            }else {
                // If customer provided, create list of current appointments for that customer so we can check overlaps
                for(Appointment appointment: AppointmentList) {
                    if(saveCustomer == appointment.getCustomerName()) {
                        customerAppointments.add(appointment);
                    }
                }
            }            
            String saveTitle = appointmentTitle.getText();
            if(saveTitle.isEmpty()) {
                errorMessage += "Title not set \n";
                isError = true;
            }
            String saveDescription = appointmentDescription.getText();
            if(saveDescription.isEmpty()) {
                errorMessage += "Description not set \n";
                isError = true;
            }
            String saveLocation = appointmentLocation.getText();
            if(saveLocation.isEmpty()) {
                errorMessage += "Location not set \n";
                isError = true;
            }
            String saveContact = appointmentContact.getText();
            if(saveContact.isEmpty()) {
                errorMessage += "Contact not set \n";
                isError = true;
            }
            String saveType = appointmentType.getText();
            if(saveType.isEmpty()) {
                errorMessage += "Type not set \n";
                isError = true;
            }
            String saveUrl = appointmentUrl.getText();
            if(saveUrl.isEmpty()) {
                errorMessage += "URL not set \n";
                isError = true;
            }
            String saveDate = "";
            try {
                saveDate = appointmentDate.getValue().toString();
            }catch (Exception e) {
                    errorMessage += "Date not set \n";
                    isError = true;
            }
            String saveStartHour = startHour.getValue();
            if(saveStartHour == null) {
                errorMessage += "Starting hour not set \n";
                isError = true;
            // If starting time is set, but not between working hours of 9am-5pm
            }else if(Integer.parseInt(saveStartHour)<9 || Integer.parseInt(saveStartHour) > 16) {
                errorMessage += "Start time must be between 9am-5pm \n";
                isError = true;
            }
            String saveStartMin = startMin.getValue();
            if(saveStartMin == null) {
                errorMessage += "Starting minutes not set \n";
                isError = true;
            }
            String saveEndHour = endHour.getValue();
            if(saveEndHour == null) {
                errorMessage += "Ending hour not set \n";
                isError = true;
            // If ending time is set, but not between working hours of 9am-5pm
            }else if(Integer.parseInt(saveEndHour)<9 || Integer.parseInt(saveStartHour) > 17) {
                errorMessage += "End time must be between 9am-5pm \n";
                isError = true;
            }
            String saveEndMin = endMin.getValue();
            if(saveEndMin == null) {
                errorMessage += "Ending minutes not set";
                isError = true;
            }
            
            // If we are modifying an appointment, remove the existing appointment from the list of appointments that we need to check overlapping for
            if(appointmentScreenType.getText().equals("Modify Appointment")) {
                userAppointments.remove(modifiedAppointment);
                customerAppointments.remove(modifiedAppointment);
            }            
            
            // If no other errors, check if appointment overlaps with an existing appointment for this user or customer
            /* 
                Exception Handling converts beginning and ending times into DateTime values and tests if they fall into 1 of the 4 categories:
                    1. Beginning time falls between starting and ending time of an appointment (overlaps partial over end of appointment)
                    2. Ending time falls between starting and ending time of an appointment (overlaps partial over beginning of appointment)
                    3. Beginning time begins BEFORE OR ON the appointment starting time 
                        and ends ON OR AFTER the appointment ending time (overlaps entire thing and beyond)
            */
            
            if(!isError) {
                start = TimeFiles.localStringToCalendar(saveDate.replace("/", "-") + " " + saveStartHour + ":" + saveStartMin + ":00");
                end = TimeFiles.localStringToCalendar(saveDate.replace("/", "-") + " " + saveEndHour + ":" + saveEndMin + ":00");
                if(end.before(start) || end.equals(start)) {
                    errorMessage +="end time must be after start time";
                    isError = true;
                }
            }
            
            if(!isError) {

                // Loop through current user appointments and check for overlaps
                for(Appointment appointment: userAppointments) {
                    if((start.getTime().after(appointment.getStart().getTime()) && start.getTime().before(appointment.getEnd().getTime())) || 
                            (end.getTime().after(appointment.getStart().getTime()) && end.getTime().before(appointment.getEnd().getTime())) ||
                            ((start.getTime().before(appointment.getStart().getTime()) || (start.getTime().equals(appointment.getStart().getTime()))) && 
                            ((end.getTime().equals(appointment.getEnd().getTime())) || end.getTime().after(appointment.getEnd().getTime())))) { 
                        errorMessage +="Appointment overlaps with existing user appointment \n";
                        isError = true;
                    }
                }
                // Loop through current customer appointments and check for overlaps
                for(Appointment appointment: customerAppointments) {
                    if((start.getTime().after(appointment.getStart().getTime()) && start.getTime().before(appointment.getEnd().getTime())) || 
                            (end.getTime().after(appointment.getStart().getTime()) && end.getTime().before(appointment.getEnd().getTime())) ||
                            (start.getTime().before(appointment.getStart().getTime()) && end.getTime().after(appointment.getEnd().getTime())) || 
                            (start.getTime().equals(appointment.getStart().getTime()) && end.getTime().equals(appointment.getEnd().getTime()))){
                        errorMessage +="Appointment overlaps with existing customer appointment";
                        isError = true;
                    }
                }
            }
            
            // If error flag set to true, then throw error that was created
            if(isError) {
                throw new Exception(errorMessage);
            }else {
                // Otherwise, add new appointment to Appointment Table
                
                // Replace customer and user names with IDs
                int saveCustomerId = 0;
                int saveUserId = 0;
                for(Customer customer: CustomerList) {
                    if(customer.getCustomerName().toUpperCase().equals(saveCustomer.toUpperCase())) {
                        saveCustomerId = customer.getCustomerId();
                    }
                }
                for(User user: UserList) {
                    if(user.getUserName().toUpperCase().equals(saveUser.toUpperCase())) {
                        saveUserId = user.getUserId();
                    }
                }
                
                if(appointmentScreenType.getText().equals("Modify Appointment")) {
                    DAO.AppointmentDaoImpl.updateAppointment(Integer.parseInt(appointmentId.getText()), saveCustomerId, saveUserId, saveTitle, saveDescription, saveLocation, saveContact, saveType, saveUrl, start, end, loggedInUser.getUserName());
                }else {
                    // Add to Appointment Table
                    DAO.AppointmentDaoImpl.addAppointment(saveCustomerId, saveUserId, saveTitle, saveDescription, saveLocation, saveContact, saveType, saveUrl, start, end, loggedInUser.getUserName(), loggedInUser.getUserName());
                }
                // Repopulate AppointmentList with new appointment
                AppointmentList = DAO.AppointmentDaoImpl.getAllAppointments();
                
                // Return to Appointment Screen
                Parent parent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
                Scene part_screen_scene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(part_screen_scene);
                app_stage.show();   
            }
            
        }catch (Exception e) {
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
        
    }
    
    // Callback to set picked date from the calendar picker
    private Callback<DatePicker, DateCell> getDayCellFactory() {
 
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
 
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
 
                        // Disable Monday, Tueday, Wednesday.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }    
    
    // Set selected appointment if modifying appointment
    public void setAppointment(Appointment appointment, String screenType) throws ParseException{
        
        modifiedAppointment = appointment;
        
        appointmentScreenType.setText(screenType);
        appointmentId.setText(Integer.toString(appointment.getAppointmentId()));
        appointmentConsultant.setValue(appointment.getUserName());
        appointmentCustomer.setValue(appointment.getCustomerName());
        appointmentTitle.setText(appointment.getTitle());
        appointmentDescription.setText(appointment.getDescription());
        appointmentLocation.setText(appointment.getLocation());
        appointmentContact.setText(appointment.getContact());
        appointmentType.setText(appointment.getType());
        appointmentUrl.setText(appointment.getUrl());
        appointmentDate.setValue(LocalDateTime.ofInstant(appointment.getStart().toInstant(), appointment.getStart().getTimeZone().toZoneId()).toLocalDate());
        startHour.setValue(TimeFiles.ConvertToLocalTimeHours(appointment.getStart()));
        startMin.setValue(TimeFiles.ConvertToLocalTimeMinutes(appointment.getStart()));
        endHour.setValue(TimeFiles.ConvertToLocalTimeHours(appointment.getEnd()));
        endMin.setValue(TimeFiles.ConvertToLocalTimeMinutes(appointment.getEnd()));        
    }

    @FXML
    private void cancelAddEditAppointment(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show(); 
    }
    
}
