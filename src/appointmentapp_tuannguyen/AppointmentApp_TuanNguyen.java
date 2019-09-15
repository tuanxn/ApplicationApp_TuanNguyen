/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentapp_tuannguyen;

import static appointmentapp_tuannguyen.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author tuanxn
 */
public class AppointmentApp_TuanNguyen extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int employeeID;
        String employeeName, department, hireDate;
        
        // Create input
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Enter employee id: ");
        employeeID = keyboard.nextInt();
        
//        System.out.print("Enter employee name: ");
//        employeeName = keyboard.nextLine();
//        System.out.print("Enter department: ");
//        department = keyboard.nextLine();
//        System.out.print("Enter hireDate: ");
//        hireDate = keyboard.nextLine();
        
        try {
            DBConnection.makeConnection();
            
            // Write SQL Insert statement
//            String sqlStatement = "INSERT INTO employee_tbl(EmployeeName, Department, HireDate)" +
//                                  "VALUES(" +
//                                  "'" + employeeName + "', " +
//                                  "'" + department + "', " +
//                                  "'" + hireDate + "')";
            
            // Write SQL DELETE statement
            String sqlStatement = "DELETE FROM employee_tbl WHERE Employee = " + String.valueOf(employeeID);

            // Execute INSERT statement
            Query.makeQuery(sqlStatement);
             
            // Write SQL statement
            sqlStatement = "SELECT * FROM employee_tbl";
            
            // Execute statement and create ResultSet object
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            
            // Retrieve all records
            while(result.next()) {
                System.out.println(result.getInt("Employee") + ", ");
                System.out.println(result.getString("EmployeeName") + ", ");
                System.out.println(result.getString("Department") + ", ");
                System.out.println(result.getDate("HireDate") + ", ");
                System.out.println(result.getTime("HireDate") + ", ");
                System.out.println();
            }
            
            
            launch(args);
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Logger.getLogger(AppointmentApp_TuanNguyen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
