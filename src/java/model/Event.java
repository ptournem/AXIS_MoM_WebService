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
 * @author Robois
 */
public class Event extends Entity{
    public Property dateOfEvent;
    public Property location;

    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(this.dateOfEvent);
        list.add(this.location);
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public void constructEvent() {
        
    }
    
    public void insertDateOfEvent(Property p) {
        
    }

    public void insertLocation(Property p) {
        
    }
}
