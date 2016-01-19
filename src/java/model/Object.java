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
 * @author APP-Riad.Belmahi
 */

public class Object extends Entity {

    public Property dateCreation;
    public Property location;
    public Property author;

    
    public Property[] getPropertiesObject() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(this.author);
        list.add(this.location);
        list.add(this.dateCreation);
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public void constructObject() {
        
    }

    public void insertDateCreation(Property p) {
        
    }
    
    public void insertLocation(Property p) {
        
    }
    
    public void insertAuthor(Property p) {
        
    }

}
