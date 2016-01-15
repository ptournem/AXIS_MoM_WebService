/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Dialog.Entity;
import Dialog.Property;
import org.apache.jena.rdf.model.*;

/**
 *
 * @author APP-Riad.Belmahi
 */

public class Oeuvre extends Entity {

    public String dateCreation;
    public String location;
    public String author;

    public Oeuvre(String URI) {
        constructEntity(URI);
        
        //construct de dateCreation
        //this.dateCreation = blabla
        
        
        //construct de location
        
        //construct de author
    }

    public void insertDateCreation(Property p) {
        
    }

    public void insertLocation(Property p) {
        
    }
    
    public void insertAuthor(Property p) {
        
    }
    
    

}
