/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javafx.collections.ObservableList;

/**
 *
 * @author tuanxn
 */
public interface AppointmentInterface {
    
    // Retrieve record(s) from datatable
    ObservableList getFromTable();

    // Add to database table
    int addToTable(int Id);
    
    // Update database table record
    void updateTable(int Id);
    
    // Delete from database table
    void deleteFromTable();
    
}
