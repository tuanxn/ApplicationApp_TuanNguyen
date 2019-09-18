/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import appointmentapp_tuannguyen.AppointmentApp_TuanNguyen;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
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
public class MainScreenController implements Initializable {

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
    private TableColumn<Appointment, String> appointmentType;
    @FXML
    private ComboBox<String> monthFilter;
    @FXML
    private ComboBox<String> weekFilter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Populate Main Screen table with upcoming user appointments
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("startString"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("endString"));
        appointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        try {
            AppointmentTable.setItems(DAO.AppointmentDaoImpl.getUserAppointments(AppointmentApp_TuanNguyen.loggedInUser.getUserId()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }    

    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void editCustomers(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();        
    }

    @FXML
    private void editAppointments(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();          
    }

    @FXML
    private void viewReports(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();          
    }
    
}
