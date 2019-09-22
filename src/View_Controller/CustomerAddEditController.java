/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AddressList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AppointmentList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CityList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CountryList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CustomerList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.loggedInUser;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.errorAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tuanxn
 */
public class CustomerAddEditController implements Initializable {

    @FXML
    private TextField customerId;
    @FXML
    private CheckBox customerActive;
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
    
    // Created to hold instance of original Customer record being modified
    Customer modifiedCustomer;
    // Flag to indicate if any old references need to be kept in the system
    boolean referenceExists;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCustomer(Customer customer, String screenType) {
        
        modifiedCustomer = customer;
        
        customerScreenType.setText(screenType);
        customerId.setText(Integer.toString(customer.getCustomerId()));
        if(customer.getActive()) {
            customerActive.setSelected(true);
        }
        customerName.setText(customer.getCustomerName());
        customerPhone.setText(customer.getCustomerPhone());
        customerAddress.setText(customer.getCustomerAddress());
        customerAddress2.setText(customer.getCustomerAddress2());
        customerCity.setText(customer.getCustomerCity());
        customerCountry.setText(customer.getCustomerCountry());
        customerPostalCode.setText(customer.getCustomerPostal());
    }

    @FXML
    private void saveCustomer(ActionEvent event) {
        
        String errorMessage = "";
        boolean isError = false;
        String numberPattern = ".*\\d+.*";
        String letterPattern = ".*[a-zA-Z]+.*";
        Pattern regexNumberPattern = Pattern.compile(numberPattern);
        Pattern regexLetterPattern = Pattern.compile(letterPattern);
        Matcher matcher;
        
        try {
            // Grabs whether customer is active and converts to tinyint for DB
            boolean isActive = customerActive.selectedProperty().get();

            String saveName = customerName.getText();
            if(saveName.isEmpty()) {
                errorMessage += "Name not set \n";
                isError = true;
            }
            String savePhone = customerPhone.getText();
            matcher = regexLetterPattern.matcher(savePhone);
            if(savePhone.isEmpty()) {
                errorMessage += "Phone not set \n";
                isError = true;
            }else if(matcher.find()) {
                errorMessage += "Phone cannot have alphabet characters \n";
                isError = true;
            }        
            String saveAddress = customerAddress.getText();
            if(saveAddress.isEmpty()) {
                errorMessage += "Address not set \n";
                isError = true;
            }        
            String saveAddress2 = customerAddress2.getText();    
            String saveCity = customerCity.getText();
            matcher = regexNumberPattern.matcher(saveCity);
            if(saveCity.isEmpty()) {
                errorMessage += "City not set \n";
                isError = true;
            }else if(matcher.find()) {
                errorMessage += "City cannot have numerical characters \n";
                isError = true;
            }                
            String saveCountry = customerCountry.getText();
            matcher = regexNumberPattern.matcher(saveCountry);
            if(saveCountry.isEmpty()) {
                errorMessage += "Country not set \n";
                isError = true;
            }else if(matcher.find()) {
                errorMessage += "Country cannot have numerical characters \n";
                isError = true;
            }   
            String savePostal = customerPostalCode.getText();
            if(savePostal.isEmpty()) {
                errorMessage += "Postal Code not set \n";
                isError = true;
            }
            
            int countryId = 0;
            int cityId = 0;
            int addressId = 0;
            
            if(isError) {
                throw new Exception(errorMessage);
            }else { 
                if(customerScreenType.getText().equals("Modify Customer")){
                    
                    countryId = modifiedCustomer.getCountryCode();
                    cityId = modifiedCustomer.getCityCode();
                    addressId = modifiedCustomer.getAddressId();
                    
                    // Removing the modified customer from the list of customers so we can check to see
                    // if we can remove any old references that were changed
                    CustomerList.remove(modifiedCustomer);
                    
                    // If country name input is not the same as before
                    if(!(modifiedCustomer.getCustomerCountry().toUpperCase().equals(saveCountry.toUpperCase()))) {                        
                        referenceExists = false;      
                        // Check in country list if new country input already exists (say another customer is using it)
                        for(Country country: CountryList) {
                            if((country.getCountry().toUpperCase().equals(saveCountry.toUpperCase()))) {
                                // Set flag to true if we find it
                                referenceExists = true;                            
                            }
                        }
                        // If the new country name does not already exist, create a record in the Country table
                        if(!referenceExists) {
                            DAO.CountryDaoImpl.addCountry(saveCountry.toUpperCase(), loggedInUser.getUserName(), loggedInUser.getUserName());    
                            // Get countryId for new country added to Country table
                        }
                    }
                    countryId = DAO.CountryDaoImpl.getCountry(saveCountry.toUpperCase()).getCountryId();
                    System.out.println(countryId);
                        
                    // If city name input is not the same as before
                    if(!(modifiedCustomer.getCustomerCity().toUpperCase().equals(saveCity.toUpperCase())
                            && modifiedCustomer.getCountryCode()==countryId)) {                        
                        referenceExists = false;      
                        // Check in city list if new city input already exists (say another customer is using it)
                        for(City city: CityList) {
                            if((city.getCity().toUpperCase().equals(saveCity.toUpperCase())) &&
                                    city.getCountryId()==countryId) {
                                // Set flag to true if we find it
                                referenceExists = true;                            
                            }
                        }
                        // If the new city name does not already exist, create a record in the City table
                        if(!referenceExists) {
                            DAO.CityDaoImpl.addCity(saveCity.toUpperCase(), countryId, loggedInUser.getUserName(), loggedInUser.getUserName());
                            // Get cityId for new country added to City table
                          
                        }
                    }                   
                    cityId = DAO.CityDaoImpl.getCity(saveCity.toUpperCase(), countryId).getCityId();
                    System.out.println(cityId);     
                    // If address input is not the same as before
                    if(!(modifiedCustomer.getCustomerPhone().toUpperCase().equals(savePhone.toUpperCase()) &&
                            modifiedCustomer.getCustomerAddress().toUpperCase().equals(saveAddress.toUpperCase()) &&
                            modifiedCustomer.getCustomerAddress2().toUpperCase().equals(saveAddress2.toUpperCase()) &&
                            modifiedCustomer.getCustomerPostal().toUpperCase().equals(savePostal.toUpperCase()) && 
                            modifiedCustomer.getCustomerCity().toUpperCase()==saveCity.toUpperCase())) {                        
                        referenceExists = false;      
                        // Check in address list if new address input already exists (say another customer is using it)
                        for(Address address: AddressList) {
                            if(address.getPhone().toUpperCase().equals(savePhone.toUpperCase()) &&
                                    address.getAddress().toUpperCase().equals(saveAddress.toUpperCase()) &&
                                    address.getAddress2().toUpperCase().equals(saveAddress2.toUpperCase()) &&
                                    address.getPostalCode().toUpperCase().equals(savePostal.toUpperCase()) &&
                                    address.getCityId()==cityId){
                                // Set flag to true if we find it
                                referenceExists = true;                            
                            }
                        }
                        // If the new address does not already exist, create a record in the Address table
                        if(!referenceExists) {
                            DAO.AddressDaoImpl.addAddress(saveAddress.toUpperCase(), saveAddress2.toUpperCase(), cityId, savePostal.toUpperCase(), savePhone.toUpperCase(), loggedInUser.getUserName(), loggedInUser.getUserName());
                            // Get addressId for new address added to Address table                          
                        }
                    }
                    addressId = DAO.AddressDaoImpl.getAddress(saveAddress.toUpperCase(), saveAddress2.toUpperCase(), cityId, savePostal.toUpperCase(), savePhone.toUpperCase()).getAddressId(); 
                    
                    // Update the customer record
                    DAO.CustomerDaoImpl.updateCustomer(Integer.parseInt(customerId.getText()), saveName.toUpperCase(), addressId, isActive, loggedInUser.getUserName());
                    // Go through and remove any unused records
                    
                    // Check to see if the old address is still being used by any other customer
                    referenceExists = false;
                    for(Customer customer: CustomerList) {
                        if(customer.getAddressId()==modifiedCustomer.getAddressId()) {
                            // Set to true if another customer is using the old address
                            referenceExists = true;
                        }
                    }
                    // If address could not be found for any other customers
                    if(!referenceExists) {
                        // Delete the old address reference from the Address table
                        DAO.AddressDaoImpl.deleteAddress(modifiedCustomer.getAddressId());
                    }
                    
                    // Check to see if the old city name is still being used by any other customer
                    referenceExists = false;
                    for(Customer customer: CustomerList) {
                        if(customer.getCityCode()==modifiedCustomer.getCityCode()) {
                            // Set to true if another customer is using the old city name
                            referenceExists = true;
                        }
                    }
                    // If city name could not be found for any other customers
                    if(!referenceExists) {
                        // Delete the old city reference from the City table
                        DAO.CityDaoImpl.deleteCity(modifiedCustomer.getCityCode());
                    }
                        
                    // Check to see if the old country name is still being used by any other customer
                    referenceExists = false;
                    for(Customer customer: CustomerList) {
                        if(customer.getCountryCode()==modifiedCustomer.getCountryCode()) {
                            // Set to true if another customer is using the old country name
                            referenceExists = true;
                        }
                    }
                    // If country name could not be found for any other customers
                    if(!referenceExists) {
                        // Delete the old country reference from the Country table
                        DAO.CountryDaoImpl.deleteCountry(modifiedCustomer.getCountryCode());
                    }                        
                        

                    
                }else {

                    // NEW CUSTOMER RECORD
                    // If we are adding a new customer record altogether
                    referenceExists = false;      
                    // Check in country list if new country input already exists (say another customer is using it)
                    for(Country country: CountryList) {
                        if((country.getCountry().toUpperCase().equals(saveCountry.toUpperCase()))) {
                            // Set flag to true if we find it
                            referenceExists = true;                            
                        }
                    }
                    // If the new country name does not already exist, create a record in the Country table
                    if(!referenceExists) {
                        DAO.CountryDaoImpl.addCountry(saveCountry.toUpperCase(), loggedInUser.getUserName(), loggedInUser.getUserName());    
                        // Get countryId for new country added to Country table
                    }
                    countryId = DAO.CountryDaoImpl.getCountry(saveCountry.toUpperCase()).getCountryId();
                    
                      
                    referenceExists = false;      
                    // Check in city list if new city input already exists (say another customer is using it)
                    for(City city: CityList) {
                        if((city.getCity().toUpperCase().equals(saveCity.toUpperCase())) && 
                                city.getCountryId()==countryId) {
                            // Set flag to true if we find it
                            referenceExists = true;                            
                        }
                    }
                    // If the new city name does not already exist, create a record in the City table
                    if(!referenceExists) {
                        DAO.CityDaoImpl.addCity(saveCity.toUpperCase(), countryId, loggedInUser.getUserName(), loggedInUser.getUserName());
                        // Get cityId for new country added to City table                                                   
                    }
                    cityId = DAO.CityDaoImpl.getCity(saveCity.toUpperCase(), countryId).getCityId(); 
;
                                        
                    referenceExists = false;      
                    // Check in address list if new address input already exists (say another customer is using it)
                    for(Address address: AddressList) {
                        if(address.getPhone().toUpperCase().equals(savePhone.toUpperCase()) &&
                                address.getAddress().toUpperCase().equals(saveAddress.toUpperCase()) &&
                                address.getAddress2().toUpperCase().equals(saveAddress2.toUpperCase()) &&
                                address.getPostalCode().toUpperCase().equals(savePostal.toUpperCase()) &&
                                address.getCityId()==cityId) {
                            // Set flag to true if we find it
                            referenceExists = true;                            
                        }
                    }
                    // If the new address does not already exist, create a record in the Address table
                    if(!referenceExists) {
                        DAO.AddressDaoImpl.addAddress(saveAddress.toUpperCase(), saveAddress2.toUpperCase(), cityId, savePostal.toUpperCase(), savePhone.toUpperCase(), loggedInUser.getUserName(), loggedInUser.getUserName());
                        // Get addressId for new address added to Address table                                                    
                    }

                    addressId = DAO.AddressDaoImpl.getAddress(saveAddress.toUpperCase(), saveAddress2.toUpperCase(), cityId, savePostal.toUpperCase(), savePhone.toUpperCase()).getAddressId();

                    DAO.CustomerDaoImpl.addCustomer(saveName.toUpperCase(), addressId, isActive, loggedInUser.getUserName(), loggedInUser.getUserName());
                }
                
                // After successful addition or modification
                // Repull fresh tables from SQL DB
                CountryList = DAO.CountryDaoImpl.getAllCountries();
                CityList = DAO.CityDaoImpl.getAllCities();
                AddressList = DAO.AddressDaoImpl.getAllAddresses();
                CustomerList = DAO.CustomerDaoImpl.getAllCustomers();
                AppointmentList = DAO.AppointmentDaoImpl.getAllAppointments();                
                
                // Return to Customer screen
                Parent parent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
                Scene part_screen_scene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(part_screen_scene);
                app_stage.show();   
                
            }
            
        }catch (Exception e) {
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();            
        }
        
        
        
    }

    @FXML
    private void cancelEditCustomer(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene part_screen_scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(part_screen_scene);
        app_stage.show();         
    }
    
}
