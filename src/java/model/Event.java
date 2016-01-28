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
import static model.Connector.insert;

/**
 *
 * @author Robois
 */
public class Event extends Entity{
    public PropertyAdmin dateOfEvent;
    public PropertyAdmin location;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    
    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.dateOfEvent.getName(), this.dateOfEvent.getValue_locale(), this.dateOfEvent.getType(), this.dateOfEvent.getEntity_locale()));
        list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getType(), this.location.getEntity_locale()));
        list.add(new Property(this.description.getName(),this.description.getValue_locale(),this.description.getType(),this.description.getEntity_locale()));
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public PropertyAdmin[] getPropertiesAdminEvent() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
	list.add(this.dateOfEvent);
        list.add(this.location);
        list.add(this.description);
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructEvent() {
        
    }
    
    public void insertDateOfEvent(Property p) {
        insert(this.getURI(), "dbont:date", p.getValue(), p.getType());
    }

    public void insertLocation(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                insert(this.getURI(), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isThePlaceOfEvent", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "axis-datamodel:takesPlaceIn", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isThePlaceOfEvent", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "axis-datamodel:takesPlaceIn", uri1);
                insert(uri1, "axis-datamodel:isThePlaceOfEvent", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
}
