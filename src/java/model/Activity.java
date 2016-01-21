/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Dialog.Property;
import Dialog.PropertyAdmin;
import java.util.ArrayList;

/**
 *
 * @author APP-Riad.Belmahi
 */
public class Activity {
    public PropertyAdmin description;
     public PropertyAdmin dateOfactivity;
    public PropertyAdmin typeOfactivity;
    public PropertyAdmin location;

    
    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.dateOfactivity.getName(), this.dateOfactivity.getValue_locale(), this.dateOfactivity.getType(), this.dateOfactivity.getEntity_locale()));
        list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getType(), this.location.getEntity_locale()));
       list.add(new Property(this.typeOfactivity.getName(),this.typeOfactivity.getValue_locale(),this.typeOfactivity.getType(),this.typeOfactivity.getEntity_locale()));
        list.add(new Property(this.description.getName(),this.description.getValue_locale(),this.description.getType(),this.description.getEntity_locale()));
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
     public PropertyAdmin[] getPropertiesAdminEvent() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.dateOfactivity);
        list.add(this.location);
        list.add(this.typeOfactivity);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
       
}
