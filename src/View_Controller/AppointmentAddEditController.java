/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private ComboBox<String> endTime;
    @FXML
    private DatePicker appointmentDate;
    @FXML
    private ComboBox<String> startTime;
    
    Appointment appointment;    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> startTimeList = FXCollections.observableArrayList("8:00 am", "8:15 am", "8:30 am", "8:45 am",  
                "9:00 am", "9:15 am", "9:30 am", "9:45 am",
                "10:00 am", "10:15 am", "10:30 am", "10:45 am",
                "11:00 am", "11:15 am", "11:30 am", "11:45 am",
                "12:00 pm", "12:15 pm", "12:30 pm", "12:45 pm",
                "1:00 pm", "1:15 pm", "1:30 pm", "1:45 pm",
                "2:00 pm", "2:15 pm", "2:30 pm", "2:45 pm",
                "3:00 pm", "3:15 pm", "3:30 pm", "3:45 pm",
                "4:00 pm", "4:15 pm", "4:30 pm", "4:45 pm");
        ObservableList<String> endTimeList = FXCollections.observableArrayList("8:15 am", "8:30 am", "8:45 am",  
                "9:00 am", "9:15 am", "9:30 am", "9:45 am",
                "10:00 am", "10:15 am", "10:30 am", "10:45 am",
                "11:00 am", "11:15 am", "11:30 am", "11:45 am",
                "12:00 pm", "12:15 pm", "12:30 pm", "12:45 pm",
                "1:00 pm", "1:15 pm", "1:30 pm", "1:45 pm",
                "2:00 pm", "2:15 pm", "2:30 pm", "2:45 pm",
                "3:00 pm", "3:15 pm", "3:30 pm", "3:45 pm",
                "4:00 pm", "4:15 pm", "4:30 pm", "4:45 pm",
                "5:00 pm");
        ObservableList<String> consultantList = FXCollections.observableArrayList();
        ObservableList<String> customerList = FXCollections.observableArrayList();
        
        for(User consultant: UserList) {
            consultantList.add(consultant.getUserName());
        }
        
        for(Customer customer: CustomerList) {
            customerList.add(customer.getCustomerName());
        }
        
        startTime.setItems(startTimeList);
        endTime.setItems(endTimeList);
        appointmentCustomer.setItems(customerList);
        appointmentConsultant.setItems(consultantList);
        
        // Set appointment to today by default for new appointments
        appointmentDate.setValue(LocalDate.now());
  
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
    public void setAppointment(Appointment appointment, String screenType) {
        
        appointmentScreenType.setText(screenType);
        appointmentId.setText(Integer.toString(appointment.getAppointmentId()));
        
    }
    
}
