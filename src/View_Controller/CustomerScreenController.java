/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.confirmAlert;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class CustomerScreenController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, Boolean> activeCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> address2Col;
    @FXML
    private TableColumn<Customer, String> cityCol;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private Button editCustomerButton;
    
    boolean referenceExists;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        address2Col.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));

        customerTable.setItems(CustomerList);
    }    

    @FXML
    private void addCustomer(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("CustomerAddEdit.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();            
    }

    @FXML
    private void editCustomer(ActionEvent event) {
        try {
            Customer selectedCustomer=customerTable.getSelectionModel().getSelectedItem();

            System.out.println("Modify Customer");
            Stage stage;
            Parent root;
            stage=(Stage) editCustomerButton.getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("CustomerAddEdit.fxml"));
            root = loader.load(); 
            CustomerAddEditController controller = loader.getController();
            controller.setCustomer(selectedCustomer, "Modify Customer");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();    
        }catch (Exception e) {
            System.out.println(e.getMessage());
            errorAlert.setContentText("Please select a customer to modify");
            errorAlert.show();
        }        
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
        try {
            Customer selectedCustomer=customerTable.getSelectionModel().getSelectedItem();
            
            // If no selection, throw an exception
            if(selectedCustomer == null) {
                throw new Exception("Customer not selected");
            }

            System.out.println("Delete Customer");
            // Show confirmation before deleting
            confirmAlert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            // If deletion confirmed
            if (result.get() == ButtonType.OK) {
                for(Appointment appointment: AppointmentList) {
                    if(appointment.getCustomerId()==selectedCustomer.getCustomerId()) {
                        throw new Exception("Customer included in existing appointments.\nRemove apppointments first.");
                    }
                }
                // Use CRUD operation to delete from customer table
                DAO.CustomerDaoImpl.deleteCustomer(selectedCustomer.getCustomerId());
                
                // Get remaining appointments directly from table
                CustomerList = DAO.CustomerDaoImpl.getAllCustomers();
                
                // Delete any records in dependent tables that were used by the deleted customer record
                // but are no longer being used by other records
                
                referenceExists = false;
                for(Customer customer: CustomerList) {
                    if(customer.getAddressId()==selectedCustomer.getAddressId()) {
                        // Set to true if another customer is using the old address
                        referenceExists = true;
                    }
                }
                // If address could not be found for any other customers
                if(!referenceExists) {
                    // Delete the old address reference from the Address table
                    DAO.AddressDaoImpl.deleteAddress(selectedCustomer.getAddressId());
                }

                // Check to see if the old city name is still being used by any other customer
                referenceExists = false;
                for(Customer customer: CustomerList) {
                    if(customer.getCityCode()==selectedCustomer.getCityCode()) {
                        // Set to true if another customer is using the old city name
                        referenceExists = true;
                    }
                }
                // If city name could not be found for any other customers
                if(!referenceExists) {
                    // Delete the old city reference from the City table
                    DAO.CityDaoImpl.deleteCity(selectedCustomer.getCityCode());
                }

                // Check to see if the old country name is still being used by any other customer
                referenceExists = false;
                for(Customer customer: CustomerList) {
                    if(customer.getCountryCode()==selectedCustomer.getCountryCode()) {
                        // Set to true if another customer is using the old country name
                        referenceExists = true;
                    }
                }
                // If country name could not be found for any other customers
                if(!referenceExists) {
                    // Delete the old country reference from the Country table
                    DAO.CountryDaoImpl.deleteCountry(selectedCustomer.getCountryCode());
                }
                    
                    
                // Repopulate ObservableList of appointmnents
                customerTable.setItems(CustomerList);
            }
            
        }catch (Exception e) {
            System.out.println(e.getMessage());
            errorAlert.setContentText(e.getMessage());
            errorAlert.show();
        }         
    }
    
}
