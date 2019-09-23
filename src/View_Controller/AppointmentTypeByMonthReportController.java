/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.AppointmentType;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class AppointmentTypeByMonthReportController implements Initializable {

    @FXML
    private TableView<AppointmentType> AppointTypeTable;
    @FXML
    private TableColumn<AppointmentType, String> appointmentType;
    @FXML
    private TableColumn<AppointmentType, Integer> numberOfTypes;
    @FXML
    private ComboBox<String> monthSelection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March"
                , "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthSelection.setItems(months);
        
        appointmentType.setCellValueFactory(new PropertyValueFactory<AppointmentType, String>("type"));
        numberOfTypes.setCellValueFactory(new PropertyValueFactory<AppointmentType, Integer>("count"));    
        
    }    

    @FXML
    private void backToReportScreen(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();            
    }

    @FXML
    private void calculateTypes(ActionEvent event) {
        
        ObservableList<AppointmentType> appointmentTypeList = FXCollections.observableArrayList();
        
        String selectedMonth = monthSelection.getValue();
        
        if(selectedMonth != null) {
            
            // Create a filtered list of appointments for the month selected
            FilteredList<Appointment> filteredMonth = new FilteredList<>(AppointmentList);
            filteredMonth.setPredicate(row -> {
                LocalDateTime rowDate = LocalDateTime.ofInstant(row.getStart().toInstant(), row.getStart().getTimeZone().toZoneId());
                return rowDate.getMonth().name().toUpperCase().equals(selectedMonth.toUpperCase());  
            });
            
            // Create ObservableList of the types of appointments in that filtered appointment list
            ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
            for(Appointment appointment: filteredMonth) {
                if(!appointmentTypes.contains(appointment.getType())) {
                    appointmentTypes.add(appointment.getType());
                }
            }
            
            // Loop through filtered month list and create a summary of the number of types of appointments
            for(String type: appointmentTypes) {
                int typeCount = 0;
                for(Appointment appointment: filteredMonth) {
                    if(type.equals(appointment.getType())) {
                        typeCount++;
                    }
                }
                appointmentTypeList.add(new AppointmentType(type, typeCount));
            }
            AppointTypeTable.setItems(appointmentTypeList);
            
        }else {
            errorAlert.setContentText("Select a month");
            errorAlert.showAndWait();
        }
        
      
    }
    
}
