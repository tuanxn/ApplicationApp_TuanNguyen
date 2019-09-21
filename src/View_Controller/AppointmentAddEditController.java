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
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    Appointment appointment;    

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
        
        // Set appointment to today by default for new appointments
//        appointmentDate.setValue(LocalDate.now());
  
        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        appointmentDate.setDayCellFactory(dayCellFactory);        
 
    }    

    @FXML
    private void cancelAppointment(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();          
    }

    @FXML
    private void saveAppointment(ActionEvent event) {
        
        // Method to get DatePicker Date
        // Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        // startTime.getSelectedModel().getSelectedItem();
        // appointmentDuration.getSelectedModel().getSelectedItem();
        
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
    
}
