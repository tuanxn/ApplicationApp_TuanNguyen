/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentapp_tuannguyen;

import Model.Address;
import Model.Appointment;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author tuanxn
 */
public class AppointmentApp_TuanNguyen extends Application {
    
    // Global variable to provide context for logged in user
    public static User loggedInUser;
    public static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public static ObservableList<Customer> CustomerList;
    public static ObservableList<Appointment> AppointmentList;
    public static ObservableList<User> UserList;
    public static ObservableList<Country> CountryList;
    public static ObservableList<City> CityList;
    public static ObservableList<Address> AddressList;    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        
        // UNCOMMENT THE LINE BELOW TO CHANGE LOGIN SCREEN TO SPANISH
//        Locale.setDefault(new Locale("es", "ES"));
        
        
        
        
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");
        
        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Login.fxml"));
            loader.setResources(rb);
            main = loader.load();
        
            Scene scene = new Scene(main);

            stage.setScene(scene);
            stage.show();
            
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        int employeeID;
//        String employeeName, department, hireDate;
//        
//        // Create input
//        Scanner keyboard = new Scanner(System.in);
//        
//        System.out.print("Enter employee id: ");
//        employeeID = keyboard.nextInt();
        
//        System.out.print("Enter employee name: ");
//        employeeName = keyboard.nextLine();
//        System.out.print("Enter department: ");
//        department = keyboard.nextLine();
//        System.out.print("Enter hireDate: ");
//        hireDate = keyboard.nextLine();
        
//        try {
//            DBConnection.makeConnection();
            
            // Write SQL Insert statement
//            String sqlStatement = "INSERT INTO employee_tbl(EmployeeName, Department, HireDate)" +
//                                  "VALUES(" +
//                                  "'" + employeeName + "', " +
//                                  "'" + department + "', " +
//                                  "'" + hireDate + "')";
            
//            // Write SQL DELETE statement
//            String sqlStatement = "DELETE FROM employee_tbl WHERE Employee = " + String.valueOf(employeeID);
//
//            // Execute INSERT statement
//            DAO.Query.makeQuery(sqlStatement);
//             
//            // Write SQL statement
//            sqlStatement = "SELECT * FROM employee_tbl";
//            
//            // Execute statement and create ResultSet object
//            Query.makeQuery(sqlStatement);
//            ResultSet result = Query.getResult();
//            
//            // Retrieve all records
//            while(result.next()) {
//                System.out.println(result.getInt("Employee") + ", ");
//                System.out.println(result.getString("EmployeeName") + ", ");
//                System.out.println(result.getString("Department") + ", ");
//                System.out.println(result.getDate("HireDate") + ", ");
//                System.out.println(result.getTime("HireDate") + ", ");
//                System.out.println();
//            }
            
            
            launch(args);
//            DBConnection.closeConnection();
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex.getMessage());
//            Logger.getLogger(AppointmentApp_TuanNguyen.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}
