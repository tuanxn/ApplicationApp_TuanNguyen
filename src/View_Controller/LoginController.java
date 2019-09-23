/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.User;
import Utilities.TimeFiles;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.UserList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CountryList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CityList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AddressList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.rb;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class LoginController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Button exit;
    @FXML
    private Label userLabel;
    @FXML
    private Label passwordLabel;
    private String usernameAlert;
    private String passwordAlert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       label.setText(rb.getString("label"));
       userLabel.setText(rb.getString("user"));
       passwordLabel.setText(rb.getString("password"));
       login.setText(rb.getString("button"));
       exit.setText(rb.getString("exit"));
       usernameAlert = rb.getString("usernameAlert");
       passwordAlert = rb.getString("passwordAlert");
    }    

    @FXML
    private void loginButton(ActionEvent event) {
        
        String userNameText = userName.getText();
        String passwordText = password.getText();
        
        try {
            User retrievedUser = DAO.UserDaoImpl.getUser(userNameText);
            if(retrievedUser == null) {
                
                // Track unsuccessful user login in the log file
                String filename = "User_Login_Log.txt";
                File file = new File(filename);

                FileWriter fwriter = new FileWriter(filename, true);
                PrintWriter outputFile = new PrintWriter(fwriter);
                outputFile.println(userNameText + " failed to log in on " + Calendar.getInstance().getTime());
                outputFile.close();
                
                throw new Exception(usernameAlert);
                
            }else if(!passwordText.equals(retrievedUser.getPassword())) {
                
                // Track unsuccessful user login in the log file
                String filename = "User_Login_Log.txt";
                File file = new File(filename);

                FileWriter fwriter = new FileWriter(filename, true);
                PrintWriter outputFile = new PrintWriter(fwriter);
                outputFile.println(userNameText + " failed to log in on " + Calendar.getInstance().getTime());
                outputFile.close();
                
                throw new Exception(passwordAlert);
                
            }else {
                
                // Assign logged in User to the global variable loggedInUser
                // This will provide context to who is logged in during the session
                loggedInUser = retrievedUser;
                UserList = DAO.UserDaoImpl.getAllUsers();
                // Since country, city, and address cascade (in that order) into Customer, we must load those first
                CountryList = DAO.CountryDaoImpl.getAllCountries();
                CityList = DAO.CityDaoImpl.getAllCities();
                AddressList = DAO.AddressDaoImpl.getAllAddresses();
                CustomerList = DAO.CustomerDaoImpl.getAllCustomers();
                // Since user and customer list are used in appointment list, we must get those first
                AppointmentList = DAO.AppointmentDaoImpl.getAllAppointments();
                
                // Track successful user login in the log file
                String filename = "User_Login_Log.txt";
                File file = new File(filename);

                FileWriter fwriter = new FileWriter(filename, true);
                PrintWriter outputFile = new PrintWriter(fwriter);
                outputFile.println(loggedInUser.getUserName() + " successfully logged in on " + Calendar.getInstance().getTime());
                outputFile.close();

                              

                // Change to MainScreen
                Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene part_screen_scene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(part_screen_scene);
                app_stage.show();
                
                
                // Alert message if there is an appointment in the next 15 minutes
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime next15 = now.plusMinutes(15);
                // Create filtered list of appointments in next 15 minutes
                FilteredList<Appointment> filteredWeek = new FilteredList<>(AppointmentList);
                // Using lambda expression to create filtered list on the fly
                // Lambda expression gives us the ability to define a function without declaring it
                filteredWeek.setPredicate(row -> {
                    LocalDateTime rowDate = LocalDateTime.ofInstant(row.getStart().toInstant(), row.getStart().getTimeZone().toZoneId());
                    return rowDate.isAfter(now) && rowDate.isBefore(next15);
                });
                // Get size of list and if it is greater than 1, then there is an appointment in next 15 minutes
                int appointmentIn15 = filteredWeek.size();
                if(appointmentIn15 > 0) {
                    errorAlert.setContentText("There is an appointment within the next 15 minutes!");
                    errorAlert.showAndWait();
                }                
            }
        } catch (Exception e) {
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
        
    }

    @FXML
    private void exitButton(ActionEvent event) {
        System.exit(0);
    }
    
}
