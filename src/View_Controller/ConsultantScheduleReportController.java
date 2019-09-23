/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.User;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import java.io.IOException;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class ConsultantScheduleReportController implements Initializable {

    @FXML
    private ComboBox<String> consultant;
    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, Calendar> appointmentStart;
    @FXML
    private TableColumn<Appointment, Calendar> appointmentEnd;
    @FXML
    private TableColumn<Appointment, String> appointmentCustomer;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment,String> appointmentType;
    @FXML
    private TableColumn<Appointment,String> appointmentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> userNameList = FXCollections.observableArrayList();
        for(User user: UserList) {
            userNameList.add(user.getUserName());
        }
        consultant.setItems(userNameList);
        
        //Populate Main Screen table with all appointments
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("startString"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("endString"));
        appointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        AppointmentTable.setItems(AppointmentList);        
        
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
    private void filterForConsultant(ActionEvent event) {
        
        String selectedConsultant = consultant.getValue();
        
        if(selectedConsultant != null) {
            FilteredList<Appointment> filteredForConsultant = new FilteredList<>(AppointmentList);
            filteredForConsultant.setPredicate(row -> {
                return row.getUserName().equals(consultant.getValue());
            });
            AppointmentTable.setItems(filteredForConsultant);               
        }else {
            errorAlert.setContentText("Select a consultant");
            errorAlert.showAndWait();
        }
        
     
    }
    
}
