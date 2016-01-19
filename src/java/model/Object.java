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
import static model.Connector.*;

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

    public void insertDateCreation(Property p) {
        
    }
    
    public void insertLocation(Property p) {

        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "axis-datamodel:takePlaceIn", uri1);
                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:takePlaceIn", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "axis-datamodel:takePlaceIn", uri1);
                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertAuthor(Property p) {
        
        String uri1 = null;
        
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(this.getURI(), "axis-datamodel:isPerformedBy", uri1);
                insert(uri1, "axis-datamodel:performs", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:takePlaceIn", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(this.getURI(), "axis-datamodel:isPerformedBy", uri1);
                insert(uri1, "axis-datamodel:performs", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
        
    }
    

}
