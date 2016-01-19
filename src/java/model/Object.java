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

public class Object extends Entity {

    public PropertyAdmin dateCreation;
    public PropertyAdmin location;
    public PropertyAdmin author;

    
    public PropertyAdmin[] getPropertiesObject() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.author);
        list.add(this.location);
        list.add(this.dateCreation);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructObject() {
        
    }

    public void insertDateCreation(PropertyAdmin p) {
        
    }
    
    public void insertLocation(PropertyAdmin p) {
        
    }
    
    public void insertAuthor(PropertyAdmin p) {
        
    }

}
