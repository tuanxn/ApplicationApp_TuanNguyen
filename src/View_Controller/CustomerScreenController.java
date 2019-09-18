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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class CustomerScreenController implements Initializable {

    @FXML
    private TableView<?> customerTable;
    @FXML
    private TableColumn<?, ?> customerIdCol;
    @FXML
    private TableColumn<?, ?> activeCol;
    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> phoneCol;
    @FXML
    private TableColumn<?, ?> addressCol;
    @FXML
    private TableColumn<?, ?> address2Col;
    @FXML
    private TableColumn<?, ?> cityCol;
    @FXML
    private TableColumn<?, ?> countryCol;
    @FXML
    private TableColumn<?, ?> postalCodeCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCustomer(ActionEvent event) {
    }

    @FXML
    private void editCustomer(ActionEvent event) {
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
    private void deleteCustomer(ActionEvent event) {
    }
    
}
