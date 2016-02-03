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
public class Organisation extends Entity {
    

    public PropertyAdmin dateOfCreation;
    public PropertyAdmin location;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    
 public Property[] getPropertiesOrganisation() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
	//list.add(new Property(this.typeOfOrganisation.getName(), this.typeOfOrganisation.getValue_locale(), this.typeOfOrganisation.getType(), this.typeOfOrganisation.getEntity_locale()));
        list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getEntity_locale(), this.location.getType(), this.location.getLang()));
        list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        list.add(new Property(this.dateOfCreation.getName(), this.dateOfCreation.getValue_locale(), this.dateOfCreation.getEntity_locale(), this.dateOfCreation.getType(),this.dateOfCreation.getLang()));
        //list.add(new Property(this.dateCreation.getName(), this.dateCreation.getValue_locale(), this.dateCreation.getType(), this.dateCreation.getEntity_locale()));
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
   public PropertyAdmin[] getPropertiesAdminOrganisation() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	//list.add(this.typeOfOrganisation);
        list.add(this.location);
        list.add(this.dateOfCreation);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
}
