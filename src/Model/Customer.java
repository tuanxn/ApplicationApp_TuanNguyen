/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.AddressList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CityList;
import static appointmentapp_tuannguyen.AppointmentApp_TuanNguyen.CountryList;

/**
 *
 * @author tnguy
 */
public class Customer {
    private int customerId;
    private String customerName;
    private int addressId;
    private boolean active;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    private String customerAddress;
    private String customerAddress2;
    private String customerPhone;
    private int cityCode;
    private String customerCity;
    private int countryCode;
    private String customerCountry;
    private String postal;
    
    public Customer(int customerId, String customerName, int addressId, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public int getAddressId() {
        return addressId;
    }
    
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Calendar getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Calendar getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }
    
    public void setCustomerAddress() throws Exception {
        for(Address address: AddressList) {
            if(address.getAddressId()==addressId) {
                this.customerAddress = address.getAddress();
            }
        }                
    }
    
    public String getCustomerAddress2() {
        return customerAddress2;
    }    
    
    public void setCustomerAddress2() throws Exception {
        for(Address address: AddressList) {
            if(address.getAddressId()==addressId) {
                this.customerAddress2 = address.getAddress2();
            }
        }        
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }    
    
    public void setCustomerPhone() throws Exception {
        for(Address address: AddressList) {
            if(address.getAddressId()==addressId) {
                this.customerPhone = address.getPhone();
            }
        }        
    }    
    
    public String getCustomerCity() {
        return customerCity;
    }    
    
    public void setCustomerCity() throws Exception {
        for(City city: CityList) {
            if(city.getCityId()==cityCode) {
                this.customerCity = city.getCity();
            }
        }        
    }    

    public String getCustomerCountry() {
        return customerCountry;
    }    
    
    public void setCustomerCountry() throws Exception {
        for(Country country: CountryList) {
            if(country.getCountryId()==countryCode) {
                this.customerCountry = country.getCountry();
            }
        }        
    } 
    
    public String getCustomerPostal() {
        return postal;
    }    
    
    public void setCustomerPostal() throws Exception {
        for(Address address: AddressList) {
            if(address.getAddressId()==addressId) {
                this.postal = address.getPostalCode();
            }
        }                
    }     
    
    public int getCityCode() {
        return cityCode;
    }    
    
    public void setCityCode() throws Exception {
        for(Address address: AddressList) {
            if(address.getAddressId()==addressId) {
                this.cityCode = address.getCityId();
            }
        }        
    }    
    
    public int getCountryCode() {
        return countryCode;
    }    
    
    public void setCountryCode() throws Exception {
        for(City city: CityList) {
            if(city.getCityId()==cityCode) {
                this.countryCode = city.getCountryId();
            }
        }        
    }          
    
}
