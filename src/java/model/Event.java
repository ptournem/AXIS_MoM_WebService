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
import static model.Connector.selectRegOfEntity;

/**
 *
 * @author Robois
 */
public class Event extends Entity{
    public PropertyAdmin dateOfEvent;
    public PropertyAdmin location;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    public PropertyAdmin socialNetwork;
    
    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	if (!((this.dateOfEvent.getEntity_locale() == null) && (this.dateOfEvent.getValue_locale() == null))) {
            list.add(new Property(this.dateOfEvent.getName(), this.dateOfEvent.getValue_locale(), this.dateOfEvent.getEntity_locale(), this.dateOfEvent.getType(), this.dateOfEvent.getLang()));
        }
        if (!((this.location.getEntity_locale() == null) && (this.location.getValue_locale() == null))) {
            list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getEntity_locale(), this.location.getType(), this.location.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(),this.description.getValue_locale(),this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        }
//        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
//            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(),this.socialNetwork.getLang()));
//        }
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public PropertyAdmin[] getPropertiesAdminEvent() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
	list.add(this.dateOfEvent);
        list.add(this.location);
        list.add(this.description);
        //list.add(this.socialNetwork);
        
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructEvent() {
        
    }
    
    public void insertDateOfEvent(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "dbont:date", p.getValue(), p.getType());
    }

    public void insertLocation(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", this.getURI());
                break;
                
            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", this.getURI());
                break;
                
            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getValue(), p.getType());
                break;
        }
    }
}
