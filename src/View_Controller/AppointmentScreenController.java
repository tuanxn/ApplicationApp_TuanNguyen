/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import static View_Controller.MainScreenController.updated;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.confirmAlert;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class AppointmentScreenController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, Calendar> appointmentStart;
    @FXML
    private TableColumn<Appointment, Calendar> appointmentEnd;
    @FXML
    private TableColumn<Appointment, String> appointmentConsultant;
    @FXML
    private TableColumn<Appointment, String> appointmentCustomer;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment, String> appointmentDescription;
    @FXML
    private TableColumn<Appointment, String> appointmentLocation;
    @FXML
    private TableColumn<Appointment, String> appointmentContact;
    @FXML
    private TableColumn<Appointment, String> appointmentType;
    @FXML
    private TableColumn<Appointment, String> appointmentUrl;    
    @FXML
    private Button editAppointmentButton;
    @FXML
    private Button addAppointmentButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Populate Main Screen table with all appointments
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("startString"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("endString"));
        appointmentConsultant.setCellValueFactory(new PropertyValueFactory<>("userName"));
        appointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
        AppointmentTable.setItems(AppointmentList);
    }    

    @FXML
    private void editAppointment(ActionEvent event) {
        
        try {
            Appointment selectedAppointment=AppointmentTable.getSelectionModel().getSelectedItem();

            System.out.println("Modify Appointment");
            Stage stage;
            Parent root;
            stage=(Stage) editAppointmentButton.getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AppointmentAddEdit.fxml"));
            root = loader.load(); 
            AppointmentAddEditController controller = loader.getController();
            controller.setAppointment(selectedAppointment, "Modify Appointment");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();    
        }catch (Exception e) {
            System.out.println(e.getMessage());
            errorAlert.setContentText("Please select an appointment to modify");
            errorAlert.show();
        }
    }

    @FXML
    private void addAppointment(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("AppointmentAddEdit.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();          
    }

    @FXML
    private void backToMainScreen(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();          
    }

    
    /* 
    The filters for Week and Month are done using Lambda Expressions
    Because these functions would not be used elsewhere, it made more sense
    to create a simple lambda function to save space
    */
    
    @FXML
    private void filterForWeek(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(7);
        FilteredList<Appointment> filteredWeek = new FilteredList<>(AppointmentList);
        filteredWeek.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.ofInstant(row.getStart().toInstant(), row.getStart().getTimeZone().toZoneId());
            return rowDate.isAfter(now) && rowDate.isBefore(nextWeek);
        });
        AppointmentTable.setItems(filteredWeek);
    }

    @FXML
    private void filterForMonth(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonth = now.plusDays(30);
        FilteredList<Appointment> filteredMonth = new FilteredList<>(AppointmentList);
        filteredMonth.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.ofInstant(row.getStart().toInstant(), row.getStart().getTimeZone().toZoneId());
            return rowDate.isAfter(now) && rowDate.isBefore(nextMonth);
        });
        AppointmentTable.setItems(filteredMonth);        
    }

    @FXML
    private void filterForAll(ActionEvent event) {
        AppointmentTable.setItems(AppointmentList);  
    }

    @FXML
    private void deleteAppointment(ActionEvent event) {
        
        try {
            Appointment selectedAppointment=AppointmentTable.getSelectionModel().getSelectedItem();
            
            // If no selection, throw an exception
            if(selectedAppointment == null) {
                throw new Exception("Appointment not selected");
            }

            System.out.println("Delete Appointment");
            // Show confirmation before deleting
            confirmAlert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            // If deletion confirmed
            if (result.get() == ButtonType.OK) {
                // Use CRUD operation to delete from appointment table
                DAO.AppointmentDaoImpl.deleteAppointment(selectedAppointment.getAppointmentId());
                // Get remaining appointments directly from table
                AppointmentList = DAO.AppointmentDaoImpl.getAllAppointments();
                // Repopulate ObservableList of appointmnents
                AppointmentTable.setItems(AppointmentList);
            }
            
            
            
        }catch (Exception e) {
            System.out.println(e.getMessage());
            errorAlert.setContentText("Please select an appointment to delete");
            errorAlert.show();
        } 
        
    }
    
}
