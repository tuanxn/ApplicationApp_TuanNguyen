/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.rb;
import java.awt.Desktop;
import java.io.File;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class MainScreenController implements Initializable {

    // Create flag to indicate whether tableviews are updated
    // updated flag will be set to false after a record in any table is updated
    // updated flag will be set to true if the flag is false and we access a tableview
    static boolean updated;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
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

    @FXML
    private void logOut(ActionEvent event) throws IOException{
        
        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Login.fxml"));
            loader.setResources(rb);
            main = loader.load();
        
            Scene scene = new Scene(main);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch (IOException ex) {
            ex.printStackTrace();
        }        
    }

    @FXML
    private void openLog(ActionEvent event) throws IOException{
        String filename = "User_Login_Log.txt";    
        File file = new File(filename);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }
    
}
