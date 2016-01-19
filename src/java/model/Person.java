/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import java.util.ArrayList;

/**
 *
 * @author loannguyen
 */
public class Person extends Entity {
    public Property birthDate;
    public Property deathDate;
    public Property placeOfBirth;

    
    public Property[] getPropertiesPerson() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public void constructPerson() {
        
    }
    
    public void insertBirthDate(Property p) {
        
    }
    
    public void insertDeathDate(Property p) {
        
    }
    
    public void insertPlaceOfBirth(Property p) {
        
    }
}
