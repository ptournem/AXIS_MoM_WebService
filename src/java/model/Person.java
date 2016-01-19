/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import java.util.ArrayList;

/**
 *
 * @author loannguyen
 */
public class Person extends Entity {
    public PropertyAdmin birthDate;
    public PropertyAdmin deathDate;
    public PropertyAdmin placeOfBirth;

    
    public PropertyAdmin[] getPropertiesPerson() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructPerson() {
        
    }
    
    public void insertBirthDate(PropertyAdmin p) {
        
    }
    
    public void insertDeathDate(PropertyAdmin p) {
        
    }
    
    public void insertPlaceOfBirth(PropertyAdmin p) {
        
    }
}
