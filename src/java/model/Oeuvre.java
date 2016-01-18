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

    public Oeuvre() {
        
        
        this.constructEntity();
        
        //construct de dateCreation
        //this.dateCreation = blablabla
        
        
        //construct de location
        
        //construct de author
    }

    public void insertDateCreation(Property p) {
        
    }

    public void insertLocation(Property p) {
        if(p.getType() == "uri") {
            
        }
        
        else {
            
        } 
    }
    
    public void insertAuthor(Property p) {
        if(p.getType() == "uri") {
            
        }
        
        else {
            
        } 
    }
    
    

}
