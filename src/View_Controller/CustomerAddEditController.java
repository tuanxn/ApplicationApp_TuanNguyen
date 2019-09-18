/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class CustomerAddEditController implements Initializable {

    @FXML
    private TextField customerId;
    @FXML
    private ToggleButton customerActive;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerPhone;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerAddress2;
    @FXML
    private TextField customerCity;
    @FXML
    private TextField customerCountry;
    @FXML
    private TextField customerPostalCode;
    @FXML
    private Label customerScreenType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
