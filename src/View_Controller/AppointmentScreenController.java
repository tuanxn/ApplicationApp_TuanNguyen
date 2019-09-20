/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import static View_Controller.MainScreenController.updated;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
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
    
    static ObservableList<Appointment> appointmentList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            appointmentList = DAO.AppointmentDaoImpl.getAllAppointments();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        //Populate Main Screen table with upcoming user appointments
        // DOUBLE CHECK THAT THIS WILL CATCH UPDATES
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
        AppointmentTable.setItems(appointmentList);
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

    @FXML
    private void filterForWeek(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(7);
        FilteredList<Appointment> filteredWeek = new FilteredList<>(appointmentList);
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
        FilteredList<Appointment> filteredMonth = new FilteredList<>(appointmentList);
        filteredMonth.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.ofInstant(row.getStart().toInstant(), row.getStart().getTimeZone().toZoneId());
            return rowDate.isAfter(now) && rowDate.isBefore(nextMonth);
        });
        AppointmentTable.setItems(filteredMonth);        
    }

    @FXML
    private void filterForAll(ActionEvent event) {
        AppointmentTable.setItems(appointmentList);  
    }
    
}
