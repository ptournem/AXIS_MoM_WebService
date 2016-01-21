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
public class Place {
public PropertyAdmin postalCode;
public PropertyAdmin region;
public PropertyAdmin country;
public PropertyAdmin description;
//public PropertyAdmin birthPlaceOf;
//public PropertyAdmin locationOf;

 public Property[] getPropertiesPlace() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
	list.add(new Property(this.postalCode.getName(), this.postalCode.getValue_locale(), this.postalCode.getType(), this.postalCode.getEntity_locale()));
        list.add(new Property(this.region.getName(), this.region.getValue_locale(), this.region.getType(), this.region.getEntity_locale()));
        list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getType(), this.description.getEntity_locale()));
        list.add(new Property(this.country.getName(), this.country.getValue_locale(), this.country.getType(), this.country.getEntity_locale()));
        //list.add(new Property(this.birthPlaceOf.getName(), this.birthPlaceOf.getValue_locale(), this.birthPlaceOf.getType(), this.birthPlaceOf.getEntity_locale()));
	//list.add(new Property(this.locationOf.getName(), this.locationOf.getValue_locale(), this.locationOf.getType(), this.locationOf.getEntity_locale()));
	
        Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
 
   public PropertyAdmin[] getPropertiesAdminPlace() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.postalCode);
        list.add(this.region);
        list.add(this.country);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
}
