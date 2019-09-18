/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private ComboBox<?> appointmentCustomer;
    @FXML
    private ComboBox<?> appointmentStart;
    @FXML
    private ComboBox<?> appointmentEnd;
    @FXML
    private ComboBox<?> appointmentConsultant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelAppointment(ActionEvent event) {
    }

    @FXML
    private void saveAppointment(ActionEvent event) {
    }
    
}
