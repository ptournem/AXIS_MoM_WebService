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
    public PropertyAdmin placeOfEvent;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    public PropertyAdmin socialNetwork;
    public PropertyAdmin hasParticipant;
    public PropertyAdmin website;
    
    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

	if (!((this.dateOfEvent.getEntity_locale() == null) && (this.dateOfEvent.getValue_locale() == null))) {
            list.add(new Property(this.dateOfEvent.getName(), this.dateOfEvent.getValue_locale(), this.dateOfEvent.getEntity_locale(), this.dateOfEvent.getType(), this.dateOfEvent.getLang()));
        }
        if (!((this.placeOfEvent.getEntity_locale() == null) && (this.placeOfEvent.getValue_locale() == null))) {
            list.add(new Property(this.placeOfEvent.getName(), this.placeOfEvent.getValue_locale(), this.placeOfEvent.getEntity_locale(), this.placeOfEvent.getType(), this.placeOfEvent.getLang()));
        }
        if (!((this.hasParticipant.getEntity_locale() == null) && (this.hasParticipant.getValue_locale() == null))) {
            list.add(new Property(this.hasParticipant.getName(),this.hasParticipant.getValue_locale(),this.hasParticipant.getEntity_locale(), this.hasParticipant.getType(), this.hasParticipant.getLang()));
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
        list.add(this.placeOfEvent);
        list.add(this.description);
        list.add(this.hasParticipant);
        list.add(this.sameAs);
        //list.add(this.socialNetwork);
        
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructEvent() {
        
    }
    
    public void insertDateOfEvent(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "dbont:date", p.getValue(), p.getType());
    }

    public void insertPlaceOfEvent(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
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
    
    public void insertHasParticipant(Property p) {
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getEnt()[0].getURI());
                break;
                
            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPhysicalPerson"), "axis-datamodel:participatesInEvent", this.getURI());
                break;
                
            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getValue(), p.getType());
                break;
        }
    }
}
