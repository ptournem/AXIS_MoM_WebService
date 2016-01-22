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
 * @author Robois
 */
public class Event extends Entity{
    public PropertyAdmin dateOfEvent;
  //  public PropertyAdmin typeOfevent;
    public PropertyAdmin location;
    public PropertyAdmin description;

    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.dateOfEvent.getName(), this.dateOfEvent.getValue_locale(), this.dateOfEvent.getType(), this.dateOfEvent.getEntity_locale()));
        list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getType(), this.location.getEntity_locale()));
     //  list.add(new Property(this.typeOfevent.getName(),this.typeOfevent.getValue_locale(),this.typeOfevent.getType(),this.typeOfevent.getEntity_locale()));
        list.add(new Property(this.description.getName(),this.description.getValue_locale(),this.description.getType(),this.description.getEntity_locale()));
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public PropertyAdmin[] getPropertiesAdminEvent() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.dateOfEvent);
        list.add(this.location);
      //  list.add(this.typeOfevent);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructEvent() {
        
    }
    
    public void insertDateOfEvent(Property p) {
        
    }

    public void insertLocation(Property p) {
        
    }
}
