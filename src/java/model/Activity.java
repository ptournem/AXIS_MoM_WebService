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
 * @author APP-Riad.Belmahi
 */
public class Activity extends Entity {
    public PropertyAdmin description;
    public PropertyAdmin sameAs;

    
    public Property[] getPropertiesActivity() {
        ArrayList<Property> list = new ArrayList<Property>();

        list.add(new Property(this.description.getName(),this.description.getValue_locale(),this.description.getEntity_locale(), this.description.getType(),this.description.getLang()));
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
     public PropertyAdmin[] getPropertiesAdminActivity() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
       
}
