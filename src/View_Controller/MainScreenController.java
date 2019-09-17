/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<?, ?> customerName;
    @FXML
    private TableColumn<?, ?> title;
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private TableColumn<?, ?> start;
    @FXML
    private TableColumn<?, ?> end;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }
    
}
