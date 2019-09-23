/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author tuanxn
 */
public class AppointmentType {
    private SimpleStringProperty type;
    private SimpleIntegerProperty count;
    
    public AppointmentType(String type, int count) {
        this.type = new SimpleStringProperty(type);
        this.count = new SimpleIntegerProperty(count);
    }
    
    public String getType() {
        return type.get();
    }
    
    public void setType(String typeName) {
        type.set(typeName);
    }
    
    public int getCount() {
        return count.get();
    }
    
    public void setCount(int typeCount) {
        count.set(typeCount);
    }
    
}
