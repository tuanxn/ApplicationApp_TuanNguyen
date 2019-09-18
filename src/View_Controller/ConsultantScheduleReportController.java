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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class ConsultantScheduleReportController implements Initializable {

    @FXML
    private ComboBox<?> consultant;
    @FXML
    private TableView<?> AppointmentTable;
    @FXML
    private TableColumn<?, ?> appointmentStart;
    @FXML
    private TableColumn<?, ?> appointmentEnd;
    @FXML
    private TableColumn<?, ?> appointmentCustomer;
    @FXML
    private TableColumn<?, ?> appointmentTitle;
    @FXML
    private TableColumn<?, ?> appointmentType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToReportScreen(ActionEvent event) {
    }
    
}
