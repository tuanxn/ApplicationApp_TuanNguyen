/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class AppointmentScreenController implements Initializable {

    @FXML
    private TableView<?> AppointmentTable;
    @FXML
    private ComboBox<?> monthFilter;
    @FXML
    private ComboBox<?> weekFilter;
    @FXML
    private TableColumn<?, ?> appointmentStart;
    @FXML
    private TableColumn<?, ?> appointmentEnd;
    @FXML
    private TableColumn<?, ?> appointmentConsultant;
    @FXML
    private TableColumn<?, ?> appointmentCustomer;
    @FXML
    private TableColumn<?, ?> appointmentTitle;
    @FXML
    private TableColumn<?, ?> appointmentDescription;
    @FXML
    private TableColumn<?, ?> appointmentLocation;
    @FXML
    private TableColumn<?, ?> appointmentContact;
    @FXML
    private TableColumn<?, ?> appointmentType;
    @FXML
    private TableColumn<?, ?> appointmentUrl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void editAppointment(ActionEvent event) {
    }

    @FXML
    private void addAppointment(ActionEvent event) {
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
    
}
